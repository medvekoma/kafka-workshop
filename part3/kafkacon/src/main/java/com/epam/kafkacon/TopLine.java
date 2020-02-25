package com.epam.kafkacon;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class TopLine {
    private Long pid;
    private String app;
    private String mem;

    public Long getPid() {
        return pid;
    }

    public String getApp() {
        return app;
    }

    public String getMem() {
        return mem;
    }

    public static TopLine fromString(String text) {
        try {
            return new ObjectMapper().readValue(text, TopLine.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void dump() {
        try {
            System.out.println(new ObjectMapper().writeValueAsString(this));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
