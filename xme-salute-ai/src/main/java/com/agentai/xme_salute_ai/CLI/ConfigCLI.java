package com.agentai.xme_salute_ai.CLI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ConfigCLI {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

}
