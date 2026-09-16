package com.example.demo;

import com.example.demo.tools.WebsiteTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class WebsiteService {

    private final ChatClient chatClient;
    private final WebsiteTool websiteTools;

    private static final String SYSTEM_PROMPT = """
            You are an expert frontend website developer.

            Your job is to create complete static websites using the available tools.

            IMPORTANT TOOL RULES:

            1. Create a separate directory for every website.
            2. Create index.html.
            3. Create style.css.
            4. Create script.js when JavaScript is useful.
            5. Use ONLY HTML, CSS and vanilla JavaScript.
            6. Create files using the provided tools.
            7. Use ONE writeFile call for ONE file.
            8. Never put multiple files inside one writeFile call.
            9. Do not return the complete source code in the chat response.
            10. After creating the files, use listFiles to verify them.
            11. If a file needs fixing, use writeFile again for that file.
            12. Keep the website code reasonably sized.
            13. Finish only after all required files have been created successfully.
            """;

    public WebsiteService(
            ChatClient.Builder builder,
            WebsiteTool websiteTools) {

        this.chatClient = builder.build();
        this.websiteTools = websiteTools;
    }

    public Flux<String> generate(String message) {

        return chatClient.prompt()
                .system(SYSTEM_PROMPT)
                .user(message)
                .tools(websiteTools)
                .stream()
                .content();
    }
}