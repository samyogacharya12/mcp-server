package ai_agent.mcp_server.config;

import ai_agent.mcp_server.dto.AgentState;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MemoryNode {

    public AgentState loadMemory(AgentState state) {

        List<String> steps = new ArrayList<>(state.executionHistory());
        steps.add("MemoryNode loaded conversation memory");

        List<String> memory = new ArrayList<>();

        if (state.memory() != null) {
            memory.addAll(state.memory());
        }

        memory.add("User: " + state.userMessage());

        return new AgentState(
                state.userMessage(),
                state.route(),
                state.toolResult(),
                state.documentContext(),
                state.finalResponse(),
                steps,
                state.errorMessage(),
                state.hasError(),
                memory,
                state.checkpointId()
        );

    }

    public AgentState saveMemory(AgentState state) {

        List<String> steps = new ArrayList<>(state.executionHistory());
        steps.add("MemoryNode saved final response to memory");

        List<String> memory = new ArrayList<>(state.memory());

        memory.add("AI: " + state.finalResponse());

        return new AgentState(
                state.userMessage(),
                state.route(),
                state.toolResult(),
                state.documentContext(),
                state.finalResponse(),
                steps,
                state.errorMessage(),
                state.hasError(),
                memory,
                state.checkpointId()
        );
    }



}