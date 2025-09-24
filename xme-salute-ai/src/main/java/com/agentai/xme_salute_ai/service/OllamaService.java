package com.agentai.xme_salute_ai.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

/*
@Service
public class OllamaService {

    private WebClient webClient;

    public OllamaService() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:11434/")
                .build();
    }

    public String call(String userMessage) {
        // Corpo della richiesta compatibile con Ollama
        Map<String, Object> requestBody = Map.of(
                "model", "gemma3:4b",        // Nome del modello Ollama installato
                "prompt", userMessage
        );

        // Chiamata all'API di Ollama
        Mono<String> response = webClient.post()
                .uri("/completions")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class);

        return response.block(); // blocca fino a ricevere la risposta
    }

}
*/