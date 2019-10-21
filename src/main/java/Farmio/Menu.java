package Farmio;

import Exceptions.FarmioFatalException;
import FrontEnd.Simulation;
import FrontEnd.Ui;

import java.io.IOException;

public class Menu {
    private static final String ART_NAME = "menu";
    private static final String BULLET = "\t\u2022 ";
    public static void show(Farmio farmio, boolean resume) throws FarmioFatalException {
        Storage storage = farmio.getStorage();
        Ui ui = farmio.getUi();
        Simulation simulation =farmio.getSimulation();
        if(resume && storage.getSaveExist()) { //resume, save and load
            simulation.animate("menu", 2, true);
            return;
        }
        if(resume){ //resume and save
            simulation.animate("menu", 4, true);
            return;
        }
        if(storage.getSaveExist()){ //load
            simulation.animate("menu", 3, true);
            return;
        }
        simulation.animate("menu", 0, true); //only new and quit
    }

    public void showLoadGameWarning(Ui ui, Storage storage, boolean resume){
        if(resume){
            ui.showWarning("Current game session will be overwritten!");
            ui.show("Are you sure?");
        }
    }

}
