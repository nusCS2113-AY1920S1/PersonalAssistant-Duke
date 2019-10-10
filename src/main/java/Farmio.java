import Commands.Command;
import FarmioExceptions.FarmioException;
import UserInterfaces.Ui;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Farmio {
    private Storage storage;
    private Farmer farmer;
    private Ui ui;
    private Parser parser;

    private Farmio() {
        this.ui = new Ui();
        this.storage = new Storage();
    }

    private void run() {

        /*Print Random Shit
         */
        for(int x = 0; x != 10 ; x++){
            System.out.println();
        }

        System.out.println("STARTING NEW GAME : LEVEL 1 - Wheat Farm");
        for(int x = 0; x != 1 ; x++){
            System.out.println();
        }

        System.out.println("|------------|");
        System.out.println("|            |");
        System.out.println("|   Insert   |");
        System.out.println("|   Farmer   |");
        System.out.println("|   Pic      |");
        System.out.println("|            |");
        System.out.println("|------------|");

        System.out.println("|---------------------------------------------------------------------------------------|");
        System.out.println("|  Welcome to Farmio Farmer John!                                                       |");
        System.out.println("|      In this game we you will be tasked to complete a series of  challenging tasks    |");
        System.out.println("|      This game intends expose you to the world of Computational thinking              |");
        System.out.println("|                                                                                       |");
        System.out.println("|   PRESS ENTER TO CONTINUE                                                    .   .  . |");
        System.out.println("|---------------------------------------------------------------------------------------|");

        for(int x = 0; x != 10 ; x++){
            System.out.println();
        }


















    /*
        displayWelcome();
        displayMenu();
        this.parser = new Parser(ui, farmer.tasks, farmer.wheatFarm, farmer.chickenFarm, farmer.cowFarm);
        boolean isExit = false;
        while(!isExit) {
            //introduce the problem, and show the tutorial, and show the conditions and the possible tasks and gets the user input
            loadLevel(farmer);
            //create the new task, and add to the tasklist or do whatever
            isExit = getUserActions(farmer, ui, parser);
            if (isExit) {
                break;
            }
            farmer.startDay();
            //TODO: Maybe another method?
            try {
                Storage.storeFarmer(farmer);
            } catch (IOException e) {
                ui.showError("Unsuccessful game save.");
                ui.showInfo("No gave save was done.");
            }
            checkObjectives(farmer);




        }
*/
    }

    public static void main(String[] args) {    //TODO - configure both OS
        new Farmio().run();
    }

    public boolean getUserActions(Farmer farmer, Ui ui, Parser parser) {
        boolean isStart = false;
        boolean isExit = false;
        while (!isStart && !isExit) {
            String fullCommand = ui.getInput();
            try {
                Command c = parser.parse(fullCommand);
                c.execute();
                isStart =  c.getIsStart();
                isExit = c.getIsExit();
            } catch (FarmioException e) {
                e.getMessage();
            }
        }
        return isExit;
    }

    private void displayArt(String name) {
        try{
            ui.show(storage.getAsciiArt(name));
        } catch (IOException e) {
            ui.showWarning(name.substring(0, 1).toUpperCase() + name.substring(1) + " ascii art missing!");
        }
    }

    private void displayWelcome() {
        displayArt("welcome");
        ui.show("Press ENTER to continue.");
        ui.getInput();
    }

    private void displayMenu(){
        displayArt("menu");
        ui.showMenu(storage.getFarmerExist());
        //TODO: convert swtich into parser
        while(true){
            switch(ui.getInput().toLowerCase()){
                case "load save":
                    try {
                        this.farmer = storage.loadFarmer();
                        return;
                    } catch (ParseException e) {
                        ui.showWarning("Game save is corrupted!");
                        ui.showInfo("Farmio starting a new game.");
                    } catch (IOException e) {
                        ui.showWarning("No game save detected!");
                        ui.showInfo("Farmio starting a new game.");
                    }
                case "new game":
                    this.farmer = new Farmer();
                    return;
                case "quit":
                    System.exit(0);
                default:
                    ui.showWarning("Unknown command.");
            }
        }
    }

    private static void loadLevel(Farmer farmer) {

    }

    private static void checkObjectives(Farmer farmer) {

    }
}