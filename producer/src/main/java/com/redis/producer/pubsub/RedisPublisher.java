package com.redis.producer.pubsub;

import com.redis.producer.entities.DummyRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisPublisher implements QueuePublisher<DummyRequest> {

    private RedisTemplate<String, DummyRequest> publisher;
    private Logger logger = LoggerFactory.getLogger(RedisPublisher.class);

    @Value("${topic.name}")
    private String topic;

    @Autowired
    public RedisPublisher(RedisTemplate<String, DummyRequest> publisher) {
        this.publisher = publisher;
    }

    public void send(DummyRequest request) throws InterruptedException {
        this.publisher.convertAndSend(this.topic, request);
        this.logger.info(
                "message added to queue, action="
                        + request.getAction()
                        + ", uuid="
                        + request.getUuid());
    }
}
