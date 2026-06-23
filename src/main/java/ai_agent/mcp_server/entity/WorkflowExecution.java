package ai_agent.mcp_server.entity;

import ai_agent.mcp_server.enumconstant.AgentRoute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "workflow_execution")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowExecution {

    @Id
    private String id;

    private String conversationId;
    private String checkpointId;
    private AgentRoute route;
    private List<String> steps;
    private boolean hasError;
    private String errorMessage;
    private LocalDateTime createdAt;

    public WorkflowExecution(String conversationId,
                             String checkpointId,
                             AgentRoute route,
                             List<String> steps,
                             boolean hasError,
                             String errorMessage,
                             LocalDateTime createdAt) {
        this.conversationId = conversationId;
        this.checkpointId = checkpointId;
        this.route = route;
        this.steps = steps;
        this.hasError = hasError;
        this.errorMessage = errorMessage;
        this.createdAt = createdAt;
    }
}
