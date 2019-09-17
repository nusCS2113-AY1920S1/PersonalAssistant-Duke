package wallet;
import wallet.record.ExpenseList;
import wallet.task.ScheduleList;
import wallet.command.Command;
import wallet.storage.Storage;
import wallet.task.Task;
import wallet.task.TaskList;
import wallet.ui.Reminder;
import wallet.ui.Ui;

import java.util.ArrayList;


public class Main {
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
    private ScheduleList scheduleList;
    private ExpenseList expenseList;
    /**
     * The Reminder object that handles the reminder of undone tasks
     */
    private Reminder reminder;

    /**
     * Constructs a new ui.Duke object.
     * @param path The path of the save file in the local computer.
     */
    public Main(String path){
        ui = new Ui();
        storage = new Storage(path);
        taskList = new TaskList((ArrayList<Task>) storage.loadFile());
        reminder = new Reminder(taskList);
        expenseList = new ExpenseList();
    }

    public static void main(String[] args) {
        new Main(".\\duke.txt").run();
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
            isExit = Command.parse(cmd, taskList, storage, scheduleList, expenseList);
            ui.printLine();
        }
        ui.byeMsg();
    }
}
