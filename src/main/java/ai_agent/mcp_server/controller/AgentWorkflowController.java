package ai_agent.mcp_server.controller;

import ai_agent.mcp_server.dto.AgentState;
import ai_agent.mcp_server.dto.ApprovalRequest;
import ai_agent.mcp_server.dto.Payload;
import ai_agent.mcp_server.dto.WorkflowResponse;
import ai_agent.mcp_server.service.AgentWorkflowService;
import ai_agent.mcp_server.service.ApprovalService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/api/workflow")
public class AgentWorkflowController {


    private final AgentWorkflowService agentWorkflowService;

    private final ApprovalService approvalService;

    public AgentWorkflowController(
            AgentWorkflowService agentWorkflowService,
            ApprovalService approvalService
    ){
        this.agentWorkflowService =
                agentWorkflowService;
        this.approvalService =
                approvalService;
    }


    @PostMapping("/chat")
    public WorkflowResponse chat(
            @RequestBody Payload payload
    ){

        String conversationId = payload.getConversionId();

        if (conversationId == null || conversationId.isBlank()) {
            conversationId = UUID.randomUUID().toString();
        }

        AgentState initialState =
                new AgentState(
                        conversationId,
                        payload.getMessage(),
                        null,
                        null,
                        null,
                        null,
                        new ArrayList<>(),
                        null,
                        false,
                        new ArrayList<>(),
                        null,
                        0, false);


        return agentWorkflowService
                .execute(initialState, new Exception());

    }


    @PostMapping("/approve")
    public WorkflowResponse approve(
            @RequestBody ApprovalRequest request
    ) {
        return approvalService.approveAction(request);
    }
}
