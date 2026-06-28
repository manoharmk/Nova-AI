package com.manohar.nova.tools.impl;

import com.manohar.nova.tools.Tool;
import org.springframework.stereotype.Component;

/**
 * Tool that returns the supplied input.
 */
@Component
public class EchoTool implements Tool {

    @Override
    public String getName() {
        return "echo";
    }

    @Override
    public String getDescription() {
        return "Echoes back the input message.";
    }

    @Override
    public String execute(String input) {
        if (input == null) {
            return "";
        }
        
        String echoVal = input.trim();
        if (echoVal.toLowerCase().startsWith("echo ")) {
            echoVal = echoVal.substring(5).trim();
        } else if (echoVal.equalsIgnoreCase("echo")) {
            return "";
        }
        
        return echoVal;
    }
}
