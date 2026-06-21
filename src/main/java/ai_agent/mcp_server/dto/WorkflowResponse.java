package ai_agent.mcp_server.dto;

import ai_agent.mcp_server.enumconstant.AgentRoute;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record WorkflowResponse(
        String response,
        AgentRoute route,
        List<String> steps,
        boolean hasError,
        String errorMessage,
        List<String> memory,
        String checkpointId,
        String conversationId
) {
}
