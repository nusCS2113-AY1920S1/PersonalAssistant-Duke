package javacake;

import javacake.commands.Command;
import javacake.exceptions.CakeException;
import javacake.storage.StorageManager;
import javacake.ui.Ui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaCake {
    private static Ui ui;
    private static Logic logic;
    private static boolean isCliMode = true;

    public static StorageManager storageManager;
    //    public static Storage storage;
    //    public static Profile profile;
    public static boolean isFirstTimeUser;
    public static String userName;
    public static int userProgress = 0;
    public static Logger logger = Logger.getLogger("JavaCake");


    /**
     * Constructor for main class to initialise the settings.
     */
    public JavaCake() {
        logger.log(Level.INFO, "Starting JavaCake Constructor!");
        ui = new Ui();
        try {
            logic = Logic.getInstance();
            storageManager = new StorageManager();
            userProgress = storageManager.profile.getTotalProgress();
            userName = storageManager.profile.getUsername();
            // Default username when creating new profile
            checkIfNewUser("NEW_USER_!@#");
        } catch (CakeException e) {
            ui.showLoadingError();
            logger.log(Level.WARNING, "Profile set-up failed.");
        }
    }

    /**
     * Constructor for main class to initialise the settings[TEST].
     */
    public JavaCake(String testFilePath) {
        logger.log(Level.INFO, "Starting JavaCake Constructor!");
        ui = new Ui();
        try {
            logic = Logic.getInstance();
            storageManager = new StorageManager(testFilePath);
            userProgress = storageManager.profile.getTotalProgress();
            userName = storageManager.profile.getUsername();
            // Default username when creating new profile
            checkIfNewUser("NEW_USER_!@#");
        } catch (CakeException e) {
            ui.showLoadingError();
            logger.log(Level.WARNING, "Profile set-up failed.");
        }
    }

    private void checkIfNewUser(String defaultUsername) {
        if (userName.equals(defaultUsername)) {
            isFirstTimeUser = true;
        } else {
            isFirstTimeUser = false;
        }
    }

    /**
     * CLI method to overwrite username if initially default
     * and print the required welcome messages.
     */
    private void initialiseWelcomeCliMode() throws CakeException {
        ui.showMessage(Ui.showWelcomeMsgPhaseA(isFirstTimeUser));
        if (isFirstTimeUser) {
            userName = ui.readCommand();
        } else {
            loadCake();
        }
        //To overwrite "NEW_USER_!@# with new inputted username if needed
        if (isFirstTimeUser) {
            try {
                storageManager.profile.overwriteName(userName);
                ui.showLine();
            } catch (CakeException e) {
                ui.showError(e.getMessage());
                logger.log(Level.WARNING, "Profile overwrite failed.");
            }
        }
        ui.showMessage(Ui.showWelcomeMsgPhaseB(isFirstTimeUser, userName, storageManager));
    }

    /**
     * Method to load cake ASCII.
     */
    private void loadCake() {
        try {
            ui.showMessage(Ui.getTextFile(new BufferedReader(
                    new FileReader("src/main/resources/content/cake.txt"))));
        } catch (CakeException | FileNotFoundException e) {
            ui.showError(e.getMessage());
            logger.log(Level.WARNING, "Failed to load cake.txt!");
        }
    }


    /**
     * Run the rest of the code here: CLI MODE.
     */
    private void runAsCli() {
        boolean isExit = false;
        try {
            initialiseWelcomeCliMode();
        } catch (CakeException e) {
            e.printStackTrace();
        }
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                ui.showMessage(c.execute(logic, ui, storageManager));
                isExit = c.isExit();
            } catch (CakeException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * Public Method to get String response: GUI MODE.
     */
    public String getResponse(String input) {
        logger.log(Level.INFO, "Getting response from input...");
        if (isCliMode) {
            isCliMode = false;
        }
        try {
            Command c = Parser.parse(input);
            return c.execute(logic, ui, storageManager);
        } catch (CakeException e) {
            return e.getMessage();
        }
    }

    /**
     * Public Method to get type of mode being run.
     * @return true if is CLI mode
     */
    public static boolean isCliMode() {
        return isCliMode;
    }

    /**
     * Program Start.
     */
    public static void main(String[] args) {
        new JavaCake().runAsCli();
    }
}