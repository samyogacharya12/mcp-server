package ai_agent.mcp_server.controller;

import ai_agent.mcp_server.dto.Payload;
import ai_agent.mcp_server.service.AiAgentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agent")
public class AiAgentController {


    private final AiAgentService aiAgentService;


    public AiAgentController(
            AiAgentService aiAgentService
    ) {
        this.aiAgentService = aiAgentService;
    }


    @PostMapping("/chat")
    public String chat(
            @RequestBody Payload payload
    ){

        return aiAgentService.chat(payload.getMessage());

    }

}
