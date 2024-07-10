package com.example.langchain.service;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class ChatService {

    private final ChatLanguageModel chatLanguageModel;
    private final StreamingChatLanguageModel streamingChatLanguageModel;

    public ChatService(ChatLanguageModel chatLanguageModel, StreamingChatLanguageModel streamingChatLanguageModel) {
        this.chatLanguageModel = chatLanguageModel;
        this.streamingChatLanguageModel = streamingChatLanguageModel;
    }

    public String chat(String prompt) {
        return chatLanguageModel.generate(prompt);
    }

    public Flux<String> streamChat(String prompt) {
        Sinks.Many<String> sink = Sinks.many().unicast().onBackpressureBuffer();

        streamingChatLanguageModel.generate(prompt, new StreamingResponseHandler<AiMessage>() {
            @Override
            public void onNext(String token) {
                sink.tryEmitNext(token);
            }

            @Override
            public void onError(Throwable error) {
                sink.tryEmitError(error);
            }
        });

        return sink.asFlux();
    }

}
