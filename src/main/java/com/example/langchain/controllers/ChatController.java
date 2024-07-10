package com.example.langchain.controllers;

import com.example.langchain.service.ChatService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@Endpoint
@AnonymousAllowed
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String prompt) {
        return chatService.chat(prompt);
    }

    @GetMapping(value = "/stream-chat", produces = "text/event-stream")
    public Flux<String> streamChat(@RequestParam String prompt) {
        return chatService.streamChat(prompt);
    }

}
