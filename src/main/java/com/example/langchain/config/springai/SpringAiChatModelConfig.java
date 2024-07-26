package com.example.langchain.config.springai;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringAiChatModelConfig {

    @Bean
    public ChatModel springAiChatModel() {
        return new OllamaChatModel(new OllamaApi(),
                OllamaOptions.create()
                        .withModel("phi3")
                        .withTemperature(0.7f)
        );
    }
}
