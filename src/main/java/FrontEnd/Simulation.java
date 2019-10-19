package FrontEnd;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Farmio.Farmer;
import Farmio.Storage;

import java.util.concurrent.TimeUnit;

public class Simulation {
    private static final int FRAME_PER_SECOND = 2;
    private static Storage storage;
    private static Ui ui;
    private static Farmer farmer;
    private static String framePath;
    public Simulation(String path, Farmio farmio) {
        storage = farmio.getStorage();
        ui = farmio.getUi();
        farmer = farmio.getFarmer();
        framePath = path;
    }
    public Simulation(String path, Ui userInterface, Storage storage1) {
        ui = userInterface;
        framePath = path;
        farmer = null;
        storage = storage1;
    }
    public void animate(int frameId) throws FarmioFatalException {
        refresh(ui);
        if (farmer == null) {
            ui.show(GameConsole.blankConsole(storage.loadFrame(framePath, frameId, 103, 22)));
        } else {
            ui.show(GameConsole.content(storage.loadFrame(framePath, frameId, 55, 18), farmer));
        }
    }

    public void animate(int frameIdStart, int frameIdEnd) throws FarmioFatalException {
        for(int i = frameIdStart; i <= frameIdEnd; ++i){
            animate(i);
        }
    }

    private static void refresh(Ui ui) {
        try {
            TimeUnit.MILLISECONDS.sleep((int) (1000 / FRAME_PER_SECOND) );
            ui.clearScreen();
        } catch (InterruptedException e) {
            ui.clearScreen();
            ui.showWarning("Animate refersh interrupted! Interface may not display correctly.");
        }
    }

    public void delayFrame(int frameId, int delay) throws FarmioFatalException {
        animate(frameId);
        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            e.getMessage();
        }
    }
}
