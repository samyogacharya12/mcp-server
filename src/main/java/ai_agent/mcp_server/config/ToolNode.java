package ai_agent.mcp_server.config;

import ai_agent.mcp_server.dto.AgentState;
import ai_agent.mcp_server.service.AiAgentService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ToolNode {


    private final AiAgentService aiAgentService;


    public ToolNode(
            AiAgentService aiAgentService
    ){
        this.aiAgentService = aiAgentService;
    }


    public AgentState execute(
            AgentState state
    ){

        String response =
                aiAgentService
                        .processWithMcpTools(
                                state.userMessage()
                        );

        List<String> steps = new ArrayList<>(state.executionHistory());
        steps.add("ToolNode executed MCP tool flow");


        return new AgentState(
                state.conversationId(),
                state.userMessage(),
                state.route(),
                response,
                state.documentContext(),
                response,
                steps,
                null,
                false,
                state.memory(),
                state.checkpointId(),
                state.retryCount(),
                state.approvalRequired(),
                0
        );

    }


}
