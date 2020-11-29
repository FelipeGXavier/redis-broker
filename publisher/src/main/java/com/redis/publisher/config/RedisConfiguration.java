package com.redis.publisher.config;

import com.redis.publisher.entities.DummyRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
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
    @Qualifier("redisTemplateEntity")
    public RedisTemplate<String, DummyRequest> redisTemplate() {
        RedisTemplate<String, DummyRequest> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory());
        template.setDefaultSerializer(jackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    @Qualifier("redisTemplateMessage")
    public RedisTemplate<String, String> redisTemplateMessage() {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setValueSerializer(new GenericToStringSerializer<>(String.class));
        template.setConnectionFactory(connectionFactory());
        return template;
    }

    @Bean
    public Jackson2JsonRedisSerializer<DummyRequest> jackson2JsonRedisSerializer() {
        return new Jackson2JsonRedisSerializer<>(DummyRequest.class);
    }

    @Bean
    RedisMessageListenerContainer redisContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setTaskExecutor(Executors.newFixedThreadPool(4));
        return container;
    }
}
