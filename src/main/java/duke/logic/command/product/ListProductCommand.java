package duke.logic.command.product;

import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.message.ProductMessageUtils;
import duke.logic.parser.commons.CliSyntax;
import duke.logic.parser.commons.Prefix;
import duke.model.Model;

public class ListProductCommand extends ProductCommand {

    public enum Scope {
        ALL,
        ACTIVE,
        ARCHIVE
    }

    public static final String COMMAND_WORD = "list";

    private Scope scope;
    public static final String AUTO_COMPLETE_INDICATOR = ProductCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final Prefix[] AUTO_COMPLETE_PARAMETERS = {
        CliSyntax.PREFIX_PRODUCT_SCOPE
    };

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
        //model.getFilteredProductList();
        return new CommandResult(String.format(ProductMessageUtils.MESSAGE_LIST_SCOPE, scope),
            CommandResult.DisplayedPage.PRODUCT);
    }
}
