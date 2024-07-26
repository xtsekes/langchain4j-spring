package com.example.langchain.service;

import com.example.langchain.config.Assistant;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class ChatService {

    private final ChatLanguageModel chatLanguageModel;
    private final StreamingChatLanguageModel streamingChatLanguageModel;
    private final Assistant assistant;
    private final ChatModel chatModel;

    public ChatService(ChatLanguageModel chatLanguageModel, StreamingChatLanguageModel streamingChatLanguageModel, Assistant assistant, ChatModel chatModel) {
        this.chatLanguageModel = chatLanguageModel;
        this.streamingChatLanguageModel = streamingChatLanguageModel;
        this.assistant = assistant;
        this.chatModel = chatModel;
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

    public Flux<String> streamAssistant(String prompt) {
        Sinks.Many<String> sink = Sinks.many().unicast().onBackpressureBuffer();

        assistant.streamChat(prompt)
                .onNext(sink::tryEmitNext)
                .onComplete(c -> sink.tryEmitComplete())
                .onError(sink::tryEmitError)
                .start();

        return sink.asFlux();
    }

    public String chatModel(String prompt) {
        return chatModel.call(prompt);
    }

    public Flux<String> streamModel(String prompt) {
        return chatModel.stream(prompt);
    }

}
