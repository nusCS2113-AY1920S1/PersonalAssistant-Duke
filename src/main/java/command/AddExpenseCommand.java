package command;


import dukeobject.Expense;
import dukeobject.ExpenseList;
import exception.DukeException;
import parser.CommandParams;
import storage.Storage;
import ui.Ui;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a specified command as AddCommand by extending the {@code Command} class.
 * Adds various specified type of expensesList into the ExpenseList. e.g event
 * Responses with the result.
 */
public class AddExpenseCommand extends Command {
    private static final String name = "add";
    private static final String description = "Adds a new Expense";
    private static final String usage = "add $cost";

    private enum SecondaryParam {
        DESCRIPTION("d", "a short description or name for the expense");

        private String name;
        private String description;

        SecondaryParam(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }

    /**
     * Creates a new command object, with its name, description, usage and secondary parameters.
     */
    public AddExpenseCommand() {
        super(name,
            description,
            usage,
            Stream.of(SecondaryParam.values())
                .collect(Collectors.toMap(s -> s.name, s -> s.description))
        );
    }

    @Override
    public void execute(CommandParams commandParams, ExpenseList expensesList, Ui ui, Storage storage) {
        String expenseString = commandParams.getMainParam();
        if (expenseString == null) {
            throw new DukeException("I need to know how much this costs!");
        }
        String expenseDescription = commandParams.getParam(SecondaryParam.DESCRIPTION.name);
        Expense expense = new Expense.Builder()
            .setAmount(expenseString)
            .setDescription(expenseDescription)
            .build();
        expensesList.add(expense);
        expensesList.update();
    }

}
