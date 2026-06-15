package ai_agent.mcp_server.utils;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class WeatherTool {

    @Tool(description = "Get weather information for a city")
    public String getWeather(String city){


        // Later replace this with real API call
        return """
                Weather Information

                City: %s

                Temperature: 25°C

                Condition: Sunny
                """
                .formatted(city);

    }
}
