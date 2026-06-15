package ai_agent.mcp_server.utils;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DateTimeTool {

    @Tool(description = "Get current date and time")
    public String getCurrentDateTime() {


        LocalDateTime now = LocalDateTime.now();


        return "Current date and time is: "
                + now.format(
                DateTimeFormatter.ofPattern(
                        "yyyy-MM-dd HH:mm:ss"
                )
        );
    }
}
