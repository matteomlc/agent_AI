package com.agentai.xme_salute_ai.service;

import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ChatService {

    private final ChatModel chatModel;
    private final ChatMemoryRepository chatMemory = new InMemoryChatMemoryRepository();

    public ChatService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    /**
     *
     * Metodo che recupera la conversazine di un utente salavto in memoria non persistente
     * tramite l'ID della conversazione e aggiunge il nuovo messaggio.
     *
     * @param conversationId
     * @param input
     * @return
     */
    public String ask(String conversationId, String input) {

        // recupera lo storico
        List<Message> history = new ArrayList<>(chatMemory.findByConversationId(conversationId));

        // aggiungi messaggio utente
        history.add(new UserMessage(input));

        // crea il prompt con tutta la storia
        Prompt prompt = new Prompt(history);

        // chiama il modello
        ChatResponse resp = chatModel.call(prompt);

        // ottieni messaggio assistente
        Message reply = resp.getResult().getOutput();

        // salva utente + risposta in memoria
        chatMemory.saveAll(conversationId, List.of(new UserMessage(input), reply));

        return reply.getText();
    }

    /**
     *
     * Metodo che stampa i messaggi sotto forma di flusso (token per token).
     *
     * Recupera anche lo storico di una conversazione tramite l'ID e aggiunge il nuovo messaggio.
     *
     * @param conversationId
     * @param input
     * @return
     */
    public Flux<String> askStreaming(String conversationId, String input) {
        // Recupera lo storico
        List<Message> history = new ArrayList<>(chatMemory.findByConversationId(conversationId));
        history.add(new UserMessage(input));

        Prompt prompt = new Prompt(history);

        // Chiamata sincrona al modello (una sola volta)
        ChatResponse response = chatModel.call(prompt);
        Message reply = response.getResult().getOutput();
        String fullText = reply.getText();

        // Salva conversazione (input + risposta completa)
        chatMemory.saveAll(conversationId, List.of(new UserMessage(input), reply));

        // Simula lo streaming dividendo la risposta in parole (o caratteri)
        return Flux.fromStream(Stream.of(fullText.split(" ")))
                .delayElements(Duration.ofMillis(100)) // regola la velocità
                .map(word -> word + " "); // aggiunge spazio tra parole
    }

    public void resetConversation(String conversationId) {
        chatMemory.deleteByConversationId(conversationId);
    }
}
