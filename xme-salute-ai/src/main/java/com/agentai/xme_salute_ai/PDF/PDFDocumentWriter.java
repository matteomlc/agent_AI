package com.agentai.xme_salute_ai.PDF;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentWriter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PDFDocumentWriter implements DocumentWriter {

    private final JdbcTemplate jdbc;

    public PDFDocumentWriter(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void accept(List<Document> docs) {
        String sql = "INSERT INTO vector_store (content, metadata, embedding) " +
                "VALUES (?, ?::jsonb, pgml.embed('intfloat/e5-small-v2', ?, '{}'::jsonb))";
        for (Document d : docs) {
            String content = d.getFormattedContent();
            String metadataJson = null;
            try {
                metadataJson = convertMetadataToJson(d.getMetadata());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            jdbc.update(sql, content, metadataJson, content);
        }
    }

    private String convertMetadataToJson(Map<String, Object> metadata) throws JsonProcessingException {
        // Usa la tua libreria preferita (Jackson) per convertire Map → JSON string
        return new ObjectMapper().writeValueAsString(metadata);
    }
}
