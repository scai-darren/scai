/**
*
*/
package cn.openlo.tutorial;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.openlo.common.beanutils.MapToBean;
import cn.openlo.common.util.DateUtil;
import cn.openlo.gear.jnl.JnlNoHelper;
import cn.openlo.gear.test.GearContextConfiguration;
import cn.openlo.gear.test.GearTestBase;
import cn.openlo.service.LOSClient;
import cn.openlo.service.LOServiceClient;
import cn.openlo.service.ServiceResponse;
import cn.openlo.tutorial.controller.BuyProductVO;

/**
 * <p>
 * Title: GearTest
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
@GearContextConfiguration(boxName = "box01", boxHome = "${user.dir}/src/test/resources/box01/", gearName = "lo-tutorial", gearStartTimeout = 100000)
public class GearTest extends GearTestBase {

    @Test
    public void testRuntime() throws Exception {

        System.in.read();
    }

    @Autowired
    private JnlNoHelper jnlNoHelper;
    @LOSClient(calleename = "cn.openlo.tutorial.buyProduct", localPriority = true)
    private LOServiceClient buyClient;

    @SuppressWarnings("rawtypes")
    @Test
    public void testBuyProductLOS() throws Exception {

        Map<Object, Object> request = new HashMap<Object, Object>();
        String orderNo = DateUtil.getDateString(new Date(), "yyyyMMddHHmmss");
        BigDecimal applyAmount = new BigDecimal("1000.00");
        request.put("orderNo", orderNo);
        request.put("productCode", "186001");
        request.put("transAction", "1001");
        request.put("custId", "1016123456789012");
        request.put("custName", "gaozhi");
        request.put("productId", "test_prod001");
        request.put("applyAmount", applyAmount);
        request.put("channel", "1001");
        request.put("channelDate", orderNo.substring(0, 8));
        request.put("channelJnlNo", jnlNoHelper.generate().getStringValue());
        System.out.println("orderNo:" + orderNo);
        ServiceResponse response = buyClient.sendRequest(request);
        if (!"000000".equals(response.getResponseCode())) {
            throw response.getFailCause();
        }
        BuyProductVO retVal = MapToBean.toBean(BuyProductVO.class, (Map) response.getModel(), false, null);
        Assert.assertNotNull(retVal.getContractNo());
        Assert.assertEquals(applyAmount, retVal.getAcceptAmount());
    }

    @LOSClient(calleename = "cn.openlo.tutorial.confirmOrder", localPriority = true)
    private LOServiceClient confirmClient;

    @Test
    public void testConfirmOrderLOS() throws Exception {

        Map<Object, Object> request = new HashMap<Object, Object>();
        request.put("channel", "1001");
        request.put("channelDate", DateUtil.getDateString(new Date(), "yyyyMMdd"));
        request.put("channelJnlNo", jnlNoHelper.generate().getStringValue());
        request.put("orderNo", "20160623100818");
        request.put("custId", "1016123456789012");
        request.put("orderStatus", "01");
        ServiceResponse response = confirmClient.sendRequest(request);
        if (!"000000".equals(response.getResponseCode())) {
            throw response.getFailCause();
        }
    }
}
