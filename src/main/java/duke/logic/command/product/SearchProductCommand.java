package duke.logic.command.product;

import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.message.ProductMessageUtils;
import duke.logic.parser.commons.CliSyntax;
import duke.logic.parser.commons.Prefix;
import duke.model.Model;

public class SearchProductCommand extends ProductCommand {


    public static final String COMMAND_WORD = "search";
    private final String keyword;

    public static final String AUTO_COMPLETE_INDICATOR = ProductCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final Prefix[] AUTO_COMPLETE_PARAMETERS = {
            CliSyntax.PREFIX_PRODUCT_SEARCH
    };

    public SearchProductCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.getProductWithKeyword(keyword);
        return new CommandResult(String.format(ProductMessageUtils.MESSAGE_SEARCH_PRODUCT_SHOWN, keyword),
                CommandResult.DisplayedPage.PRODUCT);
    }
}
