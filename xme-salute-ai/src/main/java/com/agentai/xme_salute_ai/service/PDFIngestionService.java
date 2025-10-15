package com.agentai.xme_salute_ai.service;

import com.agentai.xme_salute_ai.PDF.PDFDocumentReader;
import com.agentai.xme_salute_ai.PDF.PDFDocumentWriter;
import com.agentai.xme_salute_ai.transformer.TextSplitter;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PDFIngestionService {

    private final PDFDocumentReader reader;
    private final TextSplitter transformer;
    private final PDFDocumentWriter writer;

    public PDFIngestionService(PDFDocumentReader reader, TextSplitter transformer, PDFDocumentWriter writer) {
        this.reader = reader;
        this.transformer = transformer;
        this.writer = writer;
    }

    public void ingest() {
        // ETL pipeline: Extract → Transform → Load
        List<Document> docs = reader.get();                 // Extract
        List<Document> chunks = transformer.apply(docs);    // Transform
        writer.accept(chunks);                              // Load
    }
}
