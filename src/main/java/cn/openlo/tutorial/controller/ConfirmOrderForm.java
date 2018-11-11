/**
*
*/
package cn.openlo.tutorial.controller;

import cn.openlo.dataobject.Form;
import cn.openlo.service.validation.VNotEmpty;

/**
 * <p>
 * Title: ConfirmOrderForm
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
public class ConfirmOrderForm implements Form {

    @VNotEmpty
    private String orderNo;
    @VNotEmpty
    private String custId;
    @VNotEmpty
    private String orderStatus;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
