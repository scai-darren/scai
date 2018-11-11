
package cn.openlo.tutorial.process.services;

import org.springframework.stereotype.Service;

import cn.openlo.process.engine.ProcessService;
import cn.openlo.process.engine.ServiceContext;

@Service("testTimeout")
public class TestTimeoutProcessService implements ProcessService {

    @Override
    public void execute(ServiceContext context) {

        try {
            Thread.sleep(6000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
