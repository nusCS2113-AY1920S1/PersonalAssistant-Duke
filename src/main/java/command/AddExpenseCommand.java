package command;


import dukeobjects.Expense;
import dukeobjects.ExpenseList;
import parser.CommandParams;
import storage.Storage;
import ui.Ui;

/**
 * Represents a specified command as AddCommand by extending the {@code Command} class.
 * Adds various specified type of expensesList into the ExpenseList. e.g event
 * Responses with the result.
 */
public class AddExpenseCommand extends Command {
    /**
     * Creates a new command object, with its name, description, usage and secondary parameters.
     */
    public AddExpenseCommand() {
        super("add", "Adds a new Expense", "add $cost", null);
    }

    @Override
    public void execute(CommandParams commandParams, ExpenseList expensesList, Ui ui, Storage storage) {
        String expenseString = commandParams.getMainParam();
        double expenseAmount = Double.parseDouble(expenseString);
        String expenseDescription = commandParams.getParam("d");
        Expense expense = new Expense.Builder().setAmount(expenseAmount).setDescription(expenseDescription).build();
        expensesList.add(expense);
        expensesList.update();
    }
}
