package ai_agent.mcp_server.repository;

import ai_agent.mcp_server.entity.ChatMessageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessageEntity, String> {


    List<ChatMessageEntity> findByConversationIdOrderByCreatedAtAsc(String conversationId);

}
