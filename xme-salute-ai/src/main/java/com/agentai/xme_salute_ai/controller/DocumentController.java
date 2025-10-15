package com.agentai.xme_salute_ai.controller;

import com.agentai.xme_salute_ai.service.PDFIngestionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final PDFIngestionService ingestionService;

    public DocumentController(PDFIngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @PostMapping("/ingest")
    public String ingest() {
        ingestionService.ingest();
        return "Documenti indicizzati!";
    }

}
