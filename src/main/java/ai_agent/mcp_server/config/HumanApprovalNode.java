package ai_agent.mcp_server.config;

import ai_agent.mcp_server.dto.AgentState;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HumanApprovalNode {

    public AgentState requestApproval(AgentState state) {

        List<String> steps = new ArrayList<>(state.executionHistory());
        steps.add("HumanApprovalNode blocked risky action and requested approval");

        String response = """
                This action requires human approval before execution.

                Requested action:
                %s

                Please confirm approval before continuing.
                """.formatted(state.userMessage());

        return new AgentState(
                state.conversationId(),
                state.userMessage(),
                state.route(),
                state.toolResult(),
                state.documentContext(),
                response,
                steps,
                state.errorMessage(),
                state.hasError(),
                state.memory(),
                state.checkpointId(),
                state.retryCount(),
                true,
                0
        );
    }

}
