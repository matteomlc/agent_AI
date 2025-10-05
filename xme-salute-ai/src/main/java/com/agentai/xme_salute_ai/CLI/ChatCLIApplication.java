package com.agentai.xme_salute_ai.CLI;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Scanner;
import java.util.UUID;

public class ChatCLIApplication {

    public static void main(String[] args) {
        // Avvia solo il contesto Spring senza server web
        var context = new SpringApplicationBuilder()
                .sources(ConfigCLI.class)   // classe @Configuration
                .web(WebApplicationType.NONE)
                .run(args);

        WebClient webClient = context.getBean(WebClient.Builder.class)
                .baseUrl("http://localhost:8080/api/chat")
                .build();

        Scanner scanner = new Scanner(System.in);
        String conversationId = UUID.randomUUID().toString();
        boolean streaming = false;

        System.out.println("CLI avviata. /exit, /reset, /switch <id>, /stream on|off");
        while (true) {
            System.out.print("You (" + conversationId + "): ");
            String line = scanner.nextLine();

            if (line.equalsIgnoreCase("/exit")) break;
            if (line.startsWith("/switch")) {
                conversationId = line.split("\\s+").length > 1
                        ? line.split("\\s+")[1]
                        : UUID.randomUUID().toString();
                System.out.println("Nuovo conversationId: " + conversationId);
                continue;
            }
            if (line.equalsIgnoreCase("/reset")) {
                String finalConversationId = conversationId;
                webClient.delete()
                        .uri(uri -> uri.path("/reset")
                                .queryParam("conversationId", finalConversationId)
                                .build())
                        .retrieve()
                        .toBodilessEntity()
                        .block();
                System.out.println("Reset per: " + conversationId);
                continue;
            }
            if (line.equalsIgnoreCase("/stream on")) { streaming = true; continue; }
            if (line.equalsIgnoreCase("/stream off")) { streaming = false; continue; }

            if (!streaming) {
                String finalConversationId1 = conversationId;
                String resp = webClient.get()
                        .uri(uri -> uri.path("/ask")
                                .queryParam("conversationId", finalConversationId1)
                                .queryParam("input", line)
                                .build())
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
                System.out.println("AI: " + resp);
            } else {
                String finalConversationId2 = conversationId;
                Flux<String> flux = webClient.get()
                        .uri(uri -> uri.path("/ask/stream")
                                .queryParam("conversationId", finalConversationId2)
                                .queryParam("input", line)
                                .build())
                        .retrieve()
                        .bodyToFlux(String.class);

                flux.doOnNext(System.out::print)
                        .doOnComplete(() -> System.out.println())
                        .doOnComplete(() -> System.out.println("\n"))
                        .blockLast();
            }
        }
        System.out.println("Bye!");
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