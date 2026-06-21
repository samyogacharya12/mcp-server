package ai_agent.mcp_server.controller;

import ai_agent.mcp_server.dto.AgentState;
import ai_agent.mcp_server.dto.Payload;
import ai_agent.mcp_server.dto.WorkflowResponse;
import ai_agent.mcp_server.service.AgentWorkflowService;
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


    public AgentWorkflowController(
            AgentWorkflowService agentWorkflowService
    ){
        this.agentWorkflowService =
                agentWorkflowService;
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
                        null
                );


        return agentWorkflowService
                .execute(initialState, new Exception());

    }
}
