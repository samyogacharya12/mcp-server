package ai_agent.mcp_server.config;

import ai_agent.mcp_server.dto.AgentState;
import ai_agent.mcp_server.entity.AgentMemory;
import ai_agent.mcp_server.repository.AgentMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MemoryNode {

     @Autowired
     private AgentMemoryRepository agentMemoryRepository;


    public AgentState loadMemory(AgentState state) {

        List<String> steps = new ArrayList<>(state.executionHistory());
        steps.add("MemoryNode loaded conversation memory");


        List<AgentMemory> savedMessages =
                agentMemoryRepository.findByConversationIdOrderByCreatedAtAsc(
                        state.conversationId());

        List<String> memory = new ArrayList<>();

        for (AgentMemory agentMemory : savedMessages) {
            if (state.memory() != null) {
                memory.add(agentMemory.getRole() + " :" + agentMemory.getMessage());
            }
        }

        memory.add("User: " + state.userMessage());

        return new AgentState(
                state.conversationId(),
                state.userMessage(),
                state.route(),
                state.toolResult(),
                state.documentContext(),
                state.finalResponse(),
                steps,
                state.errorMessage(),
                state.hasError(),
                memory,
                state.checkpointId()
        );

    }

    public AgentState saveMemory(AgentState state) {

        List<String> steps = new ArrayList<>(state.executionHistory());
        steps.add("MemoryNode saved final response to memory");

        List<String> memory = new ArrayList<>(state.memory());


        memory.add("AI: " + state.finalResponse());

        agentMemoryRepository.save(new AgentMemory(state.conversationId(), "USER", state.userMessage()));

        return new AgentState(
                state.conversationId(),
                state.userMessage(),
                state.route(),
                state.toolResult(),
                state.documentContext(),
                state.finalResponse(),
                steps,
                state.errorMessage(),
                state.hasError(),
                memory,
                state.checkpointId()
        );
    }



}