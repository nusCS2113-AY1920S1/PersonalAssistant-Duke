package duke.logic.command;

import duke.exception.DukeException;
import duke.logic.CommandParams;
import duke.logic.CommandResult;
import duke.model.Model;
import duke.storage.Storage;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Sets the time scope of visible expenses in expense tracker.
 * Scopes include day, week, month, year and all.
 *
 * By default, time scope day will only show expense on current day.
 * With /previous parameters specified as n, day will show expenses n days ago.
 * Same usage is also applicable to week, month and year.
 */
public class ViewExpenseCommand extends Command {
    private static final String name = "viewExpense";
    private static final String description = "Change how expenses are displayed";
    private static final String usage = "view $criteria";

    private static final String COMPLETE_MESSAGE = "Changed view scope of expenses!";
    private static final String EXCEPTION_WORD_TIME_SCOPE = "time scope";
    private static final String PARAM_PREVIOUS_NAME = "previous";
    private static final String PARAM_PREVIOUS_REDUNDANT = "/previous";
    private static final String PARAM_PREVIOUS_ALL = "all";
    private static final int DEFAULT_PREVIOUS_VALUE = 0;

    /**
     * Contains all secondary parameters used by {@code ViewExpenseCommand}.
     */
    private enum SecondaryParam {
        PREVIOUS("previous", "the number of pages to move back by");

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
     * Creates a ViewExpenseCommand, with its name, description, usage and secondary parameters.
     */
    public ViewExpenseCommand() {
        super(name, description, usage, Stream.of(SecondaryParam.values())
            .collect(Collectors.toMap(s -> s.name, s -> s.description)));
    }

    @Override
    public CommandResult execute(CommandParams commandParams, Model model, Storage storage) throws DukeException {
        if (!commandParams.containsMainParam()) {
            throw new DukeException(String.format
                    (DukeException.MESSAGE_COMMAND_PARAM_MISSING, EXCEPTION_WORD_TIME_SCOPE));
        }

        String mainParam = commandParams.getMainParam();

        if (!commandParams.containsParams(PARAM_PREVIOUS_NAME)) {
            model.viewExpense(mainParam, DEFAULT_PREVIOUS_VALUE);

            return new CommandResult(COMPLETE_MESSAGE, CommandResult.DisplayedPane.EXPENSE);
        }

        if (commandParams.getMainParam().toLowerCase().equals(PARAM_PREVIOUS_ALL)) {
            throw new DukeException(String.format
                    (DukeException.MESSAGE_COMMAND_PARAM_REDUNDANT, PARAM_PREVIOUS_REDUNDANT));
        }

        int previous;
        try {
            previous = Integer.parseInt(commandParams.getParam(PARAM_PREVIOUS_NAME));
        } catch (NumberFormatException e) {
            throw new DukeException(String.format(
                DukeException.MESSAGE_EXPENSE_VIEW_NUMBER_INVALID,
                commandParams.getParam(PARAM_PREVIOUS_NAME)));
        }

        model.viewExpense(mainParam, previous);

        return new CommandResult(COMPLETE_MESSAGE, CommandResult.DisplayedPane.EXPENSE);
    }
}
