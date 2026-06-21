package ai_agent.mcp_server.config;

import ai_agent.mcp_server.dto.AgentState;
import ai_agent.mcp_server.service.DocumentService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentNode {


    private final DocumentService documentService;


    public DocumentNode(
            DocumentService documentService
    ){
        this.documentService = documentService;
    }


    public AgentState execute(
            AgentState state
    ){


        String answer =
                documentService
                        .searchDocument(
                                state.userMessage()
                        );

        List<String> steps = new ArrayList<>(state.executionHistory());
        steps.add("DocumentNode searched pgvector document context");
        return new AgentState(
                state.conversationId(),
                state.userMessage(),
                state.route(),
                state.toolResult(),
                answer,
                answer,
                steps,
                null,
                false,
                state.memory(),
                state.checkpointId()
        );

    }

}
