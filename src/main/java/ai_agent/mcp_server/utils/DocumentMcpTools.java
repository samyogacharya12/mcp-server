package ai_agent.mcp_server.utils;

import ai_agent.mcp_server.service.RagService;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

@Component
public class DocumentMcpTools {

    private final RagService ragService;

    public DocumentMcpTools(RagService ragService) {
        this.ragService = ragService;
    }

    @McpTool(description = "Search the ingested document and answer a question using document context")
    public String searchDocument(
        @McpToolParam(description = "Conversation id", required = true) String conversationId,
        @McpToolParam(description = "Question about the ingested document", required = true) String question) {
        return ragService.ask(conversationId, question);
    }
}
