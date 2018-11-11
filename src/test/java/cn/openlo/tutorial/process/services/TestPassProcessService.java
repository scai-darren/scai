package cn.openlo.tutorial.process.services;

import org.springframework.stereotype.Service;

import cn.openlo.process.engine.ServiceContext;
import cn.openlo.process.engine.service.AbstractProcessService;

@Service("testPass")
public class TestPassProcessService extends AbstractProcessService {

    @Override
    public void doService(ServiceContext context) {
        context.getServiceMap().put(context.getService().getName(), true);
    }

}
