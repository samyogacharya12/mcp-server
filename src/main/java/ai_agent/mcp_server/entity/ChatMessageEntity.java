package ai_agent.mcp_server.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "chat_history")
@Data
public class ChatMessageEntity {

    @Id
    private String id;

    @Indexed
    private String conversationId;

    private String role;
    private String content;
    private Instant createdAt;


    public ChatMessageEntity(String conversationId,
                             String role,
                             String content,
                             Instant createdAt){
        this.conversationId=conversationId;
        this.role=role;
        this.content=content;
        this.createdAt=createdAt;

    }
}
