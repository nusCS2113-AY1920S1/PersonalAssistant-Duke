package gazeeebo.parsers;


import gazeeebo.commands.expenses.AddExpenseCommand;
import gazeeebo.commands.expenses.DeleteExpenseCommand;
import gazeeebo.commands.expenses.ExpenseListCommand;
import gazeeebo.commands.expenses.FindExpenseCommand;
import gazeeebo.commands.expenses.UndoExpenseCommand;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.Map;
import java.util.TreeMap;

/**
 * Allows user to call commands to record and manage their expenses.
 */
public class ExpenseCommandParser extends Command {

    /**
     * This method is allows user to call commands to add expenses,
     * find expenses on a certain date, delete a chosen expense,
     * see the expense list and exit the expense page.
     *
     * @param list          list of all tasks
     * @param ui            the object that deals with
     *                      printing things to the user
     * @param storage       the object that deals with storing data
     * @param commandStack
     * @param deletedTask
     * @param triviaManager
     * @throws IOException    Catch error if the read file fails
     * @throws ParseException Catch error if parsing of command fails
     */
    @Override
    public void execute(final ArrayList<Task> list,
                        final Ui ui, final Storage storage,
                        final Stack<ArrayList<Task>> commandStack,
                        final ArrayList<Task> deletedTask,
                        final TriviaManager triviaManager)
            throws IOException, ParseException {
        /*Read file from storage*/
        HashMap<LocalDate, ArrayList<String>> map = storage.readFromExpensesFile();
        Map<LocalDate, ArrayList<String>> expenses =
                new TreeMap<LocalDate, ArrayList<String>>(map);
        Stack<Map<LocalDate, ArrayList<String>>> oldExpenses = new Stack<>();
        boolean isExitExpenses = false;

        System.out.print("Welcome to your expenses record!"
                + " What would you like to do?\n\n");
        System.out.println("_________________________"
                + "_________________________________");
        System.out.println("1. Add expenses command: add");
        System.out.println("2. Find expenses on a certain date: "
                + "find yyyy-MM-dd");
        System.out.println("3. Delete a certain expense: delete");
        System.out.println("4. See your expense list: list");
        System.out.println("5. Exit Expense page: esc");

        System.out.println("_________________________"
                + "_________________________________");


        while (!isExitExpenses) {
            ui.readCommand();
            if (ui.fullCommand.equals("add")) {
                copyMap(expenses, oldExpenses);
                new AddExpenseCommand(ui, expenses);
            } else if (ui.fullCommand.split(" ")[0].equals("find")) {
                new FindExpenseCommand(ui, expenses);
            } else if (ui.fullCommand.equals("delete")) {
                copyMap(expenses, oldExpenses);
                new DeleteExpenseCommand(ui, expenses);
            } else if (ui.fullCommand.equals("list")) {
                new ExpenseListCommand(expenses);
            } else if (ui.fullCommand.equals("undo")) {
                expenses = UndoExpenseCommand.undoExpenses(expenses,
                        oldExpenses, storage);
            } else if (ui.fullCommand.equals("esc")) {
                isExitExpenses = true;
                System.out.println("Going back to Main Menu...\n"
                        + "Content Page:\n"
                        + "------------------ \n"
                        + "1. help\n"
                        + "2. contacts\n"
                        + "3. expenses\n"
                        + "4. places\n"
                        + "5. tasks\n"
                        + "6. cap\n"
                        + "7. spec\n"
                        + "8. moduleplanner\n"
                        + "9. notes\n"
                        + "To exit: bye\n"
                );
            } else {
                System.out.println("Command not found, please re-enter!");
            }
            String toStore = "";
            for (LocalDate key : expenses.keySet()) {
                if (expenses.get(key).size() > 1) {
                    for (int i = 0; i < expenses.get(key).size(); i++) {
                        toStore = toStore.concat(key + "|"
                                + expenses.get(key).get(i)
                                + "\n");
                    }
                } else if (expenses.get(key).size() == 1) {

                    toStore = toStore.concat(key + "|"
                            + expenses.get(key).get(0)
                            + "\n");
                }
            }
            storage.writeToExpensesFile(toStore);
        }
    }

    /**
     * copy map of places into a stack of maps.
     *
     * @param expenses    map of current expenses
     * @param oldExpenses stack of map of previous expenses
     */
    private void copyMap(final Map<LocalDate,
            ArrayList<String>> expenses,
                         final Stack<Map<LocalDate,
                                 ArrayList<String>>> oldExpenses) {

        Map<LocalDate, ArrayList<String>> currentExpenses = new TreeMap<>();
        for (LocalDate key : expenses.keySet()) {
            ArrayList<String> listNameExpenses = new ArrayList<>();
            for (int i = 0; i < expenses.get(key).size(); i++) {
                listNameExpenses.add(expenses.get(key).get(i));
            }
            currentExpenses.put(key, listNameExpenses);
        }
        oldExpenses.push(currentExpenses);
    }

    /**
     * Program does not exit and continues running
     * since command "bye" is not called.
     *
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
