/**
*
*/
package cn.openlo.tutorial.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

import cn.openlo.common.ResponseCode;
import cn.openlo.common.util.DateUtil;
import cn.openlo.gear.exception.GenericBizException;
import cn.openlo.gear.jnl.JnlNoHelper;
import cn.openlo.gear.test.GearContextConfiguration;
import cn.openlo.gear.test.GearTestBase;
import cn.openlo.protocol.Protocol;
import cn.openlo.service.LOSClient;
import cn.openlo.service.LOServiceClient;
import cn.openlo.service.ServiceResponse;
import cn.openlo.tutorial.dto.LoTutorialOrder;

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
@GearContextConfiguration(boxName = "box01", boxHome = "${user.dir}/src/test/resources/box01/", gearName = "lo-tutorial", gearStartTimeout = 400000)
public class OrderControllerTest extends GearTestBase {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JnlNoHelper jnlNoHelper;

    @LOSClient(calleename = "lo-tutorial.dubbo1", protocol = Protocol.DUBBO, localPriority = false)
    protected LOServiceClient losClient;
    
    @Test
    public void dubbo1() throws Exception {
    	/*Map<Object, Object> request = new HashMap<Object, Object>();
		Map<String, String> nameValuePairs = new HashMap<>();
		nameValuePairs.put("content", "ContentByCallRemoteHttp");
		nameValuePairs.put("name", "gaozhi");
		HttpFormBody formBody = new HttpFormBody();
		HttpForm form = new HttpForm(formBody);
		formBody.setContent4NameValuePair(nameValuePairs);
		request.put(HttpForm.KEY_FORM, form);
		ServiceResponse result = losClient.sendRequest(request);

		Assert.assertEquals("Welcome gaozhi!", result.getModel());*/
		System.out.println("进入测试中的dubbo1方法");
		HashMap<String, String> request = new HashMap<>();
		LoTutorialOrder order = new LoTutorialOrder();
		order.setCustId("aa");
		order.setOrderNo("aa");
		ServiceResponse response = losClient.sendRequest(order);
		
		
        /*ServiceResponse response = losClient.sendRequest(request);
        Assert.assertEquals("No name!", response.getModel());
        request.put("name", "gaozhi");
        response = losClient.sendRequest(request);
        Assert.assertEquals("Welcome gaozhi!", response.getModel());*/
    }
    
    
    
    
    @LOSClient(calleename = "topic-ORDER-AMS-submit.1001301003", protocol = Protocol.MQ)
    protected LOServiceClient topicORDERAMSSubmit1001301003;

    @Test
    public void testTopicORDERAMSsubmitAll() throws Exception {
        OrderRecevieForm form = new OrderRecevieForm();
        form.setChannel("4001");
        form.setChannelDate(DateUtil.getDateString(new Date(), "yyyyMMdd"));
        form.setChannelJnlNo(jnlNoHelper.generate().getStringValue());
        form.setSourceSystemId("4001");
        form.setOrderNo(System.currentTimeMillis() + "");
        form.setUmNo("100109312312");
        form.setProductCode("301003");
        form.setTransAction("1001");
        form.setOrderData(
            "{\"summary\":\"电子账户提现\",\"cardNo\":\"6217002741562186917\",\"transDate\":\"20160907\",\"transAmount\":1.00,\"eacct\":\"6226720401008378\",\"umNo\":\"1002016090000027256\",\"payPostscript\":\"电子账户提现\",\"currency\":\"156\"}");
        form.setTag(null);

        ServiceResponse res = topicORDERAMSSubmit1001301003.sendRequest(form);
        logger.info("testTopicName1 res = " + JSON.toJSONString(res));
    }

    @LOSClient(calleename = "topicName2.tagA", protocol = Protocol.MQ)
    protected LOServiceClient topicName2;

    @Test
    public void testTopicName2() throws Exception {
        OrderRecevieForm form = new OrderRecevieForm();
        form.setChannel("4001");
        form.setChannelDate(DateUtil.getDateString(new Date(), "yyyyMMdd"));
        form.setChannelJnlNo(jnlNoHelper.generate().getStringValue());
        form.setSourceSystemId("4001");
        form.setUmNo("100109312312");
        form.setOrderNo(System.currentTimeMillis() + "");

        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                form.setTag("1001");
            }
            else {
                form.setTag("1002");
            }
            ServiceResponse res = topicName2.sendRequest(form);
            logger.info("testTopicName2 res = " + JSON.toJSONString(res));
        }
        logger.info("testTopicName2 over.");
    }

    @LOSClient(calleename = "topicName3.tagA-tagB", protocol = Protocol.MQ)
    private LOServiceClient topicName3;

    @Test
    public void testTopicName3() throws Exception {
        OrderRecevieForm form = new OrderRecevieForm();
        form.setTag("1001");
        form.setChannel("4001");
        form.setChannelDate(DateUtil.getDateString(new Date(), "yyyyMMdd"));
        form.setChannelJnlNo(jnlNoHelper.generate().getStringValue());
        form.setSourceSystemId("4001");
        form.setOrderNo(System.currentTimeMillis() + "");
        form.setUmNo("100109312312");
        form.setTag(null);

        ServiceResponse res = topicName3.sendRequest(form);
        logger.info("testTopicName1 res = " + JSON.toJSONString(res));
    }

    @LOSClient(calleename = "lo-tutorial.topicName1Callback", protocol = Protocol.DUBBO)
    protected LOServiceClient topicName1Callback;

    @Test
    public void topicName1Callback() throws Exception {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("custId", "1");
        parameters.put("orderNo", "2");
        parameters.put("orderStatus", "02");
        parameters.put("responseCode", "000000");
        parameters.put("responseMsg", "1111");
        ServiceResponse topicName1CallbackRes = topicName1Callback.sendRequest(parameters);
        if (!ResponseCode.SUCCESS.getValue().equals(topicName1CallbackRes.getResponseCode())) {
            throw new GenericBizException(topicName1CallbackRes.getResponseCode(), topicName1CallbackRes.getResponseMsg());
        }
    }

    @LOSClient(calleename = "topicName2Callback", protocol = Protocol.MQ)
    protected LOServiceClient topicName2Callback;

    @Test
    public void topicName2Callback() throws Exception {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("custId", "1");
        parameters.put("orderNo", "2");
        parameters.put("orderStatus", "02");
        parameters.put("responseCode", "000000");
        parameters.put("responseMsg", "responseMsg");
        ServiceResponse topicName2CallbackRes = topicName2Callback.sendRequest(parameters);
        if (!ResponseCode.SUCCESS.getValue().equals(topicName2CallbackRes.getResponseCode())) {
            throw new GenericBizException(topicName2CallbackRes.getResponseCode(), topicName2CallbackRes.getResponseMsg());
        }
    }
}
