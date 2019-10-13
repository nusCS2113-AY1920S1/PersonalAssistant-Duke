import Commands.Command;
import FarmioExceptions.FarmioException;
import UserCode.Conditions.ConditionChecker;
import UserInterfaces.Ui;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Farmio {
    private Storage storage;
    private Farmer farmer;
    private Ui ui;
    private Parser parser;
    private ConditionChecker conditionChecker;

    private Farmio() {
        this.ui = new Ui();
        this.storage = new Storage();
    }

    private void run() {
        displayWelcome();
        displayMenu();
        this.conditionChecker = new ConditionChecker(farmer.wheatFarm, farmer.chickenFarm, farmer.cowFarm, farmer.market);
        this.parser = new Parser(ui, farmer, conditionChecker);
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
            try {
                Storage.storeFarmer(farmer);
            } catch (IOException e) {
                ui.showError("Unsuccessful game save.");
                ui.showInfo("No gave save was done.");
            }
            checkObjectives(farmer);
        }
    }

    public static void main(String[] args) {    //TODO - configure both OS
        new Farmio().run();
    }

    public boolean getUserActions(Farmer farmer, Ui ui, Parser parser) {
        boolean isStart = false;
        boolean isExit = false;
        while (!isStart && !isExit) {
            showLevelStatement(farmer, ui);
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

    private void showLevelStatement(Farmer farmer, Ui ui) {
        ui.show("Help Farmer John buy seeds IF he has more than 100G");
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
        ui.getEnter();
    }

    private void displayMenu(){
        displayArt("menu");
        ui.showMenu(storage.getFarmerExist());
        //TODO: convert swtich into parser
        while(true){
            switch(ui.getInput().toLowerCase()){
                case "load save":
                    try {
                        this.farmer = new Farmer(ui, storage.loadFarmer());
                        return;
                    } catch (FarmioException | ParseException e) {
                        ui.showWarning("Game save is corrupted!");
                        ui.showInfo("Farmio starting a new game.");
                    } catch (IOException e) {
                        ui.showWarning("No game save detected!");
                        ui.showInfo("Farmio starting a new game.");
                    }
                case "new game":
                    this.farmer = new Farmer(ui);
                    return;
                case "quit":
                    System.exit(0);
                default:
                    ui.showWarning("Unknown command.");
            }
        }
    }

    private void loadLevel(Farmer farmer) {
        switch (farmer.level) {
            case 1:
                //in the future, will be running animation instead of just showing static message
                displayArt("level1");
                break;
            /*
            case 2:
                displayArt("level2");
                break;
            case 3:
                displayArt("level3");
                break;
            case 4:
                displayArt("level4");
                break;
             */
        }
        ui.getEnter();
    }

    private void checkObjectives(Farmer farmer) {
        //in future, end of day will also do other stuff like making the plant grow etc
        ui.show("End of Day!");
        ui.getEnter();
    }
}