package ai_agent.mcp_server.dto;

public record ApprovalRequest(String conversationId,
                              String checkpointId,
                              boolean approved) {
}
