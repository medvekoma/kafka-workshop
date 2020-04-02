package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EnrichedEntity {
    public String time;
    public String code;
    public String country;

    public EnrichedEntity() {}

    public EnrichedEntity(String entityString, String country) {
        Entity entity = Entity.fromString(entityString);
        this.time = entity.time;
        this.code = entity.code;
        this.country = country;
    }

    public String asString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
