import java.util.Scanner;

/**
 * <h1>Duke</h1>
 * Duke is a chat-bot styled todo_list manager.
 *
 * @author  Aik Peng
 * @version 1.0
 * @since   2019-07-26
 */
public class Duke {

    private TaskList tasks;

    /**
     * Creates an instance of Duke using a task list loaded from /data/duke.txt
     */
    public Duke() {
        tasks = new TaskList(Storage.load());
    }

    /**
     * Runs the main program of Duke
     * @throws Exception
     */
    public void run() throws Exception {
//        Ui.showWelcome(); // inside Storage
        boolean isExit = false;
        Scanner input = new Scanner(System.in); // TODO: Add to Ui instead?
        while (isExit == false) {
            if (input.hasNextLine()) {
                String inputLine = input.nextLine();
                if (inputLine.equals("bye")) {
                    isExit = true;
                    Parser.exit();
                } else {
                    Command c = Parser.handleInput(inputLine, tasks);
                    c.execute(tasks);
                    Storage.save(tasks.list);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception { // Exception needs to be handled?
        new Duke().run();
    }
}
