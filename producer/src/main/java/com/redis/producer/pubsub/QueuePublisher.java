package com.redis.producer.pubsub;

public interface QueuePublisher<T> {

    void send(T t) throws InterruptedException;
}
