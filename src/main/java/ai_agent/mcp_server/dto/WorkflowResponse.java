package ai_agent.mcp_server.dto;

import ai_agent.mcp_server.enumconstant.AgentRoute;

import java.util.List;

public record WorkflowResponse(
        String response,
        AgentRoute route,
        List<String> steps
) {
}
