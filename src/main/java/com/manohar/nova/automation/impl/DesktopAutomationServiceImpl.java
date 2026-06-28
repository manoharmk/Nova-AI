package com.manohar.nova.automation.impl;

import com.manohar.nova.automation.DesktopAutomationService;
import org.springframework.stereotype.Service;
import java.io.IOException;

/**
 * Windows-targeted implementation of {@link DesktopAutomationService} using {@link ProcessBuilder}.
 */
@Service
public class DesktopAutomationServiceImpl implements DesktopAutomationService {

    @Override
    public String openChrome() {
        return launch("Chrome", "cmd.exe", "/c", "start", "chrome");
    }

    @Override
    public String openEdge() {
        return launch("Edge", "cmd.exe", "/c", "start", "msedge");
    }

    @Override
    public String openNotepad() {
        return launch("Notepad", "notepad.exe");
    }

    @Override
    public String openCalculator() {
        return launch("Calculator", "calc.exe");
    }

    @Override
    public String openVSCode() {
        return launch("VS Code", "cmd.exe", "/c", "start", "code");
    }

    private String launch(String appName, String... command) {
        String os = System.getProperty("os.name").toLowerCase();
        if (!os.contains("win")) {
            return "Error: Desktop automation is only supported on Windows operating systems.";
        }
        try {
            new ProcessBuilder(command).start();
            return appName + " launched successfully.";
        } catch (IOException e) {
            return "Error: Could not launch " + appName + ". It may not be installed or registered in the environment path.";
        }
    }
}
