package FrontEnd;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Farmio.Farmer;
import Farmio.Storage;

import java.util.concurrent.TimeUnit;

public class Simulation {
    private static final int FRAME_PER_SECOND = 2;
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
    public void animate(String framePath, int frameId, boolean is_fullscreen) throws FarmioFatalException {
        refresh();
        if (is_fullscreen) {
            ui.show(GameConsole.blankConsole(storage.loadFrame(framePath, frameId, 103, 22)));
        } else {
            ui.show(GameConsole.content(storage.loadFrame(framePath, frameId, 55, 18), farmer));
        }
    }

    public void animate(String framePath, int startFrame, int endFrame, boolean is_fullscreen) throws FarmioFatalException {
        for(int i =  startFrame; i <= endFrame; i ++) {
            animate(framePath, i, is_fullscreen);
        }
    }

    public void animate(String framePath, int frameId) throws FarmioFatalException {
        refresh();
        ui.show(GameConsole.content(storage.loadFrame(framePath, frameId, 55, 18), farmer));
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
            TimeUnit.MILLISECONDS.sleep((int) (1000 / FRAME_PER_SECOND) );
            ui.clearScreen();
        } catch (InterruptedException e) {
            ui.clearScreen();
            ui.showWarning("Simulator refersh interrupted! Interface may not display correctly.");
        }
    }
}
