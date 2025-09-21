package com.agentai.xme_salute_ai.test.api_checker;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyChecker {


    @Value("${spring.ai.openai.api-key}") // legge dal file application.yml la chiave
    private String apiKey;

    @PostConstruct // annotazione per l'esecuzione del metodo non appena il bean viene inizializzato
    public void init() {
        System.out.println("✅ OpenAI API Key caricata (inizia con): " + apiKey.substring(0, 30) + "...");
    }

}
