package ai_agent.mcp_server.controller;

import ai_agent.mcp_server.dto.ToolRequest;
import ai_agent.mcp_server.dto.ToolResponse;
import ai_agent.mcp_server.service.AuditLogService;
import ai_agent.mcp_server.service.McpSecurityService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mcp")
public class McpController {

    private final McpSecurityService securityService;
    private final AuditLogService auditLogService;

    public McpController(McpSecurityService securityService,
                         AuditLogService auditLogService) {
        this.securityService = securityService;
        this.auditLogService = auditLogService;
    }

    @PostMapping("/tool-call")
    public ToolResponse callTool(@RequestBody ToolRequest request) {

        if (!securityService.isToolAllowed(request.getToolName())) {
            auditLogService.logToolCall(request, "BLOCKED_TOOL_NOT_ALLOWED");
            return new ToolResponse(false, "Tool is not allowed.");
        }

        if (!securityService.isValidInput(request.getInput())) {
            auditLogService.logToolCall(request, "BLOCKED_INVALID_INPUT");
            return new ToolResponse(false, "Invalid or unsafe input.");
        }

        if (securityService.requiresHumanApproval(request.getToolName())) {
            auditLogService.logToolCall(request, "WAITING_FOR_HUMAN_APPROVAL");
            return new ToolResponse(false, "Human approval required.");
        }

        String rawOutput = executeTool(request.getToolName(), request.getInput());

        String safeOutput = securityService.sanitizeToolOutput(rawOutput);

        auditLogService.logToolCall(request, "SUCCESS");

        return new ToolResponse(true, safeOutput);
    }

    private String executeTool(String toolName, String input) {
        return switch (toolName) {
            case "searchDocuments" -> "Searching documents for: " + input;
            case "getCustomerById" -> "Customer data for ID: " + input;
            case "summarizeReport" -> "Summary created for: " + input;
            default -> "Unknown tool";
        };
    }
}
