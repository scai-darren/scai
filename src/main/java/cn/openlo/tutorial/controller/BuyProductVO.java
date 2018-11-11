/**
*
*/
package cn.openlo.tutorial.controller;

import java.math.BigDecimal;

import cn.openlo.gear.dataobject.BaseVOSupport;

/**
 * <p>
 * Title: BuyProductVO
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
 * @since 2016年5月31日
 */
public class BuyProductVO extends BaseVOSupport {

    private String contractNo;
    private BigDecimal acceptAmount;

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public BigDecimal getAcceptAmount() {
        return acceptAmount;
    }

    public void setAcceptAmount(BigDecimal acceptAmount) {
        this.acceptAmount = acceptAmount;
    }
}
