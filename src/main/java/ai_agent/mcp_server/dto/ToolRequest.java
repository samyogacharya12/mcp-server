package ai_agent.mcp_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolRequest {

    private String userId;
    private String agentId;
    private String toolName;
    private String input;
}
