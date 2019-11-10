package duke.logic.command;

import duke.exception.DukeException;
import duke.logic.CommandParams;
import duke.logic.CommandResult;
import duke.model.Model;
import duke.storage.Storage;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a specified command as ViewBudgetCommand by extending the {@code Command} class.
 * Adds various specified budget views into the BudgetView.
 * Responses with the result.
 */

public class ViewBudgetCommand extends Command {
    private static final String name = "viewBudget";
    private static final String description = "sets a budgetView";
    private static final String usage = "budget $amount";

    private static final String COMPLETE_MESSAGE = "Set the budget view!";

    private enum SecondaryParam {
        TAG("tag", "tags that we want a budget to be associated with");

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
    public ViewBudgetCommand() {
        super(name, description, usage, Stream.of(ViewBudgetCommand.SecondaryParam.values())
                .collect(Collectors.toMap(s -> s.name, s -> s.description)));
    }

    /**
     * Creates a new Income with the corresponding amount and description and
     * saves it to storage file {@code income.txt}.
     *
     * Responses the result to user by using ui of Duke++ in BudgetPane.
     *
     * @param commandParams the parameters given by the user, parsed into a {@code CommandParams} object.
     * @param model         {@code Model} which the command should operate on.
     * @param storage       the storage of Duke++.
     * @return CommandResult the result of the command, which is a completed logger message, in budget display pane
     * @throws DukeException If the tag or pane of budget view is missing, or the specified pane is invalid
     */
    @Override
    public CommandResult execute(CommandParams commandParams, Model model, Storage storage) throws DukeException {
        if (!commandParams.containsMainParam()) {
            throw new DukeException(String.format(DukeException.MESSAGE_COMMAND_PARAM_MISSING, "pane"));
        }

        if (!commandParams.containsParams(SecondaryParam.TAG.name)) {
            throw new DukeException(String.format(DukeException.MESSAGE_COMMAND_PARAM_MISSING, "tag"));
        }

        int view = Integer.parseInt(commandParams.getMainParam());

        if (view < 1 || view > 6) {
            throw new DukeException(String.format(DukeException.MESSAGE_BUDGET_VIEW_INVALID, view));
        }

        String category = commandParams.getParam(SecondaryParam.TAG.name).toUpperCase();
        model.setBudgetView(view, category);

        storage.saveBudgetView(model.getBudgetView());

        return new CommandResult(COMPLETE_MESSAGE, CommandResult.DisplayedPane.BUDGET);
    }
}