package duke;

import duke.commands.AddBarCommand;
import duke.commands.Command;
import duke.commands.HelpCommand;
import duke.commands.NewCommand;
import duke.commands.RemindCommand;
import duke.commands.ViewCommand;
import duke.components.SongList;


import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;

public class Duke {
    /**
     * Chat bot cum task management application that can handle events, deadlines and normal to-do tasks,
     * as well as basic exception handling.
     */
    private Storage storage;
    private TaskList tasks;
    private SongList songs;
    private Ui ui;


    /**
     * Constructor for the duke.Duke object, which initializes the UI, duke.TaskList and duke.Storage in
     * order to carry out its functions.
     */
    public Duke() {
        ui = new Ui();
        tasks = new TaskList();
        songs = new SongList();
        storage = new Storage(Paths.get("data", "todo_list.txt"));
        try {
            storage.loadList(tasks);
        } catch (DukeException e) {
            System.out.println(ui.showError(e));
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
        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                Command c = new RemindCommand();
                String output = null;
                try {
                    output = c.execute(tasks, ui, storage);
                    if (!output.equals("")) {
                        System.out.println(output);
                    }
                } catch (DukeException e) {
                    System.out.println(ui.showError(e));
                }
            }
        };
        Timer timer = new Timer("Timer");
        timer.scheduleAtFixedRate(repeatedTask, 1000, 900000);

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command c = Parser.parse(fullCommand);
                //if the command uses the SongList
                String output;
                if (c instanceof AddBarCommand || c instanceof ViewCommand
                        || c instanceof NewCommand || c instanceof HelpCommand) {
                    output = c.execute(songs, ui, storage);
                } else {
                    output = c.execute(tasks, ui, storage);
                }
                System.out.println(output);
                isExit = c.isExit();
            } catch (DukeException e) {
                System.out.println(ui.showError(e));
            }
        }
        timer.cancel();
    }

    /**
     * duke.Main function for duke.Duke, which creates a new duke.Duke object and runs it.
     * @param args Standard Java arguments for a main function, in this case, not used
     */
    public static void main(String[] args) {
        new Duke().run();
    }


}
