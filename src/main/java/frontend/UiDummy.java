package frontend;

import exceptions.FarmioFatalException;
import farmio.Level;

public class UiDummy implements Ui {
    public static String uiTestString;

    public UiDummy() {
        uiTestString = "";
    }

    public void show(String message) {
        uiTestString += "show";
    }

    public void showExit() {
        uiTestString += "exit";
    }

    public void showError(String message) {
        uiTestString += "error";
    }

    public void showWarning(String message) {
        uiTestString += "warning";
    }

    public void clearScreen() {
        uiTestString += "clear";
    }

    public void showInfo(String message) {
        uiTestString += "info";
    }

    public String getInput() {
        uiTestString += "input";
        return "";
    }

    public void sleep(int delay) {
        uiTestString += "sleep";
    }

    public void showHint(String text) {
        uiTestString += "hint";
    }

    public void typeWriter(String text, boolean hasPressEnter) {
        uiTestString += "typewriter";
    }

    private void showLevelBegin() {
        uiTestString += "levelBegin";
    }

    /**
     * Mimics showing a narrative.
     * @param level that the narrative is to be shown.
     * @param simulation that the simulation of the level will utilise.
     * @throws FarmioFatalException if simulation file is missing
     */
    public void showNarrative(Level level, Simulation simulation) throws FarmioFatalException {
        int frameId;
        for (frameId = 0; frameId < level.getNarratives().size() - 1; frameId++) {
            getInput();
            simulation.simulate(level.getPath(), frameId);
            typeWriter("", true);
        }
        getInput();
        simulation.simulate(level.getPath(), frameId);
        typeWriter("", true);
        showLevelBegin();
    }
}
