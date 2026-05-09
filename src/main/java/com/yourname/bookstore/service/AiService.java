package com.yourname.bookstore.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    private final ChatClient chatClient;

    public AiService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String generateDescription(String title, String author) {
        String prompt = String.format("Generate a short, engaging marketing description (2-3 sentences) for a book titled '%s' by %s.", title, author);
        try {
            return chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();
        } catch (Exception e) {
            return "Unable to generate description at this time: " + e.getMessage();
        }
    }
}
