package ae.mohamedyasser.ai_gateway.controller;

import ae.mohamedyasser.ai_gateway.models.ChatRequest;
import ae.mohamedyasser.ai_gateway.models.ConversationMemory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChatController {
    private final ChatClient chatClient;
    private final ConversationMemory memory;

    public ChatController(ChatClient.Builder builder, ConversationMemory memory) {
        this.chatClient = builder.build();
        this.memory = memory;
    }

    @PostMapping("/chat")
    public String chat(@RequestBody ChatRequest conv) {
        var history = memory.getHistory(conv.conversationId());
        history.add(new UserMessage(conv.message()));
        String reply = chatClient.prompt()
                .messages(history)
                .call()
                .content();
        history.add(new AssistantMessage(reply));
        return reply;
    }
}