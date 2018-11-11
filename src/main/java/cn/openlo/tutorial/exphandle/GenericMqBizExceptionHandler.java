/**
*
*/
package cn.openlo.tutorial.exphandle;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.openlo.common.ResponseCode;
import cn.openlo.exception.JumpException;
import cn.openlo.exception.ValidateFailException;
import cn.openlo.gear.exception.GenericBizException;
import cn.openlo.protocol.Protocol;
import cn.openlo.service.LOSClient;
import cn.openlo.service.LOServiceClient;
import cn.openlo.service.ServiceContext;
import cn.openlo.service.ServiceExceptionHandler;
import cn.openlo.service.ServiceRequest;
import cn.openlo.service.ServiceResponse;

/**
 * <p>
 * Title: GenericBizExceptionHandler
 * </p>
 * <p>
 * Description:其他实现了ServiceExceptionHandler的类，如果抛JumpException，则后面的异常处理不会执行
 * </p>
 * <p>
 * Copyright: openlo.cn Copyright (C) 2016
 * </p>
 *
 * @author hesp
 *
 * @version
 * @since 2016年8月25日
 */
public class GenericMqBizExceptionHandler implements ServiceExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @LOSClient(calleename = "topicName1.*", protocol = Protocol.MQ)
    protected LOServiceClient topicName1All;

    @LOSClient(calleename = "topicName2.tagA", protocol = Protocol.MQ)
    protected LOServiceClient topicName2CallbackTagA;

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;// 先放置在链的最末端
    }

    @Override
    public void handleException(ServiceRequest request, ServiceResponse serviceResponse, ServiceContext serviceContext, Exception exception)
            throws JumpException {
        try {
            if (exception instanceof ValidateFailException) {
                String serviceName = request.getServiceName();
                if ("topicName1.*".equals(serviceName)) {
                    Map<String, Object> parameters = new HashMap<String, Object>();
                    parameters.put("custId", request.getParameters().get("custId"));
                    parameters.put("orderNo", request.getParameters().get("orderNo"));
                    parameters.put("orderStatus", request.getParameters().get("orderStatus"));
                    parameters.put("responseCode", request.getParameters().get("responseCode"));
                    parameters.put("responseMsg", request.getParameters().get("responseMsg"));
                    ServiceResponse res = topicName1All.sendRequest(parameters);
                    if (ResponseCode.SUCCESS.getValue().equals(res.getResponseCode())) {
                        throw new GenericBizException(res.getResponseCode(), res.getResponseMsg());
                    }
                    serviceResponse.setResponseCode(ResponseCode.SUCCESS.getValue());// 设置为000000，才认为是消费成功,否则会重复消费
                }
                else if ("topicName2.tagA".equals(serviceName)) {
                    Map<String, Object> parameters = new HashMap<String, Object>();
                    parameters.put("custId", request.getParameters().get("custId"));
                    parameters.put("orderNo", request.getParameters().get("orderNo"));
                    parameters.put("orderStatus", request.getParameters().get("orderStatus"));
                    parameters.put("responseCode", request.getParameters().get("responseCode"));
                    parameters.put("responseMsg", request.getParameters().get("responseMsg"));
                    ServiceResponse res = topicName2CallbackTagA.sendRequest(parameters);
                    if (ResponseCode.SUCCESS.getValue().equals(res.getResponseCode())) {
                        throw new GenericBizException(res.getResponseCode(), res.getResponseMsg());
                    }
                    serviceResponse.setResponseCode(ResponseCode.SUCCESS.getValue());// 设置为000000，才认为是消费成功,否则会重复消费
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
