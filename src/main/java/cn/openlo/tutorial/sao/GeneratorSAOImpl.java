/**
*
*/
package cn.openlo.tutorial.sao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import cn.openlo.exception.LOSException;
import cn.openlo.service.LOSClient;
import cn.openlo.service.LOServiceClient;
import cn.openlo.service.ServiceResponse;

/**
 * <p>
 * Title: GeneratorSAOImpl
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
@Component
public class GeneratorSAOImpl implements GeneratorSAO {

    @LOSClient(calleename = "cn.openlo.tutorial.generateContractNo", timeout = 2000)
    private LOServiceClient client;

    @Override
    public String generateContractNo() throws LOSException {
        Map<Object, Object> request = new HashMap<Object, Object>();
        ServiceResponse response = client.sendRequest(request);
        if (!"000000".equals(response.getResponseCode())) {
            throw new LOSException(response.getResponseMsg(), response.getFailCause());
        }
        return (String) response.getModel();
    }

}
