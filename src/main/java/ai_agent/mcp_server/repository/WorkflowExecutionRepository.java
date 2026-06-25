package ai_agent.mcp_server.repository;

import ai_agent.mcp_server.entity.WorkflowExecution;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowExecutionRepository extends MongoRepository<WorkflowExecution, String> {

    List<WorkflowExecution> findByConversationIdOrderByCreatedAtDesc(
            String conversationId
    );

    List<WorkflowExecution> findByCheckpointId(
            String checkpointId
    );

    void deleteByConversationId(String conversationId);

    long countByHasError(boolean hasError);

}
