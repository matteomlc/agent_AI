package com.agentai.xme_salute_ai.transformer;

import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentTransformer;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TextSplitter implements DocumentTransformer {

    @Override
    public List<Document> apply(List<Document> docs) {
        TokenTextSplitter splitter = new TokenTextSplitter();
        return splitter.apply(docs);
    }

}
