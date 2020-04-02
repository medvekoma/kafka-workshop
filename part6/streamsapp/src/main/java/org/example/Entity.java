package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Entity {
    public String time;
    public String code;

    public static Entity fromString(String json) {
        try {
            return new ObjectMapper().readValue(json, Entity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String asString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
