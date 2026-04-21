package ai_agent.mcp_server.utils;

import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;

public class DemoMcpTools {

    @McpTool(description = "Echo a message")
    public String echo(@McpToolParam(description = "Input text", required = true) String text) {
        return "Echo: " + text;
    }
}
