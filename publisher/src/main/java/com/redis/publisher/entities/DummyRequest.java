package com.redis.publisher.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;
import java.util.UUID;

public class DummyRequest {

    @JsonProperty("sent_at")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate sentAt;

    private String action;
    private String description;
    private String uuid = UUID.randomUUID().toString();

    public DummyRequest(LocalDate sentAt, String action, String description) {
        this.sentAt = sentAt;
        this.action = action;
        this.description = description;
    }

    public LocalDate getSentAt() {
        return sentAt;
    }

    public String getAction() {
        return action;
    }

    public String getDescription() {
        return description;
    }

    public String getUuid() {
        return uuid;
    }
}
