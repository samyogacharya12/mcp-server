package ai_agent.mcp_server.service;

import ai_agent.mcp_server.dto.AgentState;
import ai_agent.mcp_server.entity.WorkflowExecution;
import ai_agent.mcp_server.repository.WorkflowExecutionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WorkflowExecutionService {

    private final WorkflowExecutionRepository workflowExecutionRepository;

    public WorkflowExecutionService(
            WorkflowExecutionRepository workflowExecutionRepository
    ) {
        this.workflowExecutionRepository = workflowExecutionRepository;
    }

    public WorkflowExecution save(AgentState state) {

        WorkflowExecution execution =
                new WorkflowExecution(
                        state.conversationId(),
                        state.checkpointId(),
                        state.route(),
                        state.executionHistory(),
                        state.hasError(),
                        state.errorMessage(),
                        LocalDateTime.now()
                );

        return workflowExecutionRepository.save(execution);
    }

    public List<WorkflowExecution> getExecutionByConversationId(
            String conversationId
    ) {
        return workflowExecutionRepository
                .findByConversationIdOrderByCreatedAtDesc(conversationId);
    }

    public List<WorkflowExecution> getExecutionByCheckpointId(
            String checkpointId
    ) {
        return workflowExecutionRepository.findByCheckpointId(checkpointId);
    }

    public void deleteByConversationId(String conversationId) {
        workflowExecutionRepository.deleteByConversationId(conversationId);
    }

}
