package com.example.langchain.service;

import com.example.langchain.config.Assistant;
import com.example.langchain.extractor.Coach;
import com.example.langchain.extractor.CoachExtractor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.service.AiServices;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class ChatService {

    private final ChatLanguageModel chatLanguageModel;
    private final StreamingChatLanguageModel streamingChatLanguageModel;
    private final Assistant assistant;
    private final CoachExtractor coachEctractor;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public ChatService(ChatLanguageModel chatLanguageModel, StreamingChatLanguageModel streamingChatLanguageModel, Assistant assistant, CoachExtractor coachEctractor) {
        this.chatLanguageModel = chatLanguageModel;
        this.streamingChatLanguageModel = streamingChatLanguageModel;
        this.assistant = assistant;
        this.coachEctractor = coachEctractor;
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

    public String extractCoach(String context) {
        return gson.toJson(coachEctractor.extract(context));
    }
}
