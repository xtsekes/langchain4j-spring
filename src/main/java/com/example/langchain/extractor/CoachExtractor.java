package com.example.langchain.extractor;

import dev.langchain4j.service.UserMessage;

public interface CoachExtractor {

    @UserMessage("""
             Extract the info for the Coach described below.
             Return strictly only JSON, without any markdown markup surrounding it.
             Here is the document describing the Coach:
             ---
             {{it}}
            """)
    Coach extract(String context);
}
