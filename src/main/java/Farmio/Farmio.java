package Farmio;

import Commands.Command;
import Commands.CommandWelcome;
import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import FrontEnd.Ui;

public class Farmio {
    private Storage storage;
    private Farmer farmer;
    private Ui ui;
    private Level level;
    private boolean isExit;
    private Stage stage;

    private Farmio() {
        storage = new Storage();
        farmer = new Farmer(); //for ui testing not originally here
        ui = new Ui();
        stage = Stage.WELCOME;
        isExit = false;
    }

    private void run() {
        Command command;
        command = new CommandWelcome();
        try {
            try {
                command.execute(this);
            } catch (FarmioException e) {
                ui.showWarning(e.getMessage());
            }
            while (!isExit) {
                try {
                    command = Parser.parse(ui.getInput(), stage);
                    command.execute(this);
                } catch (FarmioException e) {
                    ui.showWarning(e.getMessage());
                }
            }
        } catch (FarmioFatalException e) {
            ui.showError(e.getMessage());
            ui.showInfo("Encounterd fatal error. Exiting program.");
        }
        //save the game before quitting
        ui.showExit();
    }

    public static void main(String[] args) {    //TODO - configure both OS
        new Farmio().run();
    }

    public static enum Stage {
        WELCOME, MENU_START, TASK_ADD, RUNNING_DAY, CHECK_OBJECTIVES, END_OF_DAY, START_OF_DAY
    }

    public Storage getStorage() {
        return storage;
    }

    public Ui getUi() {
        return ui;
    }

    public Farmer getFarmer() {
        return farmer;
    }

    public Stage getStage() {
        return stage;
    }

    public Level getLevel() {
        return level;
    }

    public void setFarmer(Farmer farmer) {
        this.farmer = farmer;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setExit() {
        this.isExit = true;
    }
}