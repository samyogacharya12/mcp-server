package ai_agent.mcp_server.controller;

import ai_agent.mcp_server.dto.AgentState;
import ai_agent.mcp_server.entity.WorkflowExecution;
import ai_agent.mcp_server.service.WorkflowExecutionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WorkflowExecutionController {


    private final WorkflowExecutionService service;


    public WorkflowExecutionController(
            WorkflowExecutionService service
    ){

        this.service = service;

    }


    // manual save test
    @PostMapping("/workflow/execution")
    public WorkflowExecution save(
            @RequestBody AgentState agentState
    ) {

        return service.save(
                agentState
        );

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
}
