package dolla;

import dolla.command.Command;
import dolla.task.EntryList;
import dolla.task.LogList;
import dolla.task.Task;
import dolla.task.TaskList;
import dolla.parser.MainParser;

import java.util.ArrayList;
import java.util.Scanner;

import static dolla.Storage.load;

/**
 * <h1>duke.Dolla</h1>
 * duke.Dolla is a chat-bot styled todo_list manager.
 *
 * @author  Aik Peng
 * @version 1.0
 * @since   2019-07-26
 */
public class Dolla {

    private TaskList tasks = new TaskList(new ArrayList<Task>());
    private DollaData dollaData = new DollaData();
    //    Storage storage = new Storage();
    /**
     * Creates an instance of Dolla using a data loaded from /data/dolla.txt
     */
    public Dolla() {
        //tasks = new TaskList(Storage.load());
        load(); //load from save
    }

    /**
     * Runs the main program of duke.Dolla
     * @throws Exception when exceptional condition happens
     */
    public void run() throws Exception {
        //Reminder reminderObject = new Reminder(); //reminder pop up
        //reminderObject.execute(tasks);
        boolean isExit = false;
        System.out.println("hi");
        Reminder reminder = new Reminder("debt");
        reminder.showReminder(dollaData);
        Scanner input = new Scanner(System.in); // TODO: Add to Ui or MainParser instead?
        while (isExit == false) {
            if (input.hasNextLine()) {
                String inputLine = input.nextLine();
                if (inputLine.equals("bye")) {
                    isExit = true;
                    MainParser.exit();
                } else {
                    Command c = MainParser.handleInput(dollaData.getMode(), inputLine);
                    c.execute(dollaData);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception { // Exception needs to be handled?
        new Dolla().run();
    }
}
