package ai_agent.mcp_server.dto;

import ai_agent.mcp_server.enumconstant.AgentRoute;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AgentState(
        String conversationId,

        String userMessage,

        AgentRoute route,

        String toolResult,

        String documentContext,

        String finalResponse,

        List<String> executionHistory,

        String errorMessage,

        boolean hasError,

        List<String> memory,

        String checkpointId,

        int retryCount,
        boolean approvalRequired
) {
}
