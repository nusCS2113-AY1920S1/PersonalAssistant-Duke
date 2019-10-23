package duke.logic.parser.product;

import duke.commons.core.LogsCenter;
import duke.commons.core.Message;
import duke.commons.core.index.Index;
import duke.logic.command.product.EditProductCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.commons.ParserUtil;
import duke.logic.parser.exceptions.ParseException;

import java.util.logging.Level;
import java.util.logging.Logger;

import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_INDEX;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_INGREDIENT;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_INGREDIENT_COST;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_NAME;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_RETAIL_PRICE;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_STATUS;
import static duke.logic.parser.product.ProductParserUtil.createProductDescriptor;


public class EditProductCommandParser implements Parser<EditProductCommand> {

    private static final Logger logger = LogsCenter.getLogger(EditProductCommandParser.class);

    @Override
    public EditProductCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args,
                PREFIX_PRODUCT_INDEX,
                PREFIX_PRODUCT_NAME,
                PREFIX_PRODUCT_INGREDIENT,
                PREFIX_PRODUCT_RETAIL_PRICE,
                PREFIX_PRODUCT_INGREDIENT_COST,
                PREFIX_PRODUCT_STATUS
        );

        Index index;

        try {
            index = ParserUtil.parseIndex(map.getValue(PREFIX_PRODUCT_INDEX).orElse(""));
        } catch (ParseException pe) {
            logger.log(Level.WARNING, "Parse Index error");
            throw new ParseException(Message.MESSAGE_INVALID_COMMAND_FORMAT);
        }

        return new EditProductCommand(index, createProductDescriptor(map));
    }
}
