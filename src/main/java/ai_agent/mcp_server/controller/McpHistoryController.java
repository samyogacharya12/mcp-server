package ai_agent.mcp_server.controller;

import ai_agent.mcp_server.service.MongoChatHistoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/history")
public class McpHistoryController {


    private final MongoChatHistoryService mongoChatHistoryService;

    public McpHistoryController(MongoChatHistoryService mongoChatHistoryService) {
        this.mongoChatHistoryService = mongoChatHistoryService;
    }

    @GetMapping("/{conversationId}")
    public String getHistory(@PathVariable String conversationId) {
        return mongoChatHistoryService.getRecentHistoryAsText(conversationId, 10);
    }


}
