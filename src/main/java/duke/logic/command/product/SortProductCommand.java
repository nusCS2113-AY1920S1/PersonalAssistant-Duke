package duke.logic.command.product;

import duke.logic.command.product.ListProductCommand.Scope;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.parser.commons.CliSyntax;
import duke.logic.parser.commons.Prefix;
import duke.model.Model;

import static duke.logic.message.ProductMessageUtils.MESSAGE_SORT_PRODUCT_SUCCESS;

public class SortProductCommand extends ProductCommand {

    public enum Category {
        NAME,
        COST,
        PRICE,
        PROFIT
    }

    public static final String COMMAND_WORD = "sort";

    private Category category;
    private Scope scope;
    private boolean isReversed;

    public static final String AUTO_COMPLETE_INDICATOR = ProductCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final Prefix[] AUTO_COMPLETE_PARAMETERS = {
            CliSyntax.PREFIX_PRODUCT_SORT,
            CliSyntax.PREFIX_PRODUCT_SCOPE,
            CliSyntax.PREFIX_PRODUCT_SORT_REVERSE
    };

    /**
     * Constructs a {@code SortProductCommand}.
     * @param category Sorting category
     * @param scope Scope of the product to sort
     * @param isReversed true if products are sorted in increasing order
     */
    public SortProductCommand(Category category, Scope scope, boolean isReversed) {
        this.category = category;
        this.scope = scope;
        this.isReversed = isReversed;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        new ListProductCommand(scope).execute(model);
        model.sortProducts(category, isReversed);

        return new CommandResult(String.format(MESSAGE_SORT_PRODUCT_SUCCESS, category),
                CommandResult.DisplayedPage.PRODUCT);
    }
}
