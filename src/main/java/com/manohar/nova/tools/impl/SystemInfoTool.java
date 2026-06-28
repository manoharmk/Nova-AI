package com.manohar.nova.tools.impl;

import com.manohar.nova.tools.Tool;
import org.springframework.stereotype.Component;

/**
 * Tool for retrieving system information.
 */
@Component
public class SystemInfoTool implements Tool {

    @Override
    public String getName() {
        return "system";
    }

    @Override
    public String getDescription() {
        return "Returns system information including OS name, Java version, processors, and JVM memory.";
    }

    @Override
    public String execute(String input) {
        Runtime runtime = Runtime.getRuntime();
        long totalMemoryMb = runtime.totalMemory() / (1024 * 1024);
        long freeMemoryMb = runtime.freeMemory() / (1024 * 1024);

        return String.format(
                "OS Name: %s | Java Version: %s | Available Processors: %d | Total JVM Memory: %d MB | Free JVM Memory: %d MB",
                System.getProperty("os.name"),
                System.getProperty("java.version"),
                runtime.availableProcessors(),
                totalMemoryMb,
                freeMemoryMb
        );
    }
}
