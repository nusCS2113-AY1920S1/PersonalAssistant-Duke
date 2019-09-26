package javacake;

import javacake.commands.Command;

public class Duke {
    private static String savedDataPath = "data/saved_data.txt";
    private static Ui ui;
    private static Storage storage;
    private static TaskList tasks;
    private static Profile profile;
    private static boolean isFirstTimeUser;
    private static String userName = "Glen";
    private static int userProgress = 0;
    

    /**
     * Constructor for main class to initialise the settings.
     */
    private Duke(String filePath) {
        ui = new Ui();
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
        try {
            profile.overwriteName(userName);
        } catch (DukeException e) {
            ui.showError(e.getMessage());
        }
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
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

}