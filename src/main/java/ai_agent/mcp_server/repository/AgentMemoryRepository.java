package ai_agent.mcp_server.repository;

import ai_agent.mcp_server.entity.AgentMemory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AgentMemoryRepository extends MongoRepository<AgentMemory, String> {

    List<AgentMemory> findByConversationIdOrderByCreatedAtAsc(
            String conversationId
    );

    void deleteByConversationId(String conversationId);
}
