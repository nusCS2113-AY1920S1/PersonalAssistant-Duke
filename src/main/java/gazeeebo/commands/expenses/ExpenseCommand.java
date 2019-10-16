package gazeeebo.commands.expenses;

import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * Allows user to call commands to record and manage their expenses.
 */

public class ExpenseCommand extends Command {

    /**
     * This method is allows user to call commands to add expenses, find expenses on a certain date, delete a chosen expense,
     * see the expense list and exit the expense page.
     *
     * @param list list of all tasks
     * @param ui the object that deals with printing things to the user.
     * @param storage the object that deals with storing data.
     * @throws IOException Catch error if the read file fails
     */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws IOException {
        HashMap<LocalDate, ArrayList<String>> map = storage.Expenses(); //Read the file
        Map<LocalDate, ArrayList<String>> expenses = new TreeMap<LocalDate, ArrayList<String>>(map);
        ArrayList<String> expenseList = new ArrayList<>();

        System.out.print("Welcome to your expenses record! What would you like to do?\n\n");
        System.out.println("__________________________________________________________");
        System.out.println("1. Add expenses command: add");
        System.out.println("2. Find expenses on a certain date: find");
        System.out.println("3. Delete a certain expense: delete");
        System.out.println("4. See your expense list: expense list");
        System.out.println("5. Exit Expense page: esc");
        System.out.println("__________________________________________________________");

        ui.ReadCommand();
        while(!ui.FullCommand.equals("esc")) {
            if (ui.FullCommand.contains("add")) {
                new AddExpensesCommand(ui, storage, expenses);
            } else if(ui.FullCommand.equals("find")) {
                new FindExpenseCommand(ui, expenses);
            } else if(ui.FullCommand.equals("delete")) {
                new DeleteExpenseCommand(ui, storage, expenses);
            } else if(ui.FullCommand.equals("expense list")) {
                new ExpenseListCommand(ui, expenses);
            }
            ui.ReadCommand();
        }
    }
    @Override
    public boolean isExit() {
        return false;
    }
}
