package ai_agent.mcp_server.config;

import ai_agent.mcp_server.dto.AgentState;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CheckpointNode {


    public AgentState createCheckpoint(AgentState state) {

        String checkpointId = UUID.randomUUID().toString();

        List<String> steps = new ArrayList<>(state.executionHistory());
        steps.add("CheckpointNode created checkpoint: " + checkpointId);

        return new AgentState(
                state.userMessage(),
                state.route(),
                state.toolResult(),
                state.documentContext(),
                state.finalResponse(),
                steps,
                state.errorMessage(),
                state.hasError(),
                state.memory(),
                checkpointId
        );
    }


}
