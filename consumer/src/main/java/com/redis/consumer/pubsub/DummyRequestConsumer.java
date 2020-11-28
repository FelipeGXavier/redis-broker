package com.redis.consumer.pubsub;

import com.redis.consumer.entities.DummyRequest;
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
