package duke.logic.command;

import duke.exception.DukeException;
import duke.logic.CommandParams;
import duke.logic.CommandResult;
import duke.model.Model;
import duke.storage.Storage;

import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public ViewBudgetCommand() {
        super(name, description, usage, Stream.of(ViewBudgetCommand.SecondaryParam.values())
                .collect(Collectors.toMap(s -> s.name, s -> s.description)));
    }

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