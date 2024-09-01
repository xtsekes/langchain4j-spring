package com.example.langchain.controllers;

import com.example.langchain.service.ChatService;
import com.example.langchain.service.EmbeddingService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import java.net.MalformedURLException;

@RestController
@Endpoint
@AnonymousAllowed
public class ChatController {

    private final ChatService chatService;
    private final EmbeddingService embeddingService;

    public ChatController(ChatService chatService, EmbeddingService embeddingService) {
        this.chatService = chatService;
        this.embeddingService = embeddingService;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String prompt) {
        return chatService.chat(prompt);
    }

    @GetMapping(value = "/stream-chat", produces = "text/event-stream")
    public Flux<String> streamChat(@RequestParam String prompt) {
        return chatService.streamChat(prompt);
    }

    @GetMapping(value = "/stream-assistant", produces = "text/event-stream")
    public Flux<String> streamAssistant(@RequestParam String prompt) {
        return chatService.streamAssistant(prompt);
    }

    @PostMapping("/ingest-data")
    public String ingestInfo(@RequestParam String prompt) {
        try {
            embeddingService.ingestInfo(prompt);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        return "Data ingested successfully";
    }

    @GetMapping("/extract-data")
    public String extractData(String prompt) {

        // The name of the coach we want to extract the info
        EmbeddingSearchResult<TextSegment> context = embeddingService.retrieveInfo(prompt);
        // Retrieval is set up to  give at most 3 results.
        // We aggregate them and use them all as context.
        StringBuilder sb = new StringBuilder();
        for (EmbeddingMatch<TextSegment> match : context.matches()) {
            sb.append(match.embedded().text()).append("\n");
        }

        return chatService.extractCoach(sb.toString());
    }

}
