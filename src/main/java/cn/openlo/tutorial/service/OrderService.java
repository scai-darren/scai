/**
*
*/
package cn.openlo.tutorial.service;

import cn.openlo.gear.exception.GenericBizException;
import cn.openlo.tutorial.controller.TopicName1CallBackForm;
import cn.openlo.tutorial.dto.LoTutorialOrder;

/**
 * <p>
 * Title: OrderService
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
public interface OrderService {

    public LoTutorialOrder acceptOrder(LoTutorialOrder order) throws GenericBizException;

    public void add(LoTutorialOrder oder);
    
    public void confirmOrder(String orderNo, String custId, String orderStatus) throws GenericBizException;

    public void updateOrderStatus(TopicName1CallBackForm form) throws GenericBizException;
}
