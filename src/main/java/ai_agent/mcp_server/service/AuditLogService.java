package ai_agent.mcp_server.service;

import ai_agent.mcp_server.dto.ToolRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditLogService {


    public void logToolCall(ToolRequest request, String status) {
        System.out.println("AUDIT LOG");
        System.out.println("Time: " + LocalDateTime.now());
        System.out.println("User: " + request.getUserId());
        System.out.println("Agent: " + request.getAgentId());
        System.out.println("Tool: " + request.getToolName());
        System.out.println("Status: " + status);
    }

}
