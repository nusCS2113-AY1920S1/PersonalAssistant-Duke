package dolla;

import dolla.command.Command;
import dolla.task.Task;
import dolla.task.TaskList;

import java.util.ArrayList;
import java.util.Scanner;

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

    /**
     * Creates an instance of Dolla using a data loaded from /data/dolla.txt
     */
    public Dolla() {
        //tasks = new TaskList(Storage.load());
    }

    /**
     * Runs the main program of duke.Dolla
     * @throws Exception when exceptional condition happens
     */
    public void run() throws Exception {
        //Reminder reminderObject = new Reminder(); //reminder pop up
        //reminderObject.execute(tasks);
        boolean isExit = false;
        Scanner input = new Scanner(System.in); // TODO: Add to Ui or Parser instead?
        while (isExit == false) {
            if (input.hasNextLine()) {
                String inputLine = input.nextLine();
                if (inputLine.equals("bye")) {
                    isExit = true;
                    Parser.exit();
                } else {
                    Command c = Parser.handleInput(inputLine, tasks);
                    c.execute(tasks);
                    //Storage.save(tasks.get());
                }
            }
        }
    }

    public static void main(String[] args) throws Exception { // Exception needs to be handled?
        new Dolla().run();
    }
}
