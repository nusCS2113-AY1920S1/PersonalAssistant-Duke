package FrontEnd;

import Farmio.Farmio;
import FrontEnd.Ui;

import java.util.concurrent.TimeUnit;

public class Simulate {
    //this class now does shit if changes to farmio happen after animation
    private String basepath;
    private Farmio farmio;
    private final int framePerSecond = 3;
    protected Ui ui;

    public Simulate(String path, Farmio farmio) {
        ui = farmio.getUi();
        basepath = path;
        this.farmio = farmio;
    }
    public void showFrame(int frame) {
        try {
            TimeUnit.MILLISECONDS.sleep((int) (1000 / framePerSecond) );
        } catch (InterruptedException e) {
            e.getMessage();
        }
        ui.clearScreen();
        ui.show(GameConsole.content(ui.loadStage(basepath, frame), farmio));
    }
    public void simulate(int startFrame, int endFrame) {
        for (int i=startFrame; i<=endFrame; i++) {
            showFrame(i);
        }
    }
}