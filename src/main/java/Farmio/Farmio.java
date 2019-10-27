package Farmio;

import Commands.*;
import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import FrontEnd.Simulation;
import FrontEnd.Ui;

public class Farmio {
    private Storage storage;
    private Farmer farmer;
    private Simulation simulation;
    private Ui ui;
    private Level level;
    private boolean isExit;
    private Stage stage;

    public Farmio() {
        storage = new Storage();
        farmer = new Farmer();
        ui = new Ui();
        simulation = new Simulation(this);
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
                    command = checkStage(stage) == null ? Parser.parse(ui.getInput(), stage) : checkStage(stage);
//                    command = Parser.parse(ui.getInput(), stage); //TODO jx help me review if there is better way
                    command.execute(this);
                } catch (FarmioException e) {
                    simulation.simulate();
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
        WELCOME,
        MENU_START,
        LEVEL_START,
        TASK_ADD,
        RUNNING_DAY,
        CHECK_OBJECTIVES,
        DAY_END,
        DAY_START,
        LEVEL_END,
        LEVEL_FAILED
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

    public Simulation getSimulation() {return simulation;}

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

    private static Command checkStage(Farmio.Stage stage) {
        switch (stage) {
            case LEVEL_START:
                return new CommandLevelStart();
            case RUNNING_DAY:
                return new CommandTasksRun();
            case CHECK_OBJECTIVES:
                return new CommandCheckObjectives();
            case DAY_START:
                return new CommandDayStart();
            case LEVEL_END:
                return new CommandLevelEnd();
            case LEVEL_FAILED:
                return new CommandLevelReset();
            default:
                return null;
        }
    }
}