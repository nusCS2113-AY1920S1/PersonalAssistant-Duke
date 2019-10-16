package duke.command;


import duke.Duke;
import duke.dukeobject.Expense;
import duke.exception.DukeException;

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
        DESCRIPTION("description", "a short description or name for the expense"),
        TAG("tag", "tags that should be added to the expense"),
        TIME("time", "the time of the expense");

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
    public void execute(CommandParams commandParams, Duke duke) throws DukeException {
        Expense.Builder expenseBuilder = new Expense.Builder();

        if (!commandParams.containsMainParam()) {
            throw new DukeException(String.format(DukeException.MESSAGE_COMMAND_PARAM_MISSING, "amount"));
        }
        expenseBuilder.setAmount(commandParams.getMainParam());

        if (commandParams.containsParams(SecondaryParam.DESCRIPTION.name)) {
            expenseBuilder.setDescription(commandParams.getParam(SecondaryParam.DESCRIPTION.name));
        }

        if (commandParams.containsParams(SecondaryParam.TAG.name)) {
            expenseBuilder.invertTags(commandParams.getParam(SecondaryParam.TAG.name));
        }

        if (commandParams.containsParams(SecondaryParam.TIME.name)) {
            expenseBuilder.setDescription(commandParams.getParam(SecondaryParam.TIME.name));
        }

        duke.expenseList.add(expenseBuilder.build());
        duke.expenseList.update();
    }

}
