package duke.logic.command.sale;

import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;

public class ShowSaleCommand extends SaleCommand {
    public static final String COMMAND_WORD = "";
    private static final String MESSAGE_LIST_SUCCESS = "Showing all sales.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(String.format(MESSAGE_LIST_SUCCESS), CommandResult.DisplayedPage.SALE);
    }
}