package ai_agent.mcp_server.config;

import ai_agent.mcp_server.utils.DateTimeTool;
import ai_agent.mcp_server.utils.WeatherTool;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpToolConfiguration {

    @Bean
    public ToolCallbackProvider toolCallbackProvider(
            DateTimeTool dateTimeTool,
            WeatherTool weatherTool
    ){


        return MethodToolCallbackProvider
                .builder()
                .toolObjects(
                        dateTimeTool,
                        weatherTool
                )
                .build();
    }
}
