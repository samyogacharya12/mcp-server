package ai_agent.mcp_server.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class AiAgentService {


    private final ChatClient normalChatClient;


    private final ChatClient chatClient;


    public AiAgentService(
            ChatClient.Builder builder,
            ToolCallbackProvider provider
    ) {
        this.normalChatClient = builder.build();
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

    public Flux<String> streamChat(String message, List<String> memory) {
        String previousMemory =
                String.join("\n", memory);


        return normalChatClient
                .prompt()

                .system("""
                    You are a Java tutor.

                    Answer the user's question directly.
                    Do not mention tools.
                    Do not mention functions.
                    Do not mention MCP.
                    Do not output JSON.
                    Do not say you cannot answer.

                    Explain clearly in simple words.
                    """)

                .user(message)

                .stream()

                .content();
    }

    public String chat(String message, List<String> memory) {

        String previousMemory = String.join("\n", memory);

        return chatClient
                .prompt()
                .system("""
                    You are a normal helpful AI assistant.

                    Rules:
                    - Answer the user's question directly.
                    - Do not mention tools.
                    - Do not mention function calls.
                    - Do not mention MCP.
                    - Do not output tool JSON.
                    - Do not say you are not allowed to call tools.

                    Previous conversation:
                    %s
                    """.formatted(previousMemory))
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
