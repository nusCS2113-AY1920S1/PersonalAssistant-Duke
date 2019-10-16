package javacake;

import javacake.commands.Command;
import javacake.commands.QuizCommand;
import javacake.quiz.Question;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;

public class Duke  {
    private static boolean isCliMode = true;
    private static String savedDataPath = "data/saved_data.txt";
    private static Ui ui;
    private static Storage storage;
    private static ProgressStack progressStack;
    public static Profile profile;
    public static boolean isFirstTimeUser;
    public static String userName;
    public static int userProgress = 0;


    /**
     * Constructor for main class to initialise the settings.
     */

    public Duke(String filePath) {
        ui = new Ui();
        try {
            progressStack = new ProgressStack();
            //storage = new Storage(filePath);
            //tasks = new TaskList(storage.load());
            profile = new Profile("data/save/savefile.txt", ui);
            userProgress = profile.getTotalProgress();
            userName = profile.getUsername();
            // Default username when creating new profile
            if (userName.equals("NEW_USER_!@#")) {
                isFirstTimeUser = true;
            } else {
                isFirstTimeUser = false;
            }
        } catch (DukeException e) {
            ui.showLoadingError();
        }
    }


    /**
     * Run the rest of the code here. CLI MODE.
     */
    private void run() {
        try {
            userName = ui.showWelcome(isFirstTimeUser, userName, userProgress);
        } catch (DukeException e) {
            ui.showError(e.getMessage());
        }
        //To overwrite "NEW_USER_!@# with new inputted username if needed
        if (isFirstTimeUser) {
            try {
                profile.overwriteName(userName);
                ui.showLine();
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            }
        }
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                ui.showMessage(c.execute(progressStack, ui, storage, profile));
                isExit = c.isExit();
                //System.out.println("Current progress is " + progressStack.checkProgress());
            } catch (DukeException | IOException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * Program Start.
     */
    public static void main(String[] args) {
        new Duke(savedDataPath).run();
    }

    /**
     * You should have your own function to generate a response to user input.
     * GUI MODE.
     */
    public String getResponse(String input) {
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

    public static boolean isCliMode() {
        return isCliMode;
    }
}