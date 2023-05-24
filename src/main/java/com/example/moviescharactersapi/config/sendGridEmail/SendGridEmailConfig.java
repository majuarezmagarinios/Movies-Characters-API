package com.example.moviescharactersapi.config.sendGridEmail;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendGridEmailConfig {

    @Value( "${spring.sendgrid.api-key}")
    private String apiKey;

    @Bean
    public SendGrid sendGrid() {
        return new SendGrid( apiKey );
    }

}