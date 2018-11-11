/**
*
*/
package cn.openlo.tutorial.controller;

import java.util.List;

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
public class TopicName2Form implements Form {

    @VNotEmpty
    private List<OrderRecevieForm> paramsList;

    public List<OrderRecevieForm> getParamsList() {
        return paramsList;
    }

    public void setParamsList(List<OrderRecevieForm> paramsList) {
        this.paramsList = paramsList;
    }

}
