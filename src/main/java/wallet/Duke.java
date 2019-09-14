package wallet;

import wallet.command.Command;
import wallet.storage.Storage;
import wallet.task.Task;
import wallet.task.TaskList;
import wallet.ui.Reminder;
import wallet.ui.Ui;

import java.util.ArrayList;

public class Duke {
    /**
     * The Ui object that handles input and output of the application
     */
    private Ui ui;
    /**
     * The Storage object that handles the read and write of text file from the local computer.
     */
    private Storage storage;
    /**
     * The TaskList object that handles the list of task added by the user.
     */
    private TaskList taskList;

    /**
     * The Reminder object that handles the reminder of undone tasks
     */
    private Reminder reminder;

    /**
     * Constructs a new ui.Duke object.
     * @param path The path of the save file in the local computer.
     */
    public Duke(String path){
        ui = new Ui();
        storage = new Storage(path);
        taskList = new TaskList((ArrayList<Task>) storage.loadFile());
        reminder = new Reminder(taskList);

    }

    public static void main(String[] args) {
        new Duke(".\\duke.txt").run();
    }

    /**
     * Execute and run the Duke application.
     */
    public void run(){
        ui.welcomeMsg();
        reminder.autoReminder();
        boolean isExit = false;
        while (!isExit){
            String cmd = ui.readLine();
            ui.printLine();
            isExit = Command.parse(cmd, taskList, storage);
            ui.printLine();
        }
        ui.byeMsg();
    }
}
