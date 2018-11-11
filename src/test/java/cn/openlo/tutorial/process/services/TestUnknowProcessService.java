package cn.openlo.tutorial.process.services;

import org.springframework.stereotype.Service;

import cn.openlo.process.engine.ProcessService;
import cn.openlo.process.engine.ServiceContext;
import cn.openlo.process.engine.exception.ProcessUnknowException;

@Service("testUnknow")
public class TestUnknowProcessService implements ProcessService {

    @Override
    public void execute(ServiceContext context) {
        throw new ProcessUnknowException();
    }

}