package Farmio;

import FrontEnd.Simulation;
import FrontEnd.Ui;

import java.io.IOException;

public class Menu {
    private static final String ART_NAME = "menu";
    private static final String BULLET = "\t\u2022 ";
    public static void show(Ui ui, Storage storage, boolean resume){
        ui.clearScreen();
        ui.show("Loading...");
        StringBuilder menu = new StringBuilder();
        try{
            menu.append(storage.getAsciiArt(ART_NAME));
        } catch (IOException e) {
            ui.showWarning(ART_NAME.substring(0, 1).toUpperCase() + ART_NAME.substring(1) + " ascii art missing!\n");
        }
//        menu.append("\nMenu:\n");
//        if(resume){
//            menu.append(BULLET);
//            menu.append("Resume Game\n");
//            menu.append(BULLET);
//            menu.append("Save Game\n");
//        }
//        if(storage.getFarmerExist()){
//            menu.append(BULLET);
//            menu.append("Load Game\n");
//        }
//        menu.append(BULLET);
//        menu.append("New Game\n");
//        menu.append(BULLET);
//        menu.append("Quit Game");
//        ui.clearScreen();
//        ui.show(menu.toString());
        Simulation menuSimulation = new Simulation("Menu", ui);
        if(resume && storage.getFarmerExist()) { //resume, save and load
            menuSimulation.animate(2);
            return;
        }
        if(resume){ //resume and save
            menuSimulation.animate(4);
            return;
        }
        if(storage.getFarmerExist()){ //load
            menuSimulation.animate(3);
            return;
        }
        menuSimulation.animate(0); //only new and quit
    }

    public void showLoadGameWarning(Ui ui, Storage storage, boolean resume){
        if(resume){
            ui.showWarning("Current game session will be overwritten!");
            ui.show("Are you sure?");
        }
    }

}
