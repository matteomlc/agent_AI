/*
package com.agentai.xme_salute_ai.vectorStore;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class PgVectorStoreConfig {

    @Value("${app.llm.provider}")
    private String provider;

    @Bean
    public PgVectorStore pgVectorStore(
            JdbcTemplate dataSource,
            OpenAiEmbeddingModel openAiEmbeddingModel,
            OllamaEmbeddingModel ollamaEmbeddingModel) {

        EmbeddingModel chosenModel = switch (provider) {
            case "docker" -> openAiEmbeddingModel; // come nel tuo ChatModelConfig
            case "ollama" -> ollamaEmbeddingModel;
            default -> throw new IllegalArgumentException("Provider non supportato: " + provider);
        };

        return PgVectorStore.builder(dataSource, chosenModel)
                .build();
    }

}
 */