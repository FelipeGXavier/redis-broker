package com.redis.subscriber.pubsub;

import com.redis.subscriber.entities.DummyRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DummyRequestConsumer {

    private Logger logger = LoggerFactory.getLogger(DummyRequestConsumer.class);

    public void handleMessage(DummyRequest request) {
        this.logger.info("event received, uuid=" + request.getUuid() +", description=" + request.getDescription());
    }

}
