package ai_agent.mcp_server.config;

import ai_agent.mcp_server.dto.AgentState;
import org.springframework.stereotype.Component;

@Component
public class ErrorNode {


    public AgentState handle(Exception ex, AgentState state) {

        return new AgentState(
                state.userMessage(),
                state.route(),
                state.toolResult(),
                state.documentContext(),
                ex.getMessage()+ " Something went wrong while processing your request.  ",
                state.executionHistory(),
                null,
                false,
                state.memory(),
                state.checkpointId()
        );
    }

}
