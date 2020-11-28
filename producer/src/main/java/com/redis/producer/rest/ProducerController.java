package com.redis.producer.rest;

import com.redis.producer.entities.DummyRequest;
import com.redis.producer.pubsub.QueuePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/producer")
public class ProducerController {

    private final QueuePublisher<DummyRequest> publisher;

    @Autowired
    public ProducerController(QueuePublisher<DummyRequest> publisher) {
        this.publisher = publisher;
    }

    @PostMapping
    public ResponseEntity<String> publishData(@RequestBody DummyRequest request) throws InterruptedException {
        this.publisher.send(request);
        var response = "{\"success\": true, \"message\": \"Event %s was sent to redis queue\"}";
        response = String.format(response, request.getUuid());
        return ResponseEntity.ok(response);
    }
}
