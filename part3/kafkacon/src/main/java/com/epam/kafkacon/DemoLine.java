package com.epam.kafkacon;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DemoLine {
    private Integer rand;
    private String time;
    private String text;

    public Integer getRand() {
        return rand;
    }

    public String getTime() {
        return time;
    }

    public String getText() {
        return text;
    }

    public static DemoLine fromString(String json) {
        try {
            return new ObjectMapper().readValue(json, DemoLine.class);
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
