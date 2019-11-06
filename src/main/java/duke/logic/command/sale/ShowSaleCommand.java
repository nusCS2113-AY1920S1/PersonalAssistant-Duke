package duke.logic.command.sale;

import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;

public class ShowSaleCommand extends SaleCommand {
    public static final String COMMAND_WORD = "";
    private static final String MESSAGE_LIST_SUCCESS = "Showing %d sale(s).";

    public static final String AUTO_COMPLETE_INDICATOR = SaleCommand.COMMAND_WORD + " " + ShowSaleCommand.COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.updateFilteredSaleList(model.PREDICATE_SHOW_ALL_SALES);
        return new CommandResult(String.format(MESSAGE_LIST_SUCCESS, model.getFilteredSaleList().size()),
                CommandResult.DisplayedPage.SALE);
    }
}