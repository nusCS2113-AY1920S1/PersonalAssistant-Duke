package FrontEnd;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Farmio.Farmer;
import Farmio.Storage;

public class Simulation {
    private static final int SLEEP_TIME = 300;
    private Farmio farmio;
    private Storage storage;
    private Ui ui;
    private Farmer farmer;
    private static String lastPath;
    private static int lastFrameId;
    private static boolean hadFullscreen;
    public Simulation(Farmio farmio) {
        this.farmio = farmio;
        storage = farmio.getStorage();
        ui = farmio.getUi();
        farmer = farmio.getFarmer();
        lastPath = "Welcome";
        lastFrameId = 1;
        hadFullscreen = true;
    }
    public void simulate(String framePath, int frameId, boolean isFullscreen) throws FarmioFatalException {
        lastPath = framePath;
        lastFrameId = frameId;
        hadFullscreen = isFullscreen;
        refresh();
        if (isFullscreen) {
            ui.show(GameConsole.blankConsole(storage.loadFrame(framePath, frameId, GameConsole.FULL_CONSOLE_WIDTH,
                    GameConsole.FULL_CONSOLE_HEIGHT)));
        } else {
            ui.show(GameConsole.content(storage.loadFrame(framePath, frameId, GameConsole.FRAME_SECTION_WIDTH,
                    GameConsole.FRAME_SECTION_HEIGHT), farmer, farmio.getLevel().getGoals(), farmio.getLevel().getObjective()));
        }
    }

    public void simulate(String framePath, int startFrame, int endFrame, boolean isFullscreen)
            throws FarmioFatalException {
        for(int i =  startFrame; i <= endFrame; i ++) {
            simulate(framePath, i, isFullscreen);
        }
    }

    public void simulate(String framePath, int frameId) throws FarmioFatalException {
        simulate(framePath, frameId, false);
    }

    public void simulate() throws FarmioFatalException {
        simulate(lastPath, lastFrameId, hadFullscreen);
    }

    public void simulate(String framePath, int startFrame, int endFrame) throws FarmioFatalException {
        for(int i =  startFrame; i <= endFrame; i ++) {
            simulate(framePath, i);
        }
    }
    public void simulate(int delay, String framePath, int frameId) throws FarmioFatalException {
        simulate(framePath, frameId);
        ui.sleep(delay);
    }
    private void refresh() {
        storage = farmio.getStorage();
        ui = farmio.getUi();
        farmer = farmio.getFarmer();
        ui.sleep(SLEEP_TIME);
        ui.clearScreen();
    }
}