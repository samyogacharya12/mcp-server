package ai_agent.mcp_server.config;

import ai_agent.mcp_server.dto.AgentState;
import ai_agent.mcp_server.enumconstant.AgentRoute;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RouterNode {


    public AgentState determineRoute(AgentState state) {

        String message=state.userMessage().toLowerCase();

        AgentRoute route;


        if (message.contains("delete")
                || message.contains("remove")
                || message.contains("clear")) {
            route = AgentRoute.HUMAN_APPROVAL;
        }
        else if(
                message.contains("weather")
                        ||
                        message.contains("time")
                        ||
                        message.contains("date")
        ){

            route = AgentRoute.MCP_TOOL;

        }
        else if(
                message.contains("document")
                        ||
                        message.contains("pdf")
                        ||
                        message.contains("file")
        ){
            route = AgentRoute.DOCUMENT_SEARCH;

        }
        else{
            route = AgentRoute.NORMAL_CHAT;
        }
        List<String> steps = new ArrayList<>(state.executionHistory());
        steps.add("RouterNode selected route: " + route);
        return new AgentState(
                state.conversationId(),
                state.userMessage(),
                route,
                state.toolResult(),
                state.documentContext(),
                state.finalResponse(),
                steps,
                null,
                false,
                state.memory(),
                state.checkpointId(),
                state.retryCount(),
                state.approvalRequired(),
                0
        );

    }

}
