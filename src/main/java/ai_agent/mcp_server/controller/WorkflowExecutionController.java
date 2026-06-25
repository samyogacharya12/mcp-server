package ai_agent.mcp_server.controller;

import ai_agent.mcp_server.dto.AgentState;
import ai_agent.mcp_server.dto.WorkflowMetricsResponse;
import ai_agent.mcp_server.dto.WorkflowResponse;
import ai_agent.mcp_server.entity.WorkflowExecution;
import ai_agent.mcp_server.service.AgentWorkflowService;
import ai_agent.mcp_server.service.WorkflowExecutionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WorkflowExecutionController {


    private final WorkflowExecutionService service;


    private final AgentWorkflowService agentWorkflowService;

    public WorkflowExecutionController(
            WorkflowExecutionService service,
            AgentWorkflowService agentWorkflowService
    ){

        this.service = service;
        this.agentWorkflowService = agentWorkflowService;

    }


    // manual save test
    @PostMapping("/workflow/execution")
    public WorkflowResponse chat(
            @RequestBody AgentState state
    ) {

        return agentWorkflowService.execute(state);
    }



    @GetMapping(
            "workflow/execution/conversation/{conversationId}"
    )
    public List<WorkflowExecution> byConversation(
            @PathVariable String conversationId
    ){

        return service
                .getExecutionByConversationId(
                        conversationId
                );
    }




    @GetMapping(
            "workflow/execution/checkpoint/{checkpointId}"
    )
    public List<WorkflowExecution> byCheckpoint(
            @PathVariable String checkpointId
    ){

        return service
                .getExecutionByCheckpointId(
                        checkpointId
                );
    }




    @DeleteMapping(
            "workflow/execution/conversation/{conversationId}"
    )
    public String delete(
            @PathVariable String conversationId
    ){

        service.deleteByConversationId(
                conversationId
        );


        return "Workflow execution deleted";

    }


    @GetMapping("/metrics")
    public WorkflowMetricsResponse metrics() {
        return service.getMetrics();
    }

}
