package com.manohar.nova.tools.impl;

import com.manohar.nova.automation.DesktopAutomationService;
import com.manohar.nova.tools.Tool;
import org.springframework.stereotype.Component;

/**
 * Discovered system tool for launching desktop applications.
 */
@Component
public class OpenApplicationTool implements Tool {

    private final DesktopAutomationService automationService;

    /**
     * Constructor injection.
     *
     * @param automationService the desktop automation service implementation
     */
    public OpenApplicationTool(DesktopAutomationService automationService) {
        this.automationService = automationService;
    }

    @Override
    public String getName() {
        return "openApplication";
    }

    @Override
    public String getDescription() {
        return "Launches standard desktop applications (chrome, edge, notepad, calculator, vscode).";
    }

    @Override
    public String execute(String input) {
        if (input == null || input.isBlank()) {
            return "Error: Please specify which application to open (chrome, edge, notepad, calculator, vscode).";
        }

        String app = input.toLowerCase().trim();

        // Strip action verbs to resolve actual application keyword
        if (app.startsWith("open ")) {
            app = app.substring(5).trim();
        } else if (app.startsWith("launch ")) {
            app = app.substring(7).trim();
        } else if (app.startsWith("start ")) {
            app = app.substring(6).trim();
        }

        return switch (app) {
            case "chrome" -> automationService.openChrome();
            case "edge" -> automationService.openEdge();
            case "notepad" -> automationService.openNotepad();
            case "calculator", "calc" -> automationService.openCalculator();
            case "vscode", "vs code", "code" -> automationService.openVSCode();
            default -> "Error: Application '" + app + "' is not supported. Choose from: chrome, edge, notepad, calculator, vscode.";
        };
    }
}
