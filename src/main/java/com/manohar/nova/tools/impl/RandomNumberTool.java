package com.manohar.nova.tools.impl;

import com.manohar.nova.tools.Tool;
import org.springframework.stereotype.Component;
import java.util.Random;

/**
 * Tool for generating a random number within specified bounds.
 */
@Component
public class RandomNumberTool implements Tool {

    private final Random random = new Random();

    @Override
    public String getName() {
        return "random";
    }

    @Override
    public String getDescription() {
        return "Generates a random number. Usage: random, random [max], or random [min] [max].";
    }

    @Override
    public String execute(String input) {
        String args = input.toLowerCase().replace("random", "").trim();

        if (args.isEmpty()) {
            return String.valueOf(random.nextInt(101));
        }

        String[] parts = args.split("\\s+");
        try {
            if (parts.length == 1) {
                int max = Integer.parseInt(parts[0]);
                if (max <= 0) {
                    return "Error: Bound must be positive";
                }
                return String.valueOf(random.nextInt(max + 1));
            } else if (parts.length >= 2) {
                int min = Integer.parseInt(parts[0]);
                int max = Integer.parseInt(parts[1]);
                if (min > max) {
                    return "Error: Minimum cannot be greater than maximum";
                }
                return String.valueOf(random.nextInt((max - min) + 1) + min);
            }
        } catch (NumberFormatException e) {
            return "Error: Bounds must be valid integers";
        }

        return "Error: Invalid arguments. Use: random [max] or random [min] [max]";
    }
}
