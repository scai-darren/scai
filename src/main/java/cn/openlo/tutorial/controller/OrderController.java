/**
*
*/
package cn.openlo.tutorial.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cn.openlo.common.ResponseCode;
import cn.openlo.common.util.LOJsonUtil;
import cn.openlo.exception.LOSException;
import cn.openlo.gear.exception.GenericBizException;
import cn.openlo.protocol.Protocol;
import cn.openlo.service.LOS;
import cn.openlo.service.LOSClient;
import cn.openlo.service.LOSFilters;
import cn.openlo.service.LOServiceClient;
import cn.openlo.service.ServiceResponse;
import cn.openlo.service.validation.Valid;
import cn.openlo.tutorial.dto.LoTutorialOrder;
import cn.openlo.tutorial.service.OrderService;

/**
 * <p>
 * Title: OrderController
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
@Controller
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderService orderService;
    
    
    @LOS(name = "lo-tutorial.dubbo1", enableProtocols = { Protocol.DUBBO,Protocol.HTTP })
    @LOSFilters(customList = "_generic_mq_biz_exception_handler")
    public void dubbo1(@Valid LoTutorialOrder form) throws LOSException {
    	System.out.println("进入Controller中dubbo1方法");
    	/*orderService.acceptOrder(form);*/
    	
    }
    

    @LOS(name = "cn.openlo.tutorial.buyProduct", enableProtocols = { Protocol.DUBBO, Protocol.HTTP })
    public BuyProductVO acceptOrder(@Valid BuyProductForm buyReq) throws LOSException {

        LoTutorialOrder reqOrderDTO = new LoTutorialOrder();

        try {
            BeanUtils.copyProperties(reqOrderDTO, buyReq);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        LoTutorialOrder acceptOrder = orderService.acceptOrder(reqOrderDTO);
        BuyProductVO vo = new BuyProductVO();
        vo.setContractNo(acceptOrder.getContractNo());
        vo.setAcceptAmount(acceptOrder.getApplyAmount());
        return vo;
    }

    @LOS(name = "cn.openlo.tutorial.confirmOrder", enableProtocols = { Protocol.DUBBO, Protocol.HTTP })
    public void notifyOrder(@Valid ConfirmOrderForm confirm) throws LOSException {

        orderService.confirmOrder(confirm.getOrderNo(), confirm.getCustId(), confirm.getOrderStatus());
    }

    @LOS(name = "lo-tutorial.topic-ORDER-AMS-submit", enableProtocols = { Protocol.MQ })
    @LOSFilters(customList = "_generic_mq_biz_exception_handler")
    public void topicORDERAMSSubmitAll(@Valid OrderRecevieForm form) throws LOSException {
        String tag = form.getTag();
        // 应用可以自己做一个mapping，支持动态扩展
        if ("1001301003".equals(tag)) {// 充值
            payIn(form);
        }
        else if ("1002301003".equals(tag)) {// 提现
            payOut(form);
        }
        else {
            // 由具体场景决定抛异常或者忽略
        }
    }

    @LOSClient(calleename = "topic-AMS-ORDER-result.1001301003", protocol = Protocol.MQ)
    protected LOServiceClient topicORDERAMSResult1001301003;

    private void payIn(OrderRecevieForm form) throws GenericBizException {
        String orderStatus = null;
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("umNo", form.getUmNo());
        parameters.put("orderNo", form.getOrderNo());
        try {
            // do business
            orderStatus = "01";// 成功
            parameters.put("responseCode", "000000");
            parameters.put("responseMsg", "成功");
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            orderStatus = "02";// 成功
            if (e instanceof GenericBizException) {
                GenericBizException ge = (GenericBizException) e;
                parameters.put("responseCode", ge.getErrorCode());
                parameters.put("responseMsg", ge.getErrorMessage());
            }
            else {
                parameters.put("responseCode", "888888");// 系统错误
                parameters.put("responseMsg", "系统错误:" + e.getMessage());
            }
        }
        // do business
        parameters.put("orderStatus", orderStatus);
        ServiceResponse res = topicORDERAMSResult1001301003.sendRequest(parameters);
        if (!ResponseCode.SUCCESS.getValue().equals(res.getResponseCode())) {
            throw new GenericBizException(res.getResponseCode(), res.getResponseMsg());
        }
    }

    private void payOut(OrderRecevieForm form) {

    }

    /**
     * 产品接受多个订单服务
     *
     * @param confirm
     * @throws LOSException
     */
    @LOS(name = "lo-tutorial.topicName2", enableProtocols = { Protocol.MQ }, properties = "LOS_mqBatchSize=2,LOS_mqTags=tagA")
    @LOSFilters(excludeList = "_jnl_no_inbound_service_filter", customList = "_generic_mq_biz_exception_handler")
    public void topicName2(@Valid TopicName2Form formList) throws LOSException {
        logger.info("handle formList,data:{}", LOJsonUtil.objectToJsonString(formList));
        List<OrderRecevieForm> paramsList = formList.getParamsList();
        for (OrderRecevieForm form : paramsList) {
            String tag = form.getTag();
            logger.info("handle tag {},data:{}", tag, LOJsonUtil.objectToJsonString(form));
        }
    }

    @LOS(name = "lo-tutorial.topicName3", enableProtocols = { Protocol.MQ }, properties = "LOS_mqBatchSize=2,LOS_mqTags=tagA-tagB")
    @LOSFilters(customList = "_generic_mq_biz_exception_handler")
    public void topicName3(@Valid OrderRecevieForm form) throws LOSException {
        String tag = form.getTag();
        logger.info("handle tag {},data:{}", tag, LOJsonUtil.objectToJsonString(form));
    }

    /**
     * dobbu接受通知结果
     *
     * @param form
     * @throws LOSException
     */
    @LOS(name = "lo-tutorial.topicName1Callback", enableProtocols = { Protocol.DUBBO })
    public void topicName1Callback(@Valid TopicName1CallBackForm form) throws LOSException {
        orderService.updateOrderStatus(form);
    }

    /**
     * mq接受通知结果
     *
     * @param form
     * @throws LOSException
     */
    @LOS(name = "lo-tutorial.topicName2Callback", enableProtocols = { Protocol.MQ })
    @LOSFilters(customList = "_generic_mq_biz_exception_handler")
    public void topicName2Callback(@Valid TopicName1CallBackForm form) throws LOSException {
        orderService.updateOrderStatus(form);
    }
    
    


}
