package duke;

import duke.commands.Command;
import duke.exceptions.BadInputException;
import duke.enums.CommandType;

/**
 * Duke is a task list that supports 3 types of classes - Todos, deadlines and events.
 * Tasks can be added, marked as done, searched for, and deleted.
 * Tasks are loaded from and saved to file.
 */

public class Duke {
    private static Storage storage;
    private static Parser parser;
    private static TaskList taskList;
    private static Ui ui;

    /**
     * Duke does somethings.
     * @param filePath which stores path of persistent storage
     */
    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        taskList = storage.load(); //Will always return the right object even if empty.
        parser = new Parser();
    }

    private void run() {
        ui.printIntro();
        String userInput;
        Command command = null;

        //Should probably wrap this in the UI class.
        while (true) {
            ui.printNewLine();
            userInput = ui.read();

            try {
                command = parser.parse(userInput);
                command.execute(taskList, ui, storage);

                if (command.getType() == CommandType.BYE)
                    break;

            } catch (Exception e) {
                ui.printError(e);
            }
        }
    }

    /**
     * Main function that sets the save path and runs duke.
     */
    public static void main(String[] args) {

        String currentDir = System.getProperty("user.dir");
        String filePath = currentDir + "/data/saved_tasks.txt";
        new Duke(filePath).run();
    }
}
