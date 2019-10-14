package Simulations;

import Farmio.Ui;

import java.util.concurrent.TimeUnit;

public class Simulate {
    //need run in terminal not console for clear screen to work
    protected String basepath;
    protected int numberOfFrames;
    private final int framePerSecond = 3;
    protected Ui ui;

    public Simulate(Ui userInterface, Simulation simulate) {
        ui = userInterface;
        if (simulate == Simulation.PLANTSEED) {
            basepath = "./src/main/resources/asciiArt/PlantSeedSimulation/frame";
            numberOfFrames = 10;
        }
    }
    protected void nextFrame(String statusBar,String filePath) {
        try {
            TimeUnit.MILLISECONDS.sleep((int) (1000 / framePerSecond) );
        } catch (InterruptedException e) {
            //we'll handle this later
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