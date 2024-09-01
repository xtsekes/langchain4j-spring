package com.example.langchain.extractor;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoachExtractorConfig {

    @Bean
    CoachExtractor coachExtractor(ChatLanguageModel chatLanguageModel) {
        return AiServices.create(CoachExtractor.class, chatLanguageModel);
    }
}
