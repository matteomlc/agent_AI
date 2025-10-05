
package com.agentai.xme_salute_ai.controller;

import com.agentai.xme_salute_ai.service.ChatService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    /**
     *
     * Endpoint /api/chat/ask.
     *
     * Metodo POST
     *
     * @param conversationId
     * @param input
     * @return
     */
    @PostMapping("/ask")
    public Mono<String> askPost(@RequestParam String conversationId, @RequestBody String input) {
        return Mono.just(chatService.ask(conversationId, input));
    }

    /**
     *
     * Endpoint /api/chat/ask/stream.
     *
     * Metodo POST
     *
     * @param conversationId
     * @param input
     * @return
     */
    @PostMapping(value = "/ask/stream", produces = MediaType.TEXT_PLAIN_VALUE)
    public Flux<String> askStreamPost(@RequestParam String conversationId, @RequestBody String input) {
        return chatService.askStreaming(conversationId, input);
    }

    /**
     *
     * Endpoint /api/chat/ask.
     *
     * Metodo GET
     *
     * @param conversationId
     * @param input
     * @return
     */
    @GetMapping("/ask")
    public Mono<String> askGet(@RequestParam String conversationId, @RequestParam String input) {
        return Mono.just(chatService.ask(conversationId, input));
    }

    /**
     *
     * Endpoint /api/chat/ask/stream.
     *
     * Metodo GET
     *
     * @param conversationId
     * @param input
     * @return
     */
    @GetMapping(value = "/ask/stream", produces = MediaType.TEXT_PLAIN_VALUE)
    public Flux<String> askStreamGet(@RequestParam String conversationId, @RequestParam String input) {
        return chatService.askStreaming(conversationId, input);
    }

    /**
     *
     * Endpoint /api/chat/reset.
     *
     * Cancella lo storico della conversazione.
     *
     * @param conversationId
     * @return
     */
    @DeleteMapping("/reset")
    public Mono<Void> resetConversation(@RequestParam String conversationId) {
        chatService.resetConversation(conversationId);
        return Mono.empty();
    }

}
