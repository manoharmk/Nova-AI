package com.manohar.nova.tools.impl;

import com.manohar.nova.tools.Tool;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Tool implementation that returns the current local date and time.
 */
@Component
public class TimeTool implements Tool {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public String getName() {
        return "time";
    }

    @Override
    public String getDescription() {
        return "Returns the current local date and time.";
    }

    @Override
    public String execute(String input) {
        return LocalDateTime.now().format(FORMATTER);
    }
}
