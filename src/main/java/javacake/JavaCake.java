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
    private Ui ui;
    private static Logic logic;

    public StorageManager storageManager;
    public boolean isFirstTimeUser;
    public String userName;
    public int userProgress = 0;
    public static Logger LOGGER = Logger.getLogger(JavaCake.class.getPackageName());


    /**
     * Constructor for main class to initialise the settings.
     */
    public JavaCake() throws CakeException {
        LOGGER.setUseParentHandlers(true);
        LOGGER.setLevel(Level.INFO);
        LOGGER.entering(getClass().getName(), "JavaCake");
        ui = new Ui();
        try {
            logic = Logic.getInstance();
            storageManager = new StorageManager();
            userProgress = storageManager.profile.getTotalProgress();
            System.out.println("userProgress is " + userProgress);
            userName = storageManager.profile.getUsername();
            System.out.println("userName is " + userName);
            // Default username when creating new profile
            checkIfNewUser("NEW_USER_!@#");
        } catch (CakeException e) {
            ui.showLoadingError();
            LOGGER.severe("[GUI] Storage/Profile Load failed, taking out the trash...");
            throw new CakeException(e.getMessage());
        }
        LOGGER.exiting(getClass().getName(), "initialize");
    }

    /**
     * Constructor for main class to initialise the settings[TEST].
     */
    public JavaCake(String testFilePath) {
        LOGGER.info("Starting JavaCake Constructor!");
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
            LOGGER.severe("[CLI] Storage/Profile Load failed, taking out the trash...");
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
                LOGGER.severe("Profile overwrite failed.");
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
            LOGGER.severe("Failed to load cake.txt!");
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
        LOGGER.entering(getClass().getName(), "getResponse");
        storageManager.profile.isCli = false;
        try {
            Command c = Parser.parse(input);
            LOGGER.exiting(getClass().getName(), "getResponse");
            return c.execute(logic, ui, storageManager);
        } catch (CakeException e) {
            LOGGER.warning(e.getMessage());
            return e.getMessage();
        }
    }

    /**
     * Program Start.
     */
    public static void main(String[] args) {
        try {
            new JavaCake().runAsCli();
        } catch (CakeException e) {
            System.out.println(e.getMessage());
        }
    }
}