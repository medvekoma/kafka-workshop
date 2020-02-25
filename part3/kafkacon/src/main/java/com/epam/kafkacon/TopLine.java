package com.epam.kafkacon;

import com.fasterxml.jackson.databind.ObjectMapper;

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

    public void prettyPrint() {
        try {
            System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
