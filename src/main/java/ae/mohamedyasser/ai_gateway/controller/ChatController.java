package ae.mohamedyasser.ai_gateway.controller;

import ae.mohamedyasser.ai_gateway.models.ChatRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @PostMapping("/chat")
    public String chat(@RequestBody ChatRequest conv) {
        return chatClient.prompt()
                .user(conv.message())
                .call()
                .content();
    }
}