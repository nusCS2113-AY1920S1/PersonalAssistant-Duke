package duke;

import duke.commands.Command;

import java.nio.file.Paths;

public class Duke  {
    /**
     * A chat bot cum task management application that can handle events, deadlines and normal to-do tasks,
     * as well as basic exception handling.
     */
    private Storage storage;
    private TaskList tasks;
    private Ui ui;


    /**
     * Constructor for the duke.Duke object, which initializes the UI, duke.TaskList and duke.Storage in
     * order to carry out its functions.
     */
    public Duke() {
        ui = new Ui();
        tasks = new TaskList();
        System.out.println("/home/rishi/Desktop/cs2113t/team/main/data/todo_list.txt");
        storage = new Storage(Paths.get("/home/rishi/Desktop/cs2113t/team/main/data/todo_list.txt"));
        try {
            storage.loadList(tasks);
        } catch (DukeException e) {
            ui.showError(e);
            tasks = new TaskList();
        }
    }

    /**
     * Runs the program, constantly asking for and responding to user input, finally terminating
     * upon the word "Bye".
     */
    private void run() {
        System.out.println(ui.showWelcomeMessage());
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command c = Parser.parse(fullCommand);
                String output = c.execute(tasks, ui, storage);
                System.out.println(output);
                isExit = c.isExit();
            } catch (DukeException e) {
                System.out.println(ui.showError(e));
            }
        }
    }

    /**
     * duke.Main function for duke.Duke, which creates a new duke.Duke object and runs it.
     * @param args Standard Java arguments for a main function, in this case, not used
     */
    public static void main(String[] args) {
        new Duke().run();
    }


    /**
     * Returns a String, which is the response of duke.Duke in accordance to the input.
     *
     * @param input the String typed in as an input for duke.Duke
     * @return the response String to be displayed
     */
    public String getResponse(String input) {
        try {
            Command c = Parser.parse(input);
            return c.execute(tasks, ui, storage);
            //isExit = c.isExit();
        } catch (DukeException e) {
            return ui.showError(e);
        }
    }

}