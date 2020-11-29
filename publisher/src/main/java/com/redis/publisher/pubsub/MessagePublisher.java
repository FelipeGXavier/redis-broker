package com.redis.publisher.pubsub;

public interface MessagePublisher<T> {

    void send(T t);
}
