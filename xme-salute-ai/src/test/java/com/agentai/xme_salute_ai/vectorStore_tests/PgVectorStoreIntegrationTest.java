package com.agentai.xme_salute_ai.vectorStore_tests;


import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class PgVectorStoreIntegrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void manualInsertAndSearch() {

        // -> Creazione del testo
        String text = "La salute è importante per il benessere generale.";
        List<Double> embedding = generateMockEmbedding(1536);

        // -> Inserimento
        String insertSql = "INSERT INTO vector_store (content, embedding) VALUES (?, CAST(? AS vector))";
        jdbcTemplate.update(insertSql, text, toPgVector(embedding));
        System.out.println("-> Inserito documento con embedding di dimensione " + embedding.size());

        // -> Ricerca per similarità
        List<Double> queryEmbedding = generateMockEmbedding(1536);
        List<String> results = jdbcTemplate.queryForList(
                """
                SELECT content
                FROM vector_store
                ORDER BY embedding <-> CAST(? AS vector)
                LIMIT 1
                """,
                String.class,
                toPgVector(queryEmbedding)
        );

        assertFalse(results.isEmpty());
        System.out.println("-> Documento simile trovato: " + results.get(0));
    }

    private String toPgVector(List<Double> vector) {
        return "[" + vector.stream()
                .map(d -> String.format(Locale.US, "%.6f", d))
                .collect(Collectors.joining(", "))
                + "]";
    }

    private List<Double> generateMockEmbedding(int dim) {
        Random random = new Random();
        return random.doubles(dim, -1.0, 1.0).boxed().toList();
    }
}
