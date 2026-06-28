package com.manohar.nova.tools.impl;

import com.manohar.nova.tools.Tool;
import org.springframework.stereotype.Component;
import java.util.UUID;

/**
 * Tool for generating a random UUID.
 */
@Component
public class UUIDTool implements Tool {

    @Override
    public String getName() {
        return "uuid";
    }

    @Override
    public String getDescription() {
        return "Generates a random Universally Unique Identifier (UUID).";
    }

    @Override
    public String execute(String input) {
        return UUID.randomUUID().toString();
    }
}
