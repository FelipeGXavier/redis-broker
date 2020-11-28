package com.redis.consumer.config;

import com.redis.consumer.entities.DummyRequest;
import com.redis.consumer.pubsub.DummyRequestConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.util.concurrent.Executors;

@Configuration
public class RedisConfiguration {

    @Bean
    JedisConnectionFactory connectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        return factory;
    }

    @Bean
    public RedisTemplate<String, DummyRequest> redisTemplate() {
        RedisTemplate<String, DummyRequest> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory());
        template.setDefaultSerializer(jackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(DummyRequestConsumer dummyRequestConsumer) {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(dummyRequestConsumer);
        messageListenerAdapter.setSerializer(jackson2JsonRedisSerializer());
        return messageListenerAdapter;
    }

    @Bean
    public Jackson2JsonRedisSerializer<DummyRequest> jackson2JsonRedisSerializer() {
        return new Jackson2JsonRedisSerializer<>(DummyRequest.class);
    }

    @Bean
    RedisMessageListenerContainer redisContainer(MessageListenerAdapter messageListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.addMessageListener(messageListenerAdapter, new ChannelTopic("redisQueue"));
        container.setTaskExecutor(Executors.newFixedThreadPool(4));
        return container;
    }
}
