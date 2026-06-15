package ai_agent.mcp_server.dto;

import ai_agent.mcp_server.enumconstant.AgentRoute;

import java.util.List;

public record AgentState(

        String userMessage,

        AgentRoute route,

        String toolResult,

        String documentContext,

        String finalResponse,

        List<String> executionHistory

) {
}
