package eggventory;

import eggventory.commands.Command;
import eggventory.enums.CommandType;
import eggventory.parsers.Parser;

/**
 * Eggventory is a task list that supports 3 types of classes - Todos, deadlines and events.
 * Tasks can be added, marked as done, searched for, and deleted.
 * Tasks are loaded from and saved to file.
 */

public class Eggventory {
    private static Storage storage;
    private static Parser parser;
    private static StockType stockType;
    private static Ui ui;

    /**
     * Eggventory does somethings.
     * @param filePath which stores path of persistent storage
     */
    public Eggventory(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        stockType = storage.load(); //Will always return the right object even if empty.
        parser = new Parser();
    }

    private void run() {
        ui.printIntro();
        String userInput;

        while (true) {
            userInput = ui.read();

            try {
                Command command = parser.parse(userInput);
                command.execute(stockType, ui, storage);

                if (command.getType() == CommandType.BYE) {
                    break;
                }
            } catch (Exception e) {
                ui.printError(e);
            }
        }
    }

    /**
     * Main function that sets the save path and runs eggventory.
     */
    public static void main(String[] args) {
        String currentDir = System.getProperty("user.dir");
        String filePath = currentDir + "/data/saved_tasks.txt";
        new Eggventory(filePath).run();
    }
}
