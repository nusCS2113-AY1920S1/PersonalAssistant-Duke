package FrontEnd;

import Farmio.Farmio;

import java.util.concurrent.TimeUnit;

public class Simulation {
    private String basepath;
    private Farmio farmio;
    private final int framePerSecond = 2;
    protected Ui ui;

    public Simulation(String path, Farmio farmio) {
        ui = farmio.getUi();
        basepath = path;
        this.farmio = farmio;
    }

    public Simulation(String path, Ui ui) {
        this.ui = ui;
        basepath= path;
        this.farmio = null;
    }

    public void animate(int frame) {
        try {
            TimeUnit.MILLISECONDS.sleep((int) (1000 / framePerSecond) );
        } catch (InterruptedException e) {
            e.getMessage();
        }
        ui.clearScreen();
        if (farmio == null) {
            ui.show(GameConsole.blankConsole(ui.loadStage(basepath, frame, 103, 22)));
        } else {
            ui.show(GameConsole.content(ui.loadStage(basepath, frame, 55, 18), farmio));
        }
    }

    public void animate(int startFrame, int endFrame) {
        for (int i=startFrame; i<=endFrame; i++) {
            animate(i);
        }
    }

    public void delayFrame(int frame, int delay) {
        animate(frame);
        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            e.getMessage();
        }
    }
}