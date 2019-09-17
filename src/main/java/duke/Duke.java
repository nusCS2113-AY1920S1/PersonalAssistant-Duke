package duke;

import duke.commands.Command;

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

    private Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        taskList = new TaskList(storage.load()); //Will always return the right object even if empty.
        parser = new Parser();
    }

    private void run() {
        ui.printIntro();

        String userInput;
        Command command;

        //Should probably wrap this in the UI class.
        do {
            ui.printNewLine();
            userInput = ui.read();
            command = parser.parse(userInput);
            command.execute(taskList, ui, storage);
        } while (command.getType() != Command.CommandType.BYE);

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
