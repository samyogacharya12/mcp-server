package ai_agent.mcp_server.config;

import ai_agent.mcp_server.dto.AgentState;
import ai_agent.mcp_server.service.AiAgentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatNode {


    private final AiAgentService aiAgentService;

    public ChatNode(AiAgentService aiAgentService) {
        this.aiAgentService = aiAgentService;
    }

    public AgentState execute(AgentState state) {

        String response = aiAgentService.chat(
                state.userMessage()
        );
        List<String> steps = new ArrayList<>(state.executionHistory());
        steps.add("ChatNode generated normal AI response");

        return new AgentState(
                state.userMessage(),
                state.route(),
                state.toolResult(),
                state.documentContext(),
                response,
                steps
        );
    }
}
