package ai_agent.mcp_server.utils;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DateTimeTool {

    @Tool(
            name = "currentDateTime",
            description = "Returns the real current system date and time. Always use this tool when user asks about current date or time."
    )
    public String currentDateTime(){


        String time =
                ZonedDateTime
                        .now(ZoneId.systemDefault())
                        .format(
                                DateTimeFormatter.ofPattern(
                                        "yyyy-MM-dd HH:mm:ss z"
                                )
                        );


        return "REAL SYSTEM DATE TIME: " + time;
    }
}
