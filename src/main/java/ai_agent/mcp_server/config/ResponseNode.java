package ai_agent.mcp_server.config;

import ai_agent.mcp_server.dto.AgentState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ResponseNode {

    public AgentState generate(AgentState state, Exception exception) {
        log.info("generate agent state");
        try {
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
                    state.conversationId(),
                    state.userMessage(),
                    state.route(),
                    state.toolResult(),
                    state.documentContext(),
                    finalResponse,
                    steps,
                    exception.getMessage(),
                    false,
                    state.memory(),
                    state.checkpointId()
            );
        } catch (Exception e) {
            log.error("generate agent state", e);
            return new AgentState(
                    state.conversationId(),
                    state.userMessage(),
                    state.route(),
                    state.toolResult(),
                    state.documentContext(),
                    null,
                    null,
                    exception.getMessage(),
                    true,
                    state.memory(),
                    state.checkpointId());
        }
    }
}
