package com.agentai.xme_salute_ai.config;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class ChatModelConfig {

    @Value("${app.llm.provider}")
    private String provider;

    @Bean
    public ChatModel chatModel(
            OllamaChatModel ollama,
            OpenAiChatModel docker) {

        return switch (provider) {
            case "ollama" -> ollama;
            case "docker" -> docker;
            default -> throw new IllegalArgumentException("Provider non supportato: " + provider);
        };
    }

}
