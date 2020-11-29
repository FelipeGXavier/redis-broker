package com.redis.publisher.pubsub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Qualifier("stringPublisher")
public class StringRedisPublisher implements MessagePublisher<String> {

    private RedisTemplate<String, String> publisher;
    private Logger logger = LoggerFactory.getLogger(StringRedisPublisher.class);

    @Value("${topic.string}")
    private String topic;

    @Autowired
    public StringRedisPublisher(
            @Qualifier("redisTemplateMessage") RedisTemplate<String, String> publisher) {
        this.publisher = publisher;
    }

    @Override
    public void send(String message) {
        this.publisher.convertAndSend(this.topic, message);
        this.logger.info("message added to queue, message=" + message);
    }
}
