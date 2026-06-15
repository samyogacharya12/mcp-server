package ai_agent.mcp_server.config;

import ai_agent.mcp_server.dto.AgentState;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ResponseNode {

    public AgentState generate(AgentState state) {

        String finalResponse;

        if (state.finalResponse() != null && !state.finalResponse().isBlank()) {
            finalResponse = state.finalResponse();
        } else if (state.toolResult() != null && !state.toolResult().isBlank()) {
            finalResponse = state.toolResult();
        } else if (state.documentContext() != null && !state.documentContext().isBlank()) {
            finalResponse = state.documentContext();
        } else {
            finalResponse = "I could not generate a response.";
        }

        List<String> steps = new ArrayList<>(state.executionHistory());
        steps.add("ResponseNode prepared final response");

        return new AgentState(
                state.userMessage(),
                state.route(),
                state.toolResult(),
                state.documentContext(),
                finalResponse,
                steps
        );
    }
}
