package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DemoLine {
    public Integer rand;
    public String time;
    public String text;

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
