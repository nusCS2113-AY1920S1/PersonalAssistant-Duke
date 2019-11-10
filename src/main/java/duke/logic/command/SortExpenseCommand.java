package duke.logic.command;

import duke.exception.DukeException;
import duke.logic.CommandParams;
import duke.logic.CommandResult;
import duke.model.Model;
import duke.storage.Storage;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Sets the sorting criteria of visible expenses in expense tracker.
 * Sorting criteria include amount, time and description.
 * The default sorting criteria is time.
 */
public class SortExpenseCommand extends Command {
    private static final String name = "sortExpense";
    private static final String description = "Sort expenses according to a given criteria";
    private static final String usage = "sort $criteria";

    private static final String COMPLETE_MESSAGE = "Sorted the expense!";

    /**
     * Contains all secondary parameters used by {@code SortExpenseCommand}.
     * Here the {@code SortExpenseCommand} does not demand secondary parameters.
     */
    private enum SecondaryParam {
        ;

        private String name;
        private String description;

        /**
         * Constructs a {@code SecondaryParam} with its name and usage.
         *
         * @param name        The name of the secondary parameter.
         * @param description The usage of this parameter.
         */
        SecondaryParam(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }

    /**
     * Creates a SortExpenseCommand, with its name, description, usage and secondary parameters.
     */
    public SortExpenseCommand() {
        super(name, description, usage, Stream.of(SecondaryParam.values())
            .collect(Collectors.toMap(s -> s.name, s -> s.description)));
    }

    @Override
    public CommandResult execute(CommandParams commandParams, Model model, Storage storage) throws DukeException {
        String mainParam = commandParams.getMainParam();
        model.sortExpense(mainParam);

        return new CommandResult(COMPLETE_MESSAGE, CommandResult.DisplayedPane.EXPENSE);
    }
}
