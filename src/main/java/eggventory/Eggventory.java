package eggventory;

import eggventory.commands.Command;
import eggventory.enums.CommandType;
import eggventory.parsers.Parser;

/**
 * Eggventory is a task list that supports 3 types of classes - Todos, deadlines and events.
 * Tasks can be added, marked as done, searched for, and deleted.
 * Tasks are loaded from and saved to file.
 */

public class Eggventory implements Runnable {
    private static Storage storage;
    private static Parser parser;
    private static StockList stockList;
    private static Ui ui;

    /**
     * Eggventory does somethings.
     * @param filePath which stores path of persistent storage
     */
    public Eggventory(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        stockList = storage.load(); //Will always return the right object even if empty.
        parser = new Parser();
    }

    @Override
    public void run() {
        ui.printIntro();
        String userInput;

        while (true) {
            userInput = ui.read();

            try {
                Command command = parser.parse(userInput);
                command.execute(stockList, ui, storage);

                if (command.getType() == CommandType.BYE) {
                    break;
                }
            } catch (Exception e) {
                ui.printError(e);
            }
        }
    }
}
