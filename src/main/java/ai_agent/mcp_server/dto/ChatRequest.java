package ai_agent.mcp_server.dto;

public record ChatRequest(
        String conversationId,
        String message
) {
}
