package eggventory;

import eggventory.commands.Command;
import eggventory.enums.CommandType;
import eggventory.parsers.Parser;
import eggventory.ui.Cli;

/**
 * Eggventory is a task list that supports 3 types of classes - Todos, deadlines and events.
 * Tasks can be added, marked as done, searched for, and deleted.
 * Tasks are loaded from and saved to file.
 */

public class Eggventory implements Runnable {
    private static Storage storage;
    private static Parser parser;
    private static StockList stockList;
    private static Cli cli;

    /**
     * Eggventory does somethings.
     * @param filePath which stores path of persistent storage
     */
    public Eggventory(String filePath) {
        cli = new Cli();
        storage = new Storage(filePath);
        stockList = storage.load(); //Will always return the right object even if empty.
        parser = new Parser();
    }

    @Override
    public void run() {
        cli.printIntro();
        String userInput;

        while (true) {
            userInput = cli.read();

            try {
                Command command = parser.parse(userInput);
                command.execute(stockList, cli, storage);

                if (command.getType() == CommandType.BYE) {
                    break;
                }
            } catch (Exception e) {
                cli.printError(e);
            }
        }
    }
}
