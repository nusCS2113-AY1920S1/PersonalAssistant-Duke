package duke;

import duke.command.Command;
import duke.command.ExitCommand;
import duke.command.ListPriorityCommand;
import duke.dukeexception.DukeException;
import duke.parser.Parser;
import duke.storage.PriorityStorage;
import duke.storage.Storage;
import duke.task.PriorityList;
import duke.task.TaskList;
import duke.ui.Ui;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents a duke that controls the program.
 */
public class Duke {

    private Storage storage;
    private TaskList items;
    private Ui ui;

    private PriorityStorage priorityStorage;
    private PriorityList priorityList;


    /**
     * Creates a duke to initialize storage, task list, and ui.
     *
     * @param filePath1 The location of the text file.
     * @param filePath2 The location of the priority text file.
     */
    public Duke(String filePath1, String filePath2) {
        ui = new Ui();
        storage = new Storage(filePath1);
        priorityStorage = new PriorityStorage(filePath2);

        try {
            items = new TaskList(storage.read());
        } catch (IOException e) {
            ui.showLoadingError();
            items = new TaskList();
        }

        try {
            priorityList = new PriorityList(priorityStorage.read());
        } catch (IOException e) {
            ui.showLoadingError();
            priorityList = new PriorityList();
        }

    }

    /**
     * Echoes the user input back the the user.
     * (Not in use)
     *
     * @param input The user input.
     * @return String of the response.
     */
    public String getResponse(String input) {
        return "Duke heard: " + input;
    }

    /**
     * Retrieves a command from interpreting the user input (GUI).
     *
     * @param sentence The user input.
     * @return Command to be executed thereafter.
     * @throws Exception  If there is an error reading the command.
     */
    public Command getCommand(String sentence) throws Exception {
        Command cmd = Parser.parse(sentence, items);
        return cmd;
    }

    /**
     * Executes a command to overwrite exiting storage with an updated task list (GUI).
     *
     * @param cmd Command to be executed.
     * @throws IOException  If there is an error writing the text file.
     */
    public void saveState(Command cmd) throws IOException {
        cmd.executeStorage(items,ui,storage);
    }

    /**
     * Executes a command and outputs the result to the user (GUI).
     *
     * @param cmd Command to be executed.
     * @return String to be outputted.
     */
    public String executeCommand(Command cmd) {
        String str = cmd.executeGui(items, ui);
        return str;
    }

    /**
     * Runs the duke program until exit command is executed.
     */
    public void run() {
        ui.showWelcome();
        ui.showReminder(items);
        String sentence;

        while (true) {
            sentence = ui.readCommand();
            try {
                Command cmd = Parser.parse(sentence, items);
                if (cmd instanceof ExitCommand) {
                    priorityStorage.write(priorityList);
                    break;
                } else if (cmd instanceof ListPriorityCommand) {
                    cmd.execute(items, priorityList, ui);
                } else {
                    cmd.execute(items,ui);
                    priorityList = priorityList.addPriority(cmd);
                }
            } catch (DukeException e) {
                ui.showErrorMsg(e.getMessage());
            } catch (Exception e) {
                ui.showErrorMsg("     New error, please fix:");
                e.printStackTrace();
                ui.showErrorMsg("     Duke will continue as per normal.");
            } finally {
                ui.showLine();
            }
        }
    }

    public static void main(String[] args) {
        new Duke("data/duke.txt", "data/priority.txt").run();
    }
}