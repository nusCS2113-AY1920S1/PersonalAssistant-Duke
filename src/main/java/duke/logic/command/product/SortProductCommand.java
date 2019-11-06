package duke.logic.command.product;

import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.command.product.ListProductCommand.Scope;
import duke.logic.parser.commons.CliSyntax;
import duke.logic.parser.commons.Prefix;
import duke.model.Model;

import static duke.logic.message.ProductMessageUtils.MESSAGE_SORT_PRODUCT_SUCCESS;

public class SortProductCommand extends ProductCommand {

    public static final String COMMAND_WORD = "sort";
    public static final String AUTO_COMPLETE_INDICATOR = ProductCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final Prefix[] AUTO_COMPLETE_PARAMETERS = {
        CliSyntax.PREFIX_PRODUCT_SORT,
        CliSyntax.PREFIX_PRODUCT_SCOPE,
        CliSyntax.PREFIX_PRODUCT_SORT_REVERSE
    };
    private Category category;
    private Scope scope;
    private boolean isIncrease;

    /**
     * Constructs a {@code SortProductCommand}.
     *
     * @param category   Sorting category
     * @param scope      Scope of the product to sort
     * @param isIncrease true if products are sorted in increasing order
     */
    public SortProductCommand(Category category, Scope scope, boolean isIncrease) {
        this.category = category;
        this.scope = scope;
        this.isIncrease = isIncrease;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        new ListProductCommand(scope).execute(model);
        model.sortProducts(category, isIncrease);

        return new CommandResult(String.format(MESSAGE_SORT_PRODUCT_SUCCESS, category),
            CommandResult.DisplayedPage.PRODUCT);
    }

    public enum Category {
        NAME,
        COST,
        PRICE,
        PROFIT
    }
}
