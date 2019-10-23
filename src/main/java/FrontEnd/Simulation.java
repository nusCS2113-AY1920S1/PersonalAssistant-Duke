package FrontEnd;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Farmio.Farmer;
import Farmio.Storage;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Simulation {
    private static final int SLEEP_TIME = 400;
    private Farmio farmio;
    private Storage storage;
    private Ui ui;
    private Farmer farmer;
    public Simulation(Farmio farmio) {
        this.farmio = farmio;
        storage = farmio.getStorage();
        ui = farmio.getUi();
        farmer = farmio.getFarmer();
    }
    public void animate(String framePath, int frameId, boolean isFullscreen) throws FarmioFatalException {
        refresh();
        if (isFullscreen) {
            ui.show(GameConsole.blankConsole(storage.loadFrame(framePath, frameId, GameConsole.FULL_CONSOLE_WIDTH,
                    GameConsole.FULL_CONSOLE_HEIGHT)));
        } else {
            ui.show(GameConsole.content(storage.loadFrame(framePath, frameId, GameConsole.FRAME_SECTION_WIDTH,
                    GameConsole.FRAME_SECTION_HEIGHT), farmer, farmio.getLevel().getGoals()));
        }
    }

    public void animate(String framePath, int startFrame, int endFrame, boolean isFullscreen)
            throws FarmioFatalException {
        for(int i =  startFrame; i <= endFrame; i ++) {
            animate(framePath, i, isFullscreen);
        }
    }

    public void animate(String framePath, int frameId) throws FarmioFatalException {
        refresh();
        ui.show(GameConsole.content(storage.loadFrame(framePath, frameId, GameConsole.FRAME_SECTION_WIDTH,
                GameConsole.FRAME_SECTION_HEIGHT), farmer, farmio.getLevel().getGoals()));
    }

    public void animate(String framePath, int startFrame, int endFrame) throws FarmioFatalException {
        for(int i =  startFrame; i <= endFrame; i ++) {
            animate(framePath, i);
        }
    }

    public void animate(int delay, String framePath, int frameId) throws FarmioFatalException {
        animate(framePath, frameId);
        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            ui.clearScreen();
            ui.showWarning("Simulator delay interrupted! Interface may not display correctly.");
        };
    }
    private void refresh() {
        storage = farmio.getStorage();
        ui = farmio.getUi();
        farmer = farmio.getFarmer();
        try {
            TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
            ui.clearScreen();
        } catch (InterruptedException e) {
            ui.clearScreen();
            ui.showWarning("Simulator refersh interrupted! Interface may not display correctly.");
        }
    }
}