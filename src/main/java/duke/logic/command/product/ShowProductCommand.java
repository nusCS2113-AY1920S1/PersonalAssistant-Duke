package duke.logic.command.product;

import duke.commons.core.Message;
import duke.commons.core.index.Index;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.parser.commons.CliSyntax;
import duke.logic.parser.commons.Prefix;
import duke.model.Model;
import duke.model.product.Product;

import java.util.List;

import static duke.commons.util.CollectionUtil.requireAllNonNull;
import static duke.logic.message.ProductMessageUtils.MESSAGE_SHOW_PRODUCT_SUCCESS;

public class ShowProductCommand extends ProductCommand {

    public static final String COMMAND_WORD = "show";

    public final Index index;

    public static final String AUTO_COMPLETE_INDICATOR = ProductCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final Prefix[] AUTO_COMPLETE_PARAMETERS = {
        CliSyntax.PREFIX_PRODUCT_INDEX,
    };

    public ShowProductCommand(Index index) {
        requireAllNonNull(index);

        this.index = index;
    }

    @Override
    public ShowProductCommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);
        List<Product> lastShownList = model.getFilteredProductList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Message.MESSAGE_INVALID_INDEX);
        }

        Product toShow = lastShownList.get(index.getZeroBased());
        return new ShowProductCommandResult(String.format(MESSAGE_SHOW_PRODUCT_SUCCESS, toShow.getProductName()),
                CommandResult.DisplayedPage.PRODUCT, index);
    }
}
