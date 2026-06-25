# AI Agent Workflow Project

This project implements a LangGraph-style AI Agent workflow using Java, Spring Boot, Spring AI, MCP tools, MongoDB memory, and workflow tracing.

## Architecture

User Request
    |
    v
AgentWorkflowController
    |
    v
AgentWorkflowService
    |
    v
MemoryNode
    |
    v
CheckpointNode
    |
    v
RouterNode
    |
    +--> ChatNode
    +--> ToolNode / MCP Server
    +--> DocumentNode / pgvector
    +--> HumanApprovalNode
    |
    v
ResponseNode
    |
    v
MongoDB Memory + Workflow Execution History


## Features

- LangGraph-style stateful workflow
- AgentState object for state passing
- RouterNode for conditional routing
- ChatNode for normal AI response
- ToolNode for MCP tool execution
- DocumentNode for pgvector document search
- MemoryNode for MongoDB persistent memory
- CheckpointNode for workflow checkpointing
- RetryNode for failed node retry
- ErrorNode for fallback handling
- HumanApprovalNode for risky actions
- Streaming response API
- Workflow execution history
- Metrics API
- Latency tracking


## APIs

### Chat Workflow

POST /api/workflow/chat

{
  "conversationId": "conv-123",
  "message": "Explain Spring Boot"
}

### Streaming Chat

POST /api/workflow/stream

{
  "conversationId": "conv-stream-123",
  "message": "Explain Spring Boot in simple words"
}

### Approval

POST /api/workflow/approve

{
  "conversationId": "conv-123",
  "checkpointId": "check-123",
  "approved": true
}

### Metrics

GET /api/workflow/execution/metrics

### Execution History

GET /api/workflow/execution/conversation/{conversationId}



## Workflow

1. User sends a request.
2. MemoryNode loads previous conversation from MongoDB.
3. CheckpointNode creates a checkpoint ID.
4. RouterNode decides which path to follow.
5. Selected node executes:
   - NORMAL_CHAT → ChatNode
   - MCP_TOOL → ToolNode
   - DOCUMENT_SEARCH → DocumentNode
   - HUMAN_APPROVAL → HumanApprovalNode
6. ResponseNode prepares final response.
7. MemoryNode saves response.
8. WorkflowExecutionService saves execution trace and duration.
