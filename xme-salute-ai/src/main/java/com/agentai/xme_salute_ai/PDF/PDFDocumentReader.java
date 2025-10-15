package com.agentai.xme_salute_ai.PDF;

import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PDFDocumentReader implements DocumentReader {

    private final String folderPath;

    public PDFDocumentReader(@Value("${app.pdf.folder}") String folderPath) {
        this.folderPath = folderPath;
    }

    @Override
    public List<Document> get() {
        // puoi leggere tutti i PDF nella cartella e convertire in Document
        PagePdfDocumentReader reader = new PagePdfDocumentReader(
                folderPath + "/dip_capofamiglia_1.pdf", // TODO: add other files
                PdfDocumentReaderConfig.builder()
                        .withPagesPerDocument(1)
                        .build()
        );
        return reader.read();
    }

}
