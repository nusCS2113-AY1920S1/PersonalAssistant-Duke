package farmio;

import commands.Command;
import commands.CommandWelcome;
import exceptions.FarmioException;
import exceptions.FarmioFatalException;
import frontend.AsciiColours;
import frontend.Simulation;
import frontend.Ui;
import frontend.UiManager;

import java.io.IOException;
import java.util.EnumSet;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Farmio {
    private final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private Storage storage;
    private Farmer farmer;
    private Simulation simulation;
    private Ui ui;
    private Level level;
    private boolean isExit;
    private Stage stage;

    public Farmio() {
        storage = new StorageManager();
        farmer = new Farmer();
        ui = new UiManager();
        simulation = new Simulation(this);
        stage = Stage.WELCOME;
        isExit = false;
        if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
            AsciiColours.inActivate();
        }
    }

    private void run() {
        try {
            setupLogger();
            LOGGER.log(java.util.logging.Level.INFO, "New game session started.");
            Command command;
            command = new CommandWelcome();
            try {
                command.execute(this);
            } catch (FarmioException e) {
                ui.showWarning(e.getMessage());
            }
            while (!isExit) {
                try {
                    if (Stage.noInput.contains(stage)) {
                        command = Parser.parse(" ", stage);
                    } else {
                        command = Parser.parse(ui.getInput(), stage);
                    }
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
        storage.storeFarmerPartial(farmer);
        ui.showExit();
    }

    public static void main(String[] args) {
        new Farmio().run();
    }

    public enum Stage {
        WELCOME,
        MENU_START,
        NAME_ADD,
        LEVEL_START,
        MENU,
        TASK_ADD,
        RUNNING_DAY,
        CHECK_OBJECTIVES,
        DAY_END,
        DAY_START,
        LEVEL_END,
        LEVEL_FAILED;

        public static EnumSet<Stage> noInput = EnumSet.of(LEVEL_START, RUNNING_DAY, CHECK_OBJECTIVES, DAY_START, LEVEL_END, LEVEL_FAILED);
        public static EnumSet<Stage> reqInput = EnumSet.complementOf(noInput);
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

    public void setUi(Ui dummyUi){ui = dummyUi;}

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setExit() {
        this.isExit = true;
    }

    private void setupLogger() throws FarmioFatalException {
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        for(Handler handler: handlers){
            rootLogger.removeHandler(handler);
        }
        logger.setLevel(java.util.logging.Level.INFO);
        FileHandler handler;
        try {
            handler = new FileHandler("farmio.log");
        } catch (IOException e) {
            throw new FarmioFatalException("Failed to access \'farmio.log\'.\nPlease try running farmio in another directory.");
        }
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);
    }
}