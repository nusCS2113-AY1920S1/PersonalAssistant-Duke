import moomoo.command.Command;
import moomoo.task.Budget;
import moomoo.task.CategoryList;
import moomoo.task.Storage;
import moomoo.task.TransactionList;
import moomoo.task.Ui;
import moomoo.task.MooMooException;
import moomoo.task.Parser;

/**
 * Runs MooMoo.
 */
public class MooMoo {
    private Storage storage;
    private TransactionList transList;
    private CategoryList categoryList;
    private Budget budget;
    private Ui ui;

    /**
     * Initializes different Category, Transaction Lists, Budget, Storage and Ui.
     */
    MooMoo() {
        ui = new Ui();
        storage = new Storage("data/budget.txt","data/transactions.txt","data/category.txt");

        try {
            categoryList = new CategoryList(storage.loadCategories());
        } catch (MooMooException e) {
            ui.printException(e);
            ui.showResponse();
            categoryList = new CategoryList();
        }

        try {
            transList = new TransactionList(storage.loadTransactions());
        } catch (MooMooException e) {
            ui.printException(e);
            ui.showResponse();
            transList = new TransactionList();
        }

        try {
            budget = new Budget(storage.loadBudget(categoryList));
        } catch (MooMooException e) {
            ui.printException(e);
            ui.showResponse();
            budget = new Budget();
        }

    }

    /**
     * Runs the command line interface, reads input from user and returns result accordingly.
     */
    private void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command c = Parser.parse(fullCommand, ui);
                c.execute(budget, categoryList, transList, ui, storage);
                if (ui.printResponse() != null) {
                    ui.showResponse();
                }
                isExit = c.isExit;
            } catch (MooMooException e) {
                ui.printException(e);
                ui.showResponse();
            }
        }
    }

    /**
     * Returns the response to the GUI when given an input by a user.
     * @param input Input given by user in the GUI
     * @return String Response to display on GUI by the bot.
     */
    String getResponse(String input) {
        String response;
        boolean isExit = false;
        try {
            Command c = Parser.parse(input, ui);
            c.execute(budget, categoryList, transList, ui, storage);
            isExit = c.isExit;
            if (isExit) {
                ui.showGoodbye();
                return ui.printResponse();
            }
            return ui.printResponse();
        } catch (MooMooException e) {
            return ui.printException(e);
        }
    }

    /**
     * Runs MooMoo.
     * @param args Argument values given when running the program
     */
    public static void main(String[] args) {
        new MooMoo().run();
    }
}
