package com.redis.subscriber.config;

import com.redis.subscriber.entities.DummyRequest;
import com.redis.subscriber.pubsub.DummyRequestConsumer;
import com.redis.subscriber.pubsub.StringRequestConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.util.concurrent.Executors;

@Configuration
public class RedisConfiguration {

    @Value("${topic.entity}")
    private String entityChannel;
    @Value("${topic.string}")
    private String messageChannel;

    @Bean
    JedisConnectionFactory connectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        return factory;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter() {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(dummyRequestConsumer());
        messageListenerAdapter.setSerializer(jackson2JsonRedisSerializer());
        return messageListenerAdapter;
    }

    @Bean
    public MessageListenerAdapter listenerAdapterString() {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(stringRequestConsumer());
        messageListenerAdapter.setSerializer(new GenericToStringSerializer<>(String.class));
        return messageListenerAdapter;
    }

    @Bean
    public DummyRequestConsumer dummyRequestConsumer() {
        return new DummyRequestConsumer();
    }

    @Bean
    public StringRequestConsumer stringRequestConsumer() {
        return new StringRequestConsumer();
    }

    @Bean
    public Jackson2JsonRedisSerializer<DummyRequest> jackson2JsonRedisSerializer() {
        return new Jackson2JsonRedisSerializer<>(DummyRequest.class);
    }

    @Bean
    RedisMessageListenerContainer redisContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.addMessageListener(listenerAdapter(), new ChannelTopic(this.entityChannel));
        container.addMessageListener(new MessageListenerAdapter(new StringRequestConsumer()), new ChannelTopic(this.messageChannel));
        container.setTaskExecutor(Executors.newFixedThreadPool(4));
        return container;
    }
}
