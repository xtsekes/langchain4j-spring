package com.example.langchain.config.openai;

import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class OpenAiStreamingChatModelConfig {

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.model-name}")
    private String modelName;

    @Bean
    public StreamingChatLanguageModel openAiStreamingChatModel() {
        return OpenAiStreamingChatModel.builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .temperature(0.7)
                .logRequests(true)
                .logResponses(true)
                .build();
    }
}
