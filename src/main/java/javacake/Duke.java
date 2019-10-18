package javacake;

import javacake.commands.Command;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Duke  {
    private static String savedDataPath = "data/";
    private static Ui ui;
    private static Storage storage;
    private static ProgressStack progressStack;
    private static boolean isCliMode = true;

    public static Profile profile;
    public static boolean isFirstTimeUser;
    public static String userName;
    public static int userProgress = 0;
    public static Logger logger = Logger.getLogger("JavaCake");


    /**
     * Constructor for main class to initialise the settings.
     */
    public Duke(String filePath) {
        logger.log(Level.INFO, "Starting Duke Constructor!");
        ui = new Ui();
        try {
            progressStack = new ProgressStack();
            //storage = new Storage(filePath);
            //tasks = new TaskList(storage.load());
            profile = new Profile(filePath);
            userProgress = profile.getTotalProgress();
            userName = profile.getUsername();
            // Default username when creating new profile
            checkIfNewUser("NEW_USER_!@#");
        } catch (DukeException e) {
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
    private void initialiseWelcomeCliMode() {
        ui.showMessage(Ui.showWelcomeMsgPhaseA(isFirstTimeUser));
        if (isFirstTimeUser) {
            userName = ui.readCommand();
        } else {
            loadCake();
        }
        //To overwrite "NEW_USER_!@# with new inputted username if needed
        if (isFirstTimeUser) {
            try {
                profile.overwriteName(userName);
                ui.showLine();
            } catch (DukeException e) {
                ui.showError(e.getMessage());
                logger.log(Level.WARNING, "Profile overwrite failed.");
            }
        }

        ui.showMessage(Ui.showWelcomeMsgPhaseB(isFirstTimeUser, userName, userProgress));
    }

    /**
     * Method to load cake ASCII.
     */
    private void loadCake() {
        try {
            ui.showMessage(Ui.getTextFile(new BufferedReader(
                    new FileReader("src/main/resources/content/cake.txt"))));
        } catch (DukeException | FileNotFoundException e) {
            ui.showError(e.getMessage());
            logger.log(Level.WARNING, "Failed to load cake.txt!");
        }
    }


    /**
     * Run the rest of the code here: CLI MODE.
     */
    private void runAsCli() {
        boolean isExit = false;
        initialiseWelcomeCliMode();
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                ui.showMessage(c.execute(progressStack, ui, storage, profile));
                isExit = c.isExit();
            } catch (DukeException | IOException e) {
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
            return c.execute(progressStack, ui, storage, profile);
        } catch (DukeException | IOException e) {
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
        new Duke(savedDataPath).runAsCli();
    }
}