package cn.openlo.tutorial.process.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.openlo.process.engine.ElementListener;
import cn.openlo.process.engine.ServiceContext;
import cn.openlo.process.engine.model.Event;

@Component("testListener")
public class TestListener implements ElementListener {

    static final Logger log = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void handleEvent(Event event, ServiceContext context) {
        if (Event.CREATE == event) {
            log.info("handled event:" + event);
        }
    }
}
