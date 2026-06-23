package ai_agent.mcp_server.service;

import ai_agent.mcp_server.dto.ChatRequest;
import ai_agent.mcp_server.entity.AgentMemory;
import ai_agent.mcp_server.repository.AgentMemoryRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StreamingWorkflowService {

    private final AiAgentService aiAgentService;

    private final AgentMemoryRepository memoryRepository;


    public StreamingWorkflowService(
            AiAgentService aiAgentService,
            AgentMemoryRepository memoryRepository
    ) {

        this.aiAgentService = aiAgentService;
        this.memoryRepository = memoryRepository;

    }


    public Flux<String> stream(
            ChatRequest request
    ) {


        String conversationId =
                request.conversationId();


        if (conversationId == null
                || conversationId.isBlank()) {

            conversationId =
                    UUID.randomUUID()
                            .toString();

        }


        List<AgentMemory> savedMessages =
                memoryRepository
                        .findByConversationIdOrderByCreatedAtAsc(
                                conversationId
                        );


        List<String> memory =
                new ArrayList<>();


        for (AgentMemory message : savedMessages) {

            memory.add(
                    message.getRole()
                            + ": "
                            + message.getMessage()
            );

        }


        // save user message
        memoryRepository.save(
                new AgentMemory(
                        conversationId,
                        "USER",
                        request.message()
                )
        );


        String finalConversationId =
                conversationId;


        StringBuilder responseBuilder =
                new StringBuilder();



        return aiAgentService
                .streamChat(
                        request.message(),
                        memory
                )
                .doOnNext(
                        responseBuilder::append
                )

                .doOnComplete(
                        () -> {

                            memoryRepository.save(

                                    new AgentMemory(
                                            finalConversationId,
                                            "ASSISTANT",
                                            responseBuilder.toString()
                                    )
                            );

                        }
                );

    }

}
