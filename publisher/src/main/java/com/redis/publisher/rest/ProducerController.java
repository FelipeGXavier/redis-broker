package com.redis.publisher.rest;

import com.github.javafaker.Faker;
import com.redis.publisher.entities.DummyRequest;
import com.redis.publisher.pubsub.MessagePublisher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/producer")
public class ProducerController {

    private final MessagePublisher<DummyRequest> entityPublisher;
    private final MessagePublisher<String> messagePublisher;

    public ProducerController(
            @Qualifier("entityPublisher") MessagePublisher<DummyRequest> publisher,
            @Qualifier("stringPublisher") MessagePublisher<String> messagePublisher) {
        this.entityPublisher = publisher;
        this.messagePublisher = messagePublisher;
    }

    @PostMapping
    public ResponseEntity<String> publishEntity(@RequestBody DummyRequest request) {
        this.entityPublisher.send(request);
        var response = "{\"success\": true, \"message\": \"Event %s was sent to redis\"}";
        response = String.format(response, request.getUuid());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<String> publishMessage() {
        var faker = new Faker();
        final var size = 1000;
        for (var i = 0; i < size; i++) {
            this.messagePublisher.send(faker.name().name() + " #" + i);
        }
        var response = "{\"success\": true, \"message\": \"Messages was sent to redis\"}";
        return ResponseEntity.ok(response);
    }
}
