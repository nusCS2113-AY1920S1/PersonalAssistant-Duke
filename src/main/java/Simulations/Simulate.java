package Simulations;

import FrontEnd.Ui;

import java.util.concurrent.TimeUnit;

public class Simulate {
    //need run in terminal not console for clear screen to work
    protected String basepath;
    protected int numberOfFrames;
    private final int framePerSecond = 3;
    protected Ui ui;

    public Simulate(Ui userInterface, String path, int numFrames) {
        ui = userInterface;
        basepath = "./src/main/resources/asciiArt/" + path + "/frame";
        numberOfFrames = numFrames;
    }
    protected void nextFrame(String statusBar, String filePath) {
        try {
            TimeUnit.MILLISECONDS.sleep((int) (1000 / framePerSecond) );
        } catch (InterruptedException e) {
            e.getMessage();
        }
        ui.clearScreen();
        ui.showStatusbar();
        ui.showAsciiArt(filePath);
    }
    public void simulate() {
        for (int i=0; i<numberOfFrames; i++) {
            nextFrame("statusBar", basepath + i + ".txt");
        }
    }
}