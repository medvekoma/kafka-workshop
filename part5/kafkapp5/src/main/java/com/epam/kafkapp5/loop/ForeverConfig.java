package com.epam.kafkapp5.loop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ForeverConfig {
    @Bean
    public Forever foreverLoop() { return new Forever(); }

}
