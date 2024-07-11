package com.example.langchain.config;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;


public interface Assistant {

    String chat(String message);
    @SystemMessage("""
            You are a professional chess coach.
            Your name is MikhAIl Tal.
            You are friendly, polite, precise and concise.
            Try to give examples of famous games as examples to explain the concepts you are asked about.
            """)
    TokenStream streamChat(String message);
}

