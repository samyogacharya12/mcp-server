package ai_agent.mcp_server.service;

import ai_agent.mcp_server.config.*;
import ai_agent.mcp_server.dto.AgentState;
import ai_agent.mcp_server.dto.WorkflowResponse;
import ai_agent.mcp_server.enumconstant.AgentRoute;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AgentWorkflowService {


    private final RouterNode routerNode;
    private final DocumentNode documentNode;
    private final ToolNode toolNode;
    private final ChatNode chatNode;
    private final ResponseNode responseNode;
    private final ErrorNode errorNode;
    private final MemoryNode memoryNode;
    private final CheckpointNode checkpointNode;
    private final RetryNode retryNode;

    public AgentWorkflowService(
            RouterNode routerNode,
            DocumentNode documentNode,
            ToolNode toolNode,
            ChatNode chatNode,
            ResponseNode responseNode,
            ErrorNode errorNode,
            MemoryNode memoryNode,
            CheckpointNode checkpointNode,
            RetryNode retryNode
    ) {
        this.routerNode = routerNode;
        this.documentNode = documentNode;
        this.toolNode = toolNode;
        this.chatNode = chatNode;
        this.responseNode = responseNode;
        this.errorNode = errorNode;
        this.memoryNode = memoryNode;
        this.checkpointNode = checkpointNode;
        this.retryNode = retryNode;
    }

    public WorkflowResponse execute(AgentState state, Exception exception) {
        try {
            List<String> histories =
                    new ArrayList<>(
                            state.executionHistory()
                    );

            histories.add("ToolNode");
            AgentState memoryState = memoryNode.loadMemory(state);

            AgentState checkpointState = checkpointNode.createCheckpoint(memoryState);

            AgentState agentState = routerNode.determineRoute(checkpointState);

            AgentState processedState =
                    executeSelectedNodeWithRetry(agentState);



            AgentState finalState = responseNode.generate(processedState, exception);

            AgentState savedMemoryState = memoryNode.saveMemory(finalState);


            return new WorkflowResponse(
                    savedMemoryState.finalResponse(),
                    savedMemoryState.route(),
                    savedMemoryState.executionHistory(),
                    savedMemoryState.hasError(),
                    savedMemoryState.errorMessage(),
                    savedMemoryState.memory(),
                    savedMemoryState.checkpointId(),
                    savedMemoryState.conversationId()
            );
        } catch (Exception ex) {
            AgentState errorState = errorNode.handle(exception, state);
            return new WorkflowResponse(
                    errorState.finalResponse(),
                    errorState.route(),
                    errorState.executionHistory(),
                    true,
                    errorState.errorMessage(),
                    errorState.memory(),
                    errorState.checkpointId(),
                    errorState.conversationId()
                    );

        }
    }

    private AgentState executeSelectedNodeWithRetry(AgentState state) {

        try {
            return executeSelectedNode(state);

        } catch (Exception ex) {

            if (retryNode.canRetry(state)) {

                AgentState retryState =
                        retryNode.incrementRetry(state, ex);

                return executeSelectedNode(retryState);
            }

            throw ex;
        }
    }

    private AgentState executeSelectedNode(AgentState state) {

        return switch (state.route()) {
            case MCP_TOOL -> toolNode.execute(state);
            case DOCUMENT_SEARCH -> documentNode.execute(state);
            case NORMAL_CHAT -> chatNode.execute(state);
        };
    }

}
