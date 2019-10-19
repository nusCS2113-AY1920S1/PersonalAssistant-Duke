package FrontEnd;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Farmio.Farmer;
import Farmio.Storage;

import java.util.concurrent.TimeUnit;

public class Simulation {
    private String framePath;
    private Farmio farmio;
    private static final int FRAME_PER_SECOND = 2;
    protected Ui ui;

    public Simulation(String path, Farmio farmio) {
        ui = farmio.getUi();
        framePath = path;
        this.farmio = farmio;
    }

    public Simulation(String path, Ui ui) {
        this.ui = ui;
        framePath = path;
        this.farmio = null;
    }

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

    private static void refresh(Ui ui) {
        try {
            TimeUnit.MILLISECONDS.sleep((int) (1000 / FRAME_PER_SECOND) );
            ui.clearScreen();
        } catch (InterruptedException e) {
            ui.clearScreen();
            ui.showWarning("Animate refersh interrupted! Ui may not show correctly.");
        }
    }
}

//    public void animate(int frame) {
//        try {
//            TimeUnit.MILLISECONDS.sleep((int) (1000 / framePerSecond) );
//        } catch (InterruptedException e) {
//            e.getMessage();
//        }
//        ui.clearScreen();
//        if (farmio == null) {
//            ui.show(GameConsole.blankConsole(ui.loadStage(basepath, frame, 103, 22)));
//        } else {
//            ui.show(GameConsole.content(ui.loadStage(basepath, frame, 55, 18), farmio));
//        }
//    }

//    public void animate(int startFrame, int endFrame) {
//        for (int i=startFrame; i<=endFrame; i++) {
//            animate(i);
//        }
//    }
//
//    public void delayFrame(int frame, int delay) {
//        animate(frame);
//        try {
//            TimeUnit.MILLISECONDS.sleep(delay);
//        } catch (InterruptedException e) {
//            e.getMessage();
//        }
//    }
