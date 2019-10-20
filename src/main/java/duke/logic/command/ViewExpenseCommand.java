package duke.logic.command;

import duke.exception.DukeException;
import duke.logic.CommandParams;
import duke.logic.CommandResult;
import duke.model.Model;
import duke.storage.Storage;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ViewExpenseCommand extends Command {
    private static final String name = "viewExpense";
    private static final String description = "Change how expenses are displayed";
    private static final String usage = "view $criteria";

    private static final String COMPLETE_MESSAGE = "Changed view scope of expenses!";

    private enum SecondaryParam {
        PREVIOUS("previous", "the number of pages to move back by");

        private String name;
        private String description;

        SecondaryParam(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }

    /**
     * Constructs an {@code ViewCommand} object.
     */
    public ViewExpenseCommand() {
        super(name, description, usage, Stream.of(SecondaryParam.values())
            .collect(Collectors.toMap(s -> s.name, s -> s.description)));
    }

    @Override
    public CommandResult execute(CommandParams commandParams, Model model, Storage storage) throws DukeException {
        String mainParam = commandParams.getMainParam();
        if (!commandParams.containsParams("previous")) {
            model.viewExpense(mainParam, 0);
            return new CommandResult(COMPLETE_MESSAGE, CommandResult.DisplayedPane.EXPENSE);
        }

        int previous;
        try {
            previous = Integer.parseInt(commandParams.getParam("previous"));
        } catch (NumberFormatException e) {
            throw new DukeException(String.format(
                DukeException.MESSAGE_EXPENSE_VIEW_SCOPE_NUMBER_INVALID,
                commandParams.getParam("previous")));
        }
        model.viewExpense(mainParam, previous);

        return new CommandResult(COMPLETE_MESSAGE, CommandResult.DisplayedPane.EXPENSE);
    }

}
