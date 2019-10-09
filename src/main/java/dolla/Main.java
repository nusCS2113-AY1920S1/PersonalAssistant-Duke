package dolla;

import command.Command;
import dolla.task.Task;
import dolla.task.TaskList;
import parser.MainParser;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * <h1>duke.Main</h1>
 * duke.Main is a chat-bot styled todo_list manager.
 *
 * @author  Aik Peng
 * @version 1.0
 * @since   2019-07-26
 */
public class Main {

    private TaskList tasks = new TaskList(new ArrayList<Task>());
    private String mode = "dolla";

    /**
     * Creates an instance of Main using a data loaded from /data/dolla.txt
     */
    public Main() {
        //tasks = new TaskList(Storage.load());
    }

    /**
     * Runs the main program of duke.Main
     * @throws Exception when exceptional condition happens
     */
    public void run() throws Exception {
        //Reminder reminderObject = new Reminder(); //reminder pop up
        //reminderObject.execute(tasks);
        boolean isExit = false;
        System.out.println("hi");
        Scanner input = new Scanner(System.in); // TODO: Add to Ui or MainParser instead?
        while (isExit == false) {
            if (input.hasNextLine()) {
                String inputLine = input.nextLine();
                if (inputLine.equals("bye")) {
                    isExit = true;
                    MainParser.exit();
                } else {
                    Command c = MainParser.handleInput(mode, inputLine);
                    c.execute();
                    //Storage.save(tasks.get());
                }
            }
        }
    }

    public static void main(String[] args) throws Exception { // Exception needs to be handled?
        new Main().run();
    }
}
