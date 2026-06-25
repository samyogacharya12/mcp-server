package ai_agent.mcp_server.service;

import ai_agent.mcp_server.dto.AgentState;
import ai_agent.mcp_server.dto.WorkflowMetricsResponse;
import ai_agent.mcp_server.entity.WorkflowExecution;
import ai_agent.mcp_server.enumconstant.AgentRoute;
import ai_agent.mcp_server.repository.WorkflowExecutionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class WorkflowExecutionService {

    private final WorkflowExecutionRepository workflowExecutionRepository;

    public WorkflowExecutionService(
            WorkflowExecutionRepository workflowExecutionRepository
    ) {
        this.workflowExecutionRepository = workflowExecutionRepository;
    }



    public WorkflowExecution save(AgentState state, long durationMs) {
            WorkflowExecution execution =
                    new WorkflowExecution(
                            state.conversationId(),
                            state.checkpointId(),
                            state.route(),
                            state.executionHistory(),
                            state.hasError(),
                            state.errorMessage(),
                            LocalDateTime.now(),
                            durationMs
                    );

            return workflowExecutionRepository.save(execution);
    }

    public List<WorkflowExecution> getExecutionByConversationId(
            String conversationId
    ) {
        return workflowExecutionRepository
                .findByConversationIdOrderByCreatedAtDesc(conversationId);
    }

    public List<WorkflowExecution> getExecutionByCheckpointId(
            String checkpointId
    ) {
        return workflowExecutionRepository.findByCheckpointId(checkpointId);
    }

    public void deleteByConversationId(String conversationId) {
        workflowExecutionRepository.deleteByConversationId(conversationId);
    }

    public WorkflowMetricsResponse getMetrics() {

        long totalExecutions = workflowExecutionRepository.count();

        long errorCount = workflowExecutionRepository.countByHasError(true);

        long successCount = workflowExecutionRepository.countByHasError(false);

        List<WorkflowExecution> executions = workflowExecutionRepository.findAll();

        Map<AgentRoute, Long> routeCounts =
                new EnumMap<>(AgentRoute.class);

        for (AgentRoute route : AgentRoute.values()) {
            routeCounts.put(route, 0L);
        }

        for (WorkflowExecution execution : executions) {
            AgentRoute route = execution.getRoute();

            if (route != null) {
                routeCounts.put(
                        route,
                        routeCounts.get(route) + 1
                );
            }
        }

        return new WorkflowMetricsResponse(
                totalExecutions,
                successCount,
                errorCount,
                routeCounts
        );
    }

}
