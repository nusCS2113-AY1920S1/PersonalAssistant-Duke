package duke.command;

import duke.Duke;
import duke.exception.DukeException;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a specified command as DeleteCommand by extending the {@code Command} class.
 * Deletes the task with given index or specific command from the ExpenseList of Duke.
 * Responses with the result.
 */
public class DeleteCommand extends Command {
    private static final String name = "delete";
    private static final String description = "Deletes an Expense";
    private static final String usage = "delete $index";

    private enum SecondaryParam {
        ;

        private String name;
        private String description;

        SecondaryParam(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }

    /**
     * Constructs a {@code DeleteCommand} object
     * given the index of the task to be deleted.
     */
    public DeleteCommand() {
        super(name, description, usage, Stream.of(SecondaryParam.values())
            .collect(Collectors.toMap(s -> s.name, s -> s.description)));
    }

    /**
     * Lets the ExpenseList of Duke delete the task with the given index(s), or the entire task list and
     * updates content of storage file according to new ExpenseList.
     * Responses the result to user by using ui of Duke.
     *
     * @param duke The Duke object.
     * @throws DukeException If the index given is out of range, invalid, or does not exist.
     */
    @Override
    public void execute(CommandParams commandParams, Duke duke) throws DukeException {
        if (!commandParams.containsMainParam()) {
            throw new DukeException(String.format(DukeException.MESSAGE_COMMAND_PARAM_MISSING, "index"));
        }

        if (commandParams.getMainParam().equals("all")) {
            duke.expenseList.clear();
        } else if (commandParams.getMainParam().contains("-")) {
            String[] index = commandParams.getMainParam().split("-");
            int difference = Integer.parseInt(index[1]) - Integer.parseInt(index[0]);
            int counter = 0;
            for (int i = Integer.parseInt(index[0]); counter <= difference; counter++) {
                duke.expenseList.remove(i);
            }
        } else {
            duke.expenseList.remove(Integer.parseInt(commandParams.getMainParam()));
        }
        duke.expenseList.update();
    }
}
