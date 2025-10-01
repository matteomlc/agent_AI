package com.agentai.xme_salute_ai.service;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ChatService {

    private final ChatModel chatModel;

    public ChatService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String ask(String input) {
        Prompt prompt = new Prompt(new UserMessage(input));
        ChatResponse resp = chatModel.call(prompt);
        return resp.getResults().get(0).getOutput().getText();
    }

    public Flux<String> askStreaming(String input) {
        Prompt prompt = new Prompt(new UserMessage(input));
        return this.chatModel.stream(prompt)
                .map(cr ->cr.getResults().get(0).getOutput().getText());
    }
}
