package com.manohar.nova.automation;

/**
 * Service interface for desktop process automation.
 */
public interface DesktopAutomationService {

    /**
     * Launches the Google Chrome browser.
     *
     * @return launch status message
     */
    String openChrome();

    /**
     * Launches the Microsoft Edge browser.
     *
     * @return launch status message
     */
    String openEdge();

    /**
     * Launches the Notepad text editor.
     *
     * @return launch status message
     */
    String openNotepad();

    /**
     * Launches the Windows Calculator.
     *
     * @return launch status message
     */
    String openCalculator();

    /**
     * Launches Visual Studio Code.
     *
     * @return launch status message
     */
    String openVSCode();
}
