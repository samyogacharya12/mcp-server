package ai_agent.mcp_server.controller;

import ai_agent.mcp_server.dto.ChatRequest;
import ai_agent.mcp_server.entity.AgentMemory;
import ai_agent.mcp_server.repository.AgentMemoryRepository;
import ai_agent.mcp_server.service.AiAgentService;
import ai_agent.mcp_server.service.StreamingWorkflowService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/workflow")
public class StreamingWorkflowController {


    private final StreamingWorkflowService streamingWorkflowService;

    public StreamingWorkflowController(
            StreamingWorkflowService streamingWorkflowService
    ) {
        this.streamingWorkflowService = streamingWorkflowService;
    }

    @PostMapping(
            value = "/stream",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public Flux<String> streamChat(
            @RequestBody ChatRequest request
    ) {
        return streamingWorkflowService.stream(request);
    }

}
