package FrontEnd;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Farmio.Farmer;
import Farmio.Storage;

import java.util.concurrent.TimeUnit;

public class Simulation {
    private static final int FRAME_PER_SECOND = 2;

    public static void animate(Ui ui, Storage storage, String framePath, int frameId) throws FarmioFatalException {
        refresh(ui);
        ui.show(GameConsole.blankConsole(storage.loadFrame(framePath, frameId, 103, 22)));
    }

    public static void animate(Ui ui, Storage storage, Farmer farmer, String framePath, int frameId) throws FarmioFatalException {
        refresh(ui);
        ui.show(GameConsole.content(storage.loadFrame(framePath, frameId, 55, 18), farmer));
    }

    public static void animate(Ui ui, Storage storage, Farmer farmer, String framePath, int frameIdStart, int frameIdEnd) throws FarmioFatalException {
        for(int i = frameIdStart; i <= frameIdEnd; ++i){
            animate(ui, storage, farmer, framePath, i);
        }
    }

    public static void animate(Ui ui, Storage storage, Farmer farmer, int delay, String framePath, int frameId) throws FarmioFatalException {
        animate(ui, storage, farmer, framePath, frameId);
        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            ui.clearScreen();
            ui.showWarning("Simulator delay interrupted! Interface may not display correctly.");
        };
    }

    private static void refresh(Ui ui) {
        try {
            TimeUnit.MILLISECONDS.sleep((int) (1000 / FRAME_PER_SECOND) );
            ui.clearScreen();
        } catch (InterruptedException e) {
            ui.clearScreen();
            ui.showWarning("Simulator refersh interrupted! Interface may not display correctly.");
        }
    }
}
