package cn.openlo.tutorial.process.services;

import org.springframework.stereotype.Service;

import cn.openlo.process.engine.ProcessService;
import cn.openlo.process.engine.ServiceContext;
import cn.openlo.process.engine.exception.ProcessOverException;

@Service("testOver")
public class TestOverProcessService implements ProcessService {

    @Override
    public void execute(ServiceContext context) {
        throw new ProcessOverException("100001");
    }

}
