package ai_agent.mcp_server.dto;

import ai_agent.mcp_server.enumconstant.AgentRoute;

import java.util.Map;

public record WorkflowMetricsResponse(
        long totalExecutions,
        long successCount,
        long errorCount,
        Map<AgentRoute, Long> routeCounts
) {
}
