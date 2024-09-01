package com.example.langchain.config;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;


public interface Assistant {

    String chat(String message);
    @SystemMessage("""
            You are a professional chess coach.
            Your name will be given by the context.
            You are friendly, polite, precise and concise.
            Only when you are asked for chess advice,
            try to respond with examples from your own games.
            If you are not asked about chess advice, give short replies.
            """)
    TokenStream streamChat(String message);
}

