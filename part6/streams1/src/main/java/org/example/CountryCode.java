package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CountryCode {
    public String time;
    public String code;

    public static CountryCode fromString(String json) {
        try {
            return new ObjectMapper().readValue(json, CountryCode.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
