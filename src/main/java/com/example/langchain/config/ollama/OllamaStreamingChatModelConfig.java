package com.example.langchain.config.ollama;

import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class OllamaStreamingChatModelConfig {

    @Value("${ollama.base-url}")
    private String baseURL;

    @Value("${ollama.model-name}")
    private String modelName;

    @Bean
    StreamingChatLanguageModel ollamaStreamingChatModel() {
        return OllamaStreamingChatModel.builder()
                .baseUrl(baseURL)
                .modelName(modelName)
                .temperature(0.7)
                .logRequests(true)
                .logResponses(true)
                .build();
    }
}
