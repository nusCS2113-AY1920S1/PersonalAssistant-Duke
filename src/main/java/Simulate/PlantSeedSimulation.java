package Simulate;

import FarmioExceptions.FarmioException;
import UserInterfaces.Ui;

public class PlantSeedSimulation extends Simulation {
    public PlantSeedSimulation(Ui userInterface) {
        super(userInterface);
        super.numberOfFrames = 10;
        super.basepath = "./src/main/resources/asciiArt/PlantSeedSimulation/frame";
        //frames are named frame0.txt,frame1.txt,...frame10.txt
    }
}
