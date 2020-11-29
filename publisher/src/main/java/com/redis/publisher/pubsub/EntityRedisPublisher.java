package com.redis.publisher.pubsub;

import com.redis.publisher.entities.DummyRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Qualifier("entityPublisher")
public class EntityRedisPublisher implements MessagePublisher<DummyRequest> {

    private RedisTemplate<String, DummyRequest> publisher;
    private Logger logger = LoggerFactory.getLogger(EntityRedisPublisher.class);

    @Value("${topic.entity}")
    private String topic;

    @Autowired
    public EntityRedisPublisher(@Qualifier("redisTemplateEntity") RedisTemplate<String, DummyRequest> publisher) {
        this.publisher = publisher;
    }

    public void send(DummyRequest request) {
        this.publisher.convertAndSend(this.topic, request);
        this.logger.info(
                "message added to queue, action="
                        + request.getAction()
                        + ", uuid="
                        + request.getUuid());
    }
}
