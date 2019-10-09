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

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static duke.logic.parser.commons.CliSyntax.*;

public class EditProductCommandParser implements Parser<EditProductCommand> {

    private static final Logger logger = LogsCenter.getLogger(EditProductCommandParser.class);

    @Override
    public EditProductCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args,
                PREFIX_PRODUCT_INDEX,
                PREFIX_PRODUCT_NAME,
                PREFIX_PRODUCT_INGREDIENT,
                PREFIX_PRODUCT_PRICE,
                PREFIX_PRODUCT_COST
        );

        Index index;

        try {
            index = ParserUtil.parseIndex(map.getValue(PREFIX_PRODUCT_INDEX).orElse(""));
        } catch (ParseException pe) {
            logger.log(Level.WARNING, "Parse Index error in EditProductCommandParse");
            throw new ParseException(Message.MESSAGE_INVALID_COMMAND_FORMAT);
        }

        EditProductCommand.EditProductDescriptor editProductDescriptor =
                new EditProductCommand.EditProductDescriptor();
        if (map.getValue(PREFIX_PRODUCT_NAME).isPresent()) {
            editProductDescriptor.setProductName(map.getValue(PREFIX_PRODUCT_NAME).get());
        }
        if (map.getValue(PREFIX_PRODUCT_PRICE).isPresent()) {
            editProductDescriptor.setRetailPrice(Double.parseDouble(map.getValue(PREFIX_PRODUCT_PRICE).get()));
        }
        if (map.getValue(PREFIX_PRODUCT_COST).isPresent()) {
            editProductDescriptor.setIngredientCost(Double.parseDouble(
                    map.getValue(PREFIX_PRODUCT_COST).get()));
        }
        if (map.getValue(PREFIX_PRODUCT_STATUS).isPresent()) {
            editProductDescriptor.setStatus(ParserUtil.parseProductStatus(map.getValue(PREFIX_PRODUCT_STATUS).get()));
        }
        return new EditProductCommand(index, editProductDescriptor);
    }
}
