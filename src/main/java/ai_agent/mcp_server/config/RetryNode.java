package ai_agent.mcp_server.config;

import ai_agent.mcp_server.dto.AgentState;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RetryNode {

    private static final int MAX_RETRY = 1;


    public boolean canRetry(AgentState state) {
        return state.retryCount() < MAX_RETRY;
    }

    public AgentState incrementRetry(AgentState state, Exception ex) {

        List<String> steps = new ArrayList<>(state.executionHistory());

        steps.add(
                "RetryNode retry attempt: " + (state.retryCount() + 1)
                        + " after error: "
                        + ex.getClass().getSimpleName()
        );

        return new AgentState(
                state.conversationId(),
                state.userMessage(),
                state.route(),
                state.toolResult(),
                state.documentContext(),
                state.finalResponse(),
                steps,
                ex.getMessage(),
                false,
                state.memory(),
                state.checkpointId(),
                state.retryCount() + 1,
                state.approvalRequired()
        );
    }





}
