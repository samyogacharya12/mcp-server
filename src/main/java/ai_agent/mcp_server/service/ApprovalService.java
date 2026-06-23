package ai_agent.mcp_server.service;

import ai_agent.mcp_server.dto.ApprovalRequest;
import ai_agent.mcp_server.dto.WorkflowResponse;
import ai_agent.mcp_server.enumconstant.AgentRoute;
import ai_agent.mcp_server.repository.AgentMemoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApprovalService {


    private final AgentMemoryRepository memoryRepository;

    public ApprovalService(AgentMemoryRepository memoryRepository) {
        this.memoryRepository = memoryRepository;
    }

    public WorkflowResponse approveAction(ApprovalRequest request) {

        if (!request.approved()) {
            return new WorkflowResponse(
                    "Approval denied. No action was executed.",
                    AgentRoute.HUMAN_APPROVAL,
                    List.of("ApprovalService denied requested action"),
                    false,
                    null,
                    List.of(),
                    request.checkpointId(),
                    request.conversationId(),
                    false
            );
        }

        memoryRepository.deleteByConversationId(
                request.conversationId()
        );

        return new WorkflowResponse(
                "Approved. Conversation history has been deleted.",
                AgentRoute.HUMAN_APPROVAL,
                List.of(
                        "ApprovalService received approval",
                        "Deleted conversation memory from MongoDB"
                ),
                false,
                null,
                List.of(),
                request.checkpointId(),
                request.conversationId(),
                false
        );
    }


}
