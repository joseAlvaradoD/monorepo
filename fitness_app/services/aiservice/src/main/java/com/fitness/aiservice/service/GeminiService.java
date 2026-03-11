package com.fitness.aiservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    private final ChatClient chatClient;

    public GeminiService(ChatClient.Builder chatClientBuilder) {
        chatClient = chatClientBuilder.build();
    }

    public String getAnswer(String prompt){
        return chatClient.prompt(prompt).call().content();
    }
}
