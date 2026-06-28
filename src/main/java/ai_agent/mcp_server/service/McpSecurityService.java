package ai_agent.mcp_server.service;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class McpSecurityService {


    private final Set<String> allowedTools = Set.of(
            "searchDocuments",
            "getCustomerById",
            "summarizeReport"
    );

    private final Set<String> riskyTools = Set.of(
            "deleteUser",
            "updateDatabase",
            "sendEmail"
    );

    public boolean isToolAllowed(String toolName) {
        return allowedTools.contains(toolName);
    }

    public boolean requiresHumanApproval(String toolName) {
        return riskyTools.contains(toolName);
    }

    public boolean isValidInput(String input) {
        if (input == null || input.isBlank()) {
            return false;
        }

        String lower = input.toLowerCase();

        return !lower.contains("drop table")
                && !lower.contains("delete from")
                && !lower.contains("ignore previous instructions")
                && !lower.contains("../");
    }

    public String sanitizeToolOutput(String output) {
        if (output == null) {
            return "";
        }

        return output
                .replace("ignore previous instructions", "[REMOVED]")
                .replace("system prompt", "[REMOVED]");
    }



}
