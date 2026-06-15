package ai_agent.mcp_server.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;

@Service
public class AiAgentService {


    private final ChatClient chatClient;


    public AiAgentService(
            ChatClient.Builder builder,
            ToolCallbackProvider provider
    ) {

        this.chatClient =
                builder
                        .defaultToolCallbacks(provider)
                        .build();

    }


    public String chat(String message) {


        return chatClient
                .prompt()
                .user(message)
                .call()
                .content();

    }

    // MCP Tool Execution
    public String processWithMcpTools(String message) {


        return chatClient
                .prompt()
                .system("""
                        You are an AI Agent.
                        
                                           Use the available MCP tools when needed.
                        
                                           Important:
                                           - Do not explain that you used a tool.
                                           - Do not mention tool names.
                                           - Only return the final answer to the user.
                                           - If the user asks for current date/time, return the actual date and time.
                        """)
                .user(message)
                .call()
                .content();

    }

}
