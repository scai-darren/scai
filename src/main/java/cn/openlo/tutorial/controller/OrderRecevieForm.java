/**
*
*/
package cn.openlo.tutorial.controller;

import cn.openlo.gear.dataobject.MqBaseFormSupport;
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
 * @author hesp
 *
 * @version
 * @since 2016年5月24日
 */
public class OrderRecevieForm extends MqBaseFormSupport {

    /** 公共属性 **/
    @VNotEmpty
    private String orderNo;

    @VNotEmpty
    private String umNo;

    @VNotEmpty
    private String productCode;

    @VNotEmpty
    private String transAction;

    /** 扩展属性 **/
    private String orderData;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUmNo() {
        return umNo;
    }

    public void setUmNo(String umNo) {
        this.umNo = umNo;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getTransAction() {
        return transAction;
    }

    public void setTransAction(String transAction) {
        this.transAction = transAction;
    }

    public String getOrderData() {
        return orderData;
    }

    public void setOrderData(String orderData) {
        this.orderData = orderData;
    }

}
