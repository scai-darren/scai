/**
*
*/
package cn.openlo.tutorial.service.order;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionOperations;

import cn.openlo.exception.LOException;
import cn.openlo.gear.exception.GenericBizException;
import cn.openlo.gear.jnl.JnlNoHelper;
import cn.openlo.tutorial.BizConstants;
import cn.openlo.tutorial.controller.TopicName1CallBackForm;
import cn.openlo.tutorial.dao.mybatis.LoTutorialCustShareMapper;
import cn.openlo.tutorial.dao.mybatis.LoTutorialOrderMapper;
import cn.openlo.tutorial.dao.mybatis.LoTutorialProductMapper;
import cn.openlo.tutorial.dto.LoTutorialCustShare;
import cn.openlo.tutorial.dto.LoTutorialCustShareCriteria;
import cn.openlo.tutorial.dto.LoTutorialCustShareKey;
import cn.openlo.tutorial.dto.LoTutorialOrder;
import cn.openlo.tutorial.dto.LoTutorialOrderCriteria;
import cn.openlo.tutorial.dto.LoTutorialProduct;
import cn.openlo.tutorial.sao.GeneratorSAO;
import cn.openlo.tutorial.service.OrderService;

/**
 * <p>
 * Title: OrderServiceImpl
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: openlo.cn Copyright (C) 2016
 * </p>
 *
 * @author gaozhi
 *
 * @version
 * @since 2016年5月24日
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private LoTutorialOrderMapper orderMapper;
    @Autowired
    private LoTutorialProductMapper productMapper;
    @Autowired
    private LoTutorialCustShareMapper custShareMapper;
    @Autowired
    private TransactionOperations transactionOperations;
    @Autowired
    private GeneratorSAO contractNoGenerator;
    @Autowired
    private JnlNoHelper jnlNoHelper;
    @Value("${systemId}")
    private String systemId;

    
    
    
    @Override
    public LoTutorialOrder acceptOrder(final LoTutorialOrder order) throws GenericBizException {

        order.setOrderStatus(BizConstants.ORDER_STATUS_ACCEPT);
        order.setHandleStatus(BizConstants.HANDLE_STATUS_DOING);
        order.refreshCreatedAndUpdated();
        order.setExtData("systemId:" + systemId);
        orderMapper.insert(order);

        final LoTutorialCustShare custShare;
        try {
            fillProductInfoAndValidate(order);

            order.setContractNo(contractNoGenerator.generateContractNo());

            LoTutorialCustShareKey key = new LoTutorialCustShareKey();
            key.setCustId(order.getCustId());
            key.setProductId(order.getProductId());
            custShare = custShareMapper.selectByPrimaryKey(key);
        }
        catch (Exception e) {
            order.setHandleStatus(BizConstants.HANDLE_STATUS_FAIL);
            orderMapper.updateByPrimaryKeySelective(order);
            if (e instanceof GenericBizException) {
                throw (GenericBizException) e;
            }
            else {
                throw new GenericBizException("900001", e);
            }
        }
        try {
            order.setExtData(order.getExtData() + "|" + jnlNoHelper.generateByCurrentJnlNo().getStringValue());
        }
        catch (LOException e) {
            order.setHandleStatus(BizConstants.HANDLE_STATUS_FAIL);
            orderMapper.updateByPrimaryKeySelective(order);
            logger.error("jnlNo生成失败", e);
            throw new GenericBizException("900001", e);
        }

        try {
            transactionOperations.execute(new TransactionCallback<Void>() {

                @Override
                public Void doInTransaction(TransactionStatus status) {

                    if (custShare == null) {
                        LoTutorialCustShare newCustShare = new LoTutorialCustShare();
                        newCustShare.setCustId(order.getCustId());
                        newCustShare.setProductId(order.getProductId());
                        newCustShare.setRealShare(BigDecimal.ZERO);
                        newCustShare.setPreShare(order.getApplyAmount());
                        try {
                            custShareMapper.insert(newCustShare);
                        }
                        catch (DataIntegrityViolationException e) {
                            throw new RuntimeException("请不要频繁操作");
                        }
                    }
                    else {
                        BigDecimal preShareBefore = custShare.getPreShare();
                        custShare.setPreShare(order.getApplyAmount().add(custShare.getPreShare()));
                        LoTutorialCustShareCriteria example = new LoTutorialCustShareCriteria();
                        example.createCriteria().andCustIdEqualTo(custShare.getCustId()).andProductIdEqualTo(custShare.getProductId())
                            .andRealShareEqualTo(custShare.getRealShare()).andPreShareEqualTo(preShareBefore);
                        int count = custShareMapper.updateByExampleSelective(custShare, example);
                        if (count < 1) {
                            throw new RuntimeException("请不要频繁操作");
                        }
                    }
                    order.setHandleStatus(BizConstants.HANDLE_STATUS_SUCC);
                    orderMapper.updateByPrimaryKeySelective(order);
                    return null;
                }
            });
        }
        catch (TransactionException e) {
            order.setHandleStatus(BizConstants.HANDLE_STATUS_FAIL);
            orderMapper.updateByPrimaryKeySelective(order);
            Throwable rootCause = e.getRootCause();
            if (rootCause instanceof RuntimeException && "请不要频繁操作".equals(rootCause.getMessage())) {
                throw new GenericBizException("900001");
            }
            else {
                throw new GenericBizException("900001", e);
            }
        }
        catch (Exception e) {
            order.setHandleStatus(BizConstants.HANDLE_STATUS_FAIL);
            orderMapper.updateByPrimaryKeySelective(order);
            throw new GenericBizException("900001", e);
        }

        return order;
    }

    private void fillProductInfoAndValidate(LoTutorialOrder order) throws GenericBizException {
        LoTutorialProduct product = productMapper.selectByPrimaryKey(order.getProductId());
        if (product == null) {
            throw new GenericBizException("100001");
        }
        order.setProductName(product.getProductName());
        if (order.getApplyAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new GenericBizException("100002");
        }
        if (order.getApplyAmount().compareTo(product.getSingleMinSub()) < 0
                || order.getApplyAmount().compareTo(product.getSingleMaxSub()) > 0) {
            throw new GenericBizException("100003");
        }
        if (order.getApplyAmount().divideAndRemainder(product.getSingleSubAddUnit())[1].compareTo(BigDecimal.ZERO) != 0) {
            throw new GenericBizException("100004");
        }
    }

    @Override
    public void confirmOrder(String orderNo, String custId, String orderStatus) throws GenericBizException {

        LoTutorialOrder existed = orderMapper.selectByPrimaryKey(orderNo);
        if (existed == null) {
            throw new GenericBizException("100005", new String[] { orderNo });
        }
        LoTutorialOrder orderRec = new LoTutorialOrder();
        orderRec.setOrderNo(orderNo);
        orderRec.setCustId(custId);
        if ("01".equals(orderStatus)) {
            orderRec.setOrderStatus(BizConstants.ORDER_STATUS_CONFIRM);
        }
        else {
            orderRec.setOrderStatus(BizConstants.ORDER_STATUS_INVALID);
        }
        orderRec.setHandleStatus(BizConstants.HANDLE_STATUS_DOING);
        orderRec.refreshUpdated();
        LoTutorialOrderCriteria example = new LoTutorialOrderCriteria();
        example.createCriteria().andOrderStatusEqualTo(BizConstants.ORDER_STATUS_ACCEPT)
            .andHandleStatusEqualTo(BizConstants.HANDLE_STATUS_SUCC).andOrderNoEqualTo(orderNo);
        int count = orderMapper.updateByExampleSelective(orderRec, example);
        if (count < 1) {
            throw new GenericBizException("100006", new String[] { orderNo });
        }

        final LoTutorialOrder order = orderMapper.selectByPrimaryKey(orderNo);
        LoTutorialCustShareKey key = new LoTutorialCustShareKey();
        key.setCustId(order.getCustId());
        key.setProductId(order.getProductId());
        final LoTutorialCustShare custShare = custShareMapper.selectByPrimaryKey(key);
        if (custShare.getPreShare().compareTo(order.getApplyAmount()) < 0) {
            throw new GenericBizException("900001");
        }
        order.setConfirmAmount(order.getApplyAmount());
        order.setConfirmShare(order.getApplyAmount());
        order.setHandleStatus(BizConstants.HANDLE_STATUS_SUCC);
        order.refreshUpdated();
        try {
            order.setExtData(
                order.getExtData() + "|" + jnlNoHelper.generate(jnlNoHelper.translateFromString(order.getChannelJnlNo())).getStringValue());
        }
        catch (LOException e) {
            order.setHandleStatus(BizConstants.HANDLE_STATUS_FAIL);
            orderMapper.updateByPrimaryKeySelective(order);
            logger.error("jnlNo生成失败", e);
            throw new GenericBizException("900001", e);
        }

        if (BizConstants.ORDER_STATUS_CONFIRM.equals(order.getOrderStatus())) {
            custShare.setRealShare(custShare.getRealShare().add(order.getApplyAmount()));
        }
        custShare.setPreShare(custShare.getPreShare().subtract(order.getApplyAmount()));

        try {
            transactionOperations.execute(new TransactionCallback<Void>() {

                @Override
                public Void doInTransaction(TransactionStatus status) {

                    orderMapper.updateByPrimaryKeySelective(order);
                    custShareMapper.updateByPrimaryKeySelective(custShare);
                    return null;
                }
            });
        }
        catch (RuntimeException e) {
            order.setConfirmAmount(null);
            order.setConfirmShare(null);
            order.setOrderStatus(BizConstants.ORDER_STATUS_ACCEPT);
            order.setHandleStatus(BizConstants.HANDLE_STATUS_SUCC);
            orderMapper.updateByPrimaryKeySelective(order);
            throw new GenericBizException("900001", e);
        }
    }

    /**
     *
     * (non-Javadoc)
     * 
     * @see cn.openlo.tutorial.service.OrderService#updateOrderStatus(cn.openlo.tutorial.controller.TopicName1CallBackForm)
     */
    @Override
    public void updateOrderStatus(TopicName1CallBackForm form) throws GenericBizException {

    }

	@Override
	public void add(LoTutorialOrder order) {
		// TODO Auto-generated method stub
		orderMapper.insert(order);
	}
}
