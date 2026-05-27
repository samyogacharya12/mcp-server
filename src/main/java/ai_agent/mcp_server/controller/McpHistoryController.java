package ai_agent.mcp_server.controller;

import ai_agent.mcp_server.dto.HistoryRequest;
import ai_agent.mcp_server.service.MongoChatHistoryService;
import org.springframework.web.bind.annotation.*;


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


    @PostMapping("/search")
    public String search(@RequestBody HistoryRequest request) {

        return mongoChatHistoryService.searchConversation(
                request.getConversationId(),
                request.getQuestion()
        );
    }

}
