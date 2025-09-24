package com.agentai.xme_salute_ai.controller;

import com.agentai.xme_salute_ai.service.ChatService;
import org.springframework.ai.chat.model.ChatResponse;
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

    @PostMapping("/ask")
    public Mono<String> askPost(@RequestBody String input) {
        return Mono.just(chatService.ask(input));
    }

    @PostMapping(value = "/ask/stream", produces = MediaType.TEXT_PLAIN_VALUE)
    public Flux<String> askStreamPost(@RequestBody String input) {
        return chatService.askStreaming(input)
                .doOnNext(t -> System.out.print(t))
                .doOnComplete(() -> System.out.println());
    }

    @GetMapping("/ask")
    public Mono<String> askGet(@RequestParam String input) {
        return Mono.just(chatService.ask(input));
    }

    @GetMapping(value = "/ask/stream", produces = MediaType.TEXT_PLAIN_VALUE)
    public Flux<String> askStreamGet(@RequestParam String input) {
        return chatService.askStreaming(input)
                .doOnNext(t -> System.out.print(t))
                .doOnComplete(() -> System.out.println());
    }

}
