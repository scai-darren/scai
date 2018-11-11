/**
*
*/
package cn.openlo.tutorial.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import cn.openlo.gear.sequence.StringSequenceGenerator;
import cn.openlo.protocol.Protocol;
import cn.openlo.service.LOS;

/**
 * <p>
 * Title: GeneratorController
 * </p>
 * <p>
 * Description:这个Controller仅为演示SAO而编写的服务端
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
@Controller
public class GeneratorController {

    @Resource(name = "contractNoGenerator")
    private StringSequenceGenerator contractNoGenerator;

    @LOS(name = "cn.openlo.tutorial.generateContractNo", enableProtocols = { Protocol.DUBBO, Protocol.HTTP })
    public String generateContractNo() {
        return contractNoGenerator.getStringSequence();
    }
}
