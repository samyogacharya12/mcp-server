package ai_agent.mcp_server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "agent_memory")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentMemory {

    @Id
    private String id;

    private String conversationId;

    private String role; // USER or AI

    private String message;

    private LocalDateTime createdAt;

    public AgentMemory(String conversationId, String role, String message) {
        this.conversationId = conversationId;
        this.role = role;
        this.message = message;
    }
}
