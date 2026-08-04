package com.eazybytes.section1.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MultiChatController {

    private final ChatClient googleGenAiChatClient;
    private final ChatClient ollamaChatClient;

    public MultiChatController(ChatClient googleGenAiChatClient, ChatClient ollamaChatClient) {
        this.googleGenAiChatClient = googleGenAiChatClient;
        this.ollamaChatClient = ollamaChatClient;
    }

    @GetMapping("/gemini/chat")
    public String geminiChat(@RequestParam("message") String message){
        return googleGenAiChatClient.prompt(message).call().content();
    }

    @GetMapping("/ollama/chat")
    public String chat(@RequestParam("message") String message){
        return ollamaChatClient.prompt(message).call().content();
    }
}
