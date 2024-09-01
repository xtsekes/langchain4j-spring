package com.example.langchain.config.ollama;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class OllamaEmbeddingModelConfig {

    @Value("${ollama.base-url}")
    private String baseURL;

    @Value("${ollama.embedding-model-name}")
    private String embeddingModelName;

    @Bean
    EmbeddingModel ollamaEmbeddingModel() {
        return OllamaEmbeddingModel.builder()
                .baseUrl(baseURL)
                .modelName(embeddingModelName)
                .build();
    }
}
