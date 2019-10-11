package javacake;

import javacake.commands.Command;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Duke  {

    private static String savedDataPath = "data/saved_data.txt";
    private static Ui ui;
    private static Storage storage;
    private static ProgressStack progressStack;
    private static Profile profile;
    private static boolean isFirstTimeUser;
    private static String userName = "Glen";
    private static int userProgress = 0;


    /**
     * Constructor for main class to initialise the settings.
     */

    public Duke(String filePath) {
        ui = new Ui();
        progressStack = new ProgressStack();
        try {
            //storage = new Storage(filePath);
            //tasks = new TaskList(storage.load());
            profile = new Profile("data/save/savefile.txt", ui);
            userProgress = profile.getTotalProgress();
            userName = profile.getUsername();
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
     * Run the rest of the code here.
     */
    private void run() {
        userName = ui.showWelcome(isFirstTimeUser, userName, userProgress);
        //TO OVERWRITE "NEW_USER_!@# with new inputted username if needed
        if (isFirstTimeUser) {
            try {
                profile.overwriteName(userName);
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
                c.execute(progressStack, ui, storage, profile);
                isExit = c.isExit();
                //System.out.println("Current progress is " + progressStack.checkProgress());
            } catch (DukeException e) {
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
     * Replace this stub with your completed method.
     */
    String getResponse(String input) throws DukeException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            // IMPORTANT: Save the old System.out!
            PrintStream old = System.out;
            // Tell Java to use your special stream
            System.setOut(ps);
            Command c = Parser.parse(input);
            c.execute(progressStack, ui, storage, profile);
            // Put things back
            System.out.flush();
            System.setOut(old);
            return baos.toString();
        } catch (DukeException e) {
            return "Invalid Command";
        }
    }







}