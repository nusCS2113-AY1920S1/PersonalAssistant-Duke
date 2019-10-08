package duke;

import duke.command.CommandTest;
import duke.exception.DukeException;
import duke.parser.Parser;
import duke.storage.Storage;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.util.ArrayList;
import java.text.ParseException;

import static duke.common.Messages.filePath;

/**
 * Duke processes different commands.
 */
public class Duke {

    private Storage storage;
    private TaskList taskList;
    private Ui ui;

//    /**
//     * Constructor for Duke class to instantiation Ui, Storage, TaskList classes.
//     * @param filePath String containing the directory in which the tasks are to be stored
//     */
    public Duke(Ui ui) {
        this.ui = ui;
        storage = new Storage(filePath);
        try {
            taskList = new TaskList(storage.load());
            System.out.println(taskList.getSize());
        } catch (DukeException e) {
            ui.showLoadingError();
        }
    }

    public String showWelcome() {
        return ui.showWelcome();
    }

    public ArrayList<String> runProgram(String fullCommand) throws DukeException, ParseException {
        CommandTest command = Parser.parseTest(fullCommand);
        return command.exe(taskList, ui, storage);
    }

    public void run(String fullCommand) throws DukeException, ParseException {
        CommandTest command = Parser.parseTest(fullCommand);
        command.exec(taskList, ui, storage);
    }
    /**
     * Gets response from Duke.
     *
     * @param input String input from user
     * @return String output
     * @throws DukeException if not able to find any matching items
     */
    public String getResponse(String input) throws DukeException {
        String output = "";
        if (input.contains("list")) {
            output = "Duke heard: " + taskList.listTask();
        } else if (input.contains("find")) {
            output = "Duke heard: " + taskList.findTask(input.trim().split("\\s", 2)[1]);
        } else {
            output = "unknown";
        }
        return output;
    }

    public ArrayList<String> getList() {
        return taskList.listTask();
    }

    public ArrayList<String> findList(String description) throws DukeException {
        return taskList.findTask(description);
    }

    public int getSize() {
        return taskList.getSize();
    }
}



//    /**
//     * Method to start the program.
//     */
//    public void run() {
//        ui.showWelcome();
//        boolean isExit = false;
//        while (!isExit) {
//            try {
//                String fullCommand = ui.readCommand();
//                ui.showLine();
//                Command c = Parser.parse(fullCommand);
//                c.execute(tasks, ui, storage);
//                isExit = c.isExit();
//            } catch (DukeException e) {
//                ui.showError(e.getMessage());
//            } finally {
//                ui.showLine();
//            }
//        }
//    }
//
//    /**
//     * Starting the program.
//     * @param args the command line parameter
//     */
//    public static void main(String[] args) {
//        Ui ui = new Ui();
//        new Duke(ui).runProgram();
//    }