package duke.logic.parser.sale;

import duke.logic.command.sale.AddSaleCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;

import static duke.logic.parser.commons.CliSyntax.PREFIX_SALE_DESCRIPTION;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SALE_VALUE;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SALE_IS_SPEND;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SALE_DATE;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SALE_REMARKS;
import static duke.logic.parser.sale.SaleParserUtil.createDescriptor;

/**
 * A parser that parses {@code AddSaleCommand}.
 */
public class AddSaleCommandParser implements Parser<AddSaleCommand> {
    @Override
    public AddSaleCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(
            args,
            PREFIX_SALE_DESCRIPTION,
            PREFIX_SALE_VALUE,
            PREFIX_SALE_IS_SPEND,
            PREFIX_SALE_DATE,
            PREFIX_SALE_REMARKS
        );

        return new AddSaleCommand(createDescriptor(map));
    }
}