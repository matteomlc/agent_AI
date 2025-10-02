package com.agentai.xme_salute_ai.CLI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Scanner;
import java.util.UUID;

/*
@Component
public class ChatCLI implements CommandLineRunner {

    private final WebClient webClient;

    public ChatCLI(WebClient.Builder webClientBuilder,
                   @Value("${chat.base-url:http://localhost:8080/api/chat}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    @Override
    public void run(String... args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        String conversationId = askForConversationId(scanner);
        boolean streaming = false;

        System.out.println("Comandi: /exit, /reset, /switch <id>, /stream on, /stream off");
        while (true) {
            System.out.print("You (" + conversationId + "): ");
            String line = scanner.nextLine();
            if (line == null) break;
            line = line.trim();
            if (line.isEmpty()) continue;

            // comandi
            if (line.equalsIgnoreCase("/exit")) break;

            if (line.startsWith("/switch")) {
                String[] parts = line.split("\\s+", 2);
                conversationId = (parts.length > 1 && !parts[1].isBlank()) ? parts[1] : UUID.randomUUID().toString();
                System.out.println("ConversationId -> " + conversationId);
                continue;
            }

            if (line.equalsIgnoreCase("/reset")) {
                String currentConvId = conversationId;
                webClient.delete()
                        .uri(uriBuilder -> uriBuilder.path("/reset")
                                .queryParam("conversationId", currentConvId)
                                .build())
                        .retrieve()
                        .bodyToMono(Void.class)
                        .doOnError(e -> System.out.println("Reset failed: " + e.getMessage()))
                        .block();
                System.out.println("Conversation reset: " + conversationId);
                continue;
            }

            if (line.equalsIgnoreCase("/stream on")) { streaming = true; System.out.println("Streaming ON"); continue; }
            if (line.equalsIgnoreCase("/stream off")) { streaming = false; System.out.println("Streaming OFF"); continue; }

            // invio messaggio (GET /ask oppure GET /ask/stream)
            if (!streaming) {
                String currentConvId = conversationId;
                String finalLine1 = line;
                String response = webClient.get()
                        .uri(uriBuilder -> uriBuilder.path("/ask")
                                .queryParam("conversationId", currentConvId)
                                .queryParam("input", finalLine1)
                                .build())
                        .retrieve()
                        .bodyToMono(String.class)
                        .onErrorReturn("Errore nella richiesta")
                        .block();
                System.out.println("\nAI: " + response + "\n");
            } else {
                String currentConvId = conversationId;
                // streaming: stampiamo i chunk man mano che arrivano
                String finalLine = line;
                Flux<String> flux = webClient.get()
                        .uri(uriBuilder -> uriBuilder.path("/ask/stream")
                                .queryParam("conversationId", currentConvId)
                                .queryParam("input", finalLine)
                                .build())
                        .retrieve()
                        .bodyToFlux(String.class);

                // stampiamo token per token e aspettiamo la fine dello stream
                flux.doOnNext(token -> System.out.print(token))
                        .doOnError(e -> System.out.println("\n[stream error] " + e.getMessage()))
                        .doOnComplete(() -> System.out.println("\n"))
                        .blockLast(); // blocca finché lo stream non è completo
            }
        }

        System.out.println("Bye.");
        // Non chiamiamo System.exit per lasciare il contesto vivo; opzionale

    }

    private String askForConversationId(Scanner scanner) {
        System.out.print("ConversationId (premi invio per generarne uno): ");
        String id = scanner.nextLine();
        if (id == null || id.isBlank()) {
            id = UUID.randomUUID().toString();
            System.out.println("Generato conversationId: " + id);
        }
        return id.trim();
    }

}
 */