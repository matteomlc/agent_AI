package com.agentai.xme_salute_ai.vectorStore_tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class VectorStoreSemanticTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void testSemanticSearch() {
        // Frase di test da usare per la ricerca semantica
        String queryText = "Denuncia del sinistro";

        // Query semantica usando lo stesso modello degli embeddings caricati (intfloat/e5-small-v2)
        String sql = "SELECT id, content, metadata, embedding <=> pgml.embed('intfloat/e5-small-v2', ?, '{}'::jsonb)::vector AS distance " +
                "FROM vector_store " +
                "ORDER BY distance " +
                "LIMIT 5";

        // Esecuzione query
        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, queryText);

        // Stampa risultati
        System.out.println("=== Risultati Semantic Search ===");
        for (Map<String, Object> row : results) {
            System.out.println("ID: " + row.get("id"));
            System.out.println("Content: " + row.get("content"));
            System.out.println("Metadata: " + row.get("metadata"));
            System.out.println("Distance: " + row.get("distance"));
            System.out.println("-----------------------------------");
        }
    }
}
