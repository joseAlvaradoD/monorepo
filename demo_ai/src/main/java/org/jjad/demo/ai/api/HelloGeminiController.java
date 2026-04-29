package org.jjad.demo.ai.api;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloGeminiController {

    private ChatClient chatClient;

    public HelloGeminiController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping
    public String helloGemini() {
        return chatClient.prompt("Hello Gemini!, tell me something about August 13, 1987.").call().content();
    }
}
