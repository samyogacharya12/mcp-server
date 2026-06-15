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

    public AgentWorkflowService(
            RouterNode routerNode,
            DocumentNode documentNode,
            ToolNode toolNode,
            ChatNode chatNode,
            ResponseNode responseNode
    ) {
        this.routerNode = routerNode;
        this.documentNode = documentNode;
        this.toolNode = toolNode;
        this.chatNode = chatNode;
        this.responseNode = responseNode;
    }

    public WorkflowResponse execute(AgentState state) {

        List<String> history =
                new ArrayList<>(
                        state.executionHistory()
                );

        history.add("ToolNode");

        AgentState agentState=routerNode.determineRoute(state);


        AgentState processedState = switch (agentState.route()) {
            case AgentRoute.MCP_TOOL -> toolNode.execute(agentState);
            case AgentRoute.DOCUMENT_SEARCH -> documentNode.execute(agentState);
            default -> chatNode.execute(agentState);
        };

        AgentState finalState = responseNode.generate(processedState);

        return new WorkflowResponse(
                finalState.finalResponse(),
                finalState.route(),
                finalState.executionHistory()
        );
    }





}
