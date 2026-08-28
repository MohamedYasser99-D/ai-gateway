package ae.mohamedyasser.ai_gateway.models;

import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ConversationMemory {


    private final ConcurrentHashMap<String, List<org.springframework.ai.chat.messages.Message>> conversations = new ConcurrentHashMap<>();

    public List<Message> getHistory(String conversationId) {
        return conversations.computeIfAbsent(conversationId,
                k -> Collections.synchronizedList(new ArrayList<>()));
    }

    public void append(String conversationId, Message message) {
        List<Message> history = getHistory(conversationId);
        history.add(message);
        while (history.size() > 20) {
            history.removeFirst();
        }
    }
}
