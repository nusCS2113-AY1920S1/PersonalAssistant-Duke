package duke.logic.command.product;

import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;

public class ListProductCommand extends ProductCommand {

    public enum Scope {
        ALL,
        ACTIVE,
        ARCHIVE
    }

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_LIST_ALL = "All products shown";
    public static final String MESSAGE_LIST_ACTIVE = "All active products shown";
    public static final String MESSAGE_LIST_ARCHIVE = "Archived products shown";
    public static final String MESSAGE_LIST_SCOPE = "%s products shown";

    private Scope scope;

    public ListProductCommand(Scope scope) {
        this.scope = scope;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        switch (scope) {
        case ALL:
            model.updateFilteredProductList(Model.PREDICATE_SHOW_ALL_PRODUCTS);
            break;
        case ACTIVE:
            model.updateFilteredProductList(Model.PREDICATE_SHOW_ACTIVE_PRODUCTS);
            break;
        case ARCHIVE:
            model.updateFilteredProductList(Model.PREDICATE_SHOW_ARCHIVE_PRODUCTS);
            break;
        default:
            break;
        }
        model.getFilteredProductList();
        return new CommandResult(String.format(MESSAGE_LIST_SCOPE, scope), CommandResult.DisplayedPage.PRODUCT);
    }
}
