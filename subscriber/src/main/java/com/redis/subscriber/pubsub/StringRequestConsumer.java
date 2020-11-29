package com.redis.subscriber.pubsub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class StringRequestConsumer implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(DummyRequestConsumer.class);

    @Override
    public void onMessage(Message message, byte[] bytes) {
        this.logger.info("event received, message=" + message);
    }
}
