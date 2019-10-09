package duke.logic.command.sale;

import duke.logic.command.commons.CommandResult;
import duke.logic.command.commons.Undoable;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;
import duke.model.sale.Sale;

import static java.util.Objects.requireNonNull;

/**
 * A command to add a sale to BakingHome.
 */
public class AddSaleCommand extends SaleCommand implements Undoable {

    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_SUCCESS = "New sale added [Sale ID: %s]";
    private final Sale toAdd;

    /**
     * Creates an AddSaleCommand to add the specified {@code Sale}.
     *
     * @param toAdd the {@code Sale} to be added
     */
    public AddSaleCommand(Sale toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    public CommandResult execute(Model model) throws CommandException {
        model.addSale(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getId()), CommandResult.DisplayedPage.ORDER);
    }

    @Override
    public void undo(Model model) throws CommandException {
    }

    @Override
    public void redo(Model model) throws CommandException {
    }
}
