package ai_agent.mcp_server.controller;


import ai_agent.mcp_server.service.RagService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rag")
public class McpRagController {


    private final RagService ragService;

    public McpRagController(RagService ragService) {
        this.ragService = ragService;
    }

    @PostMapping("/ask")
    public String ask(@RequestBody AskRequest request) {
        return ragService.ask(request.conversationId(), request.question());
    }

    public record AskRequest(String conversationId, String question) {}





}
