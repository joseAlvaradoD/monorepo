package com.eazybytes.section1.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient googleGenAiChatClient(GoogleGenAiChatModel googleGenAiChatModel){
        return ChatClient.create(googleGenAiChatModel);
    }

    @Bean
    public ChatClient ollamaChatClient(OllamaChatModel ollamaChatModel){
        ChatClient.Builder chatClientBuilder = ChatClient.builder(ollamaChatModel);
        return chatClientBuilder.build();
    }

    @Bean ChatClient.Builder chatClientBuilder(GoogleGenAiChatModel googleGenAiChatModel){
        ChatClient.Builder chatClientBuilder = ChatClient.builder(googleGenAiChatModel);
        return chatClientBuilder;
    }

}
