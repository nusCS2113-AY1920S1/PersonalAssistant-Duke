package duke.logic.parser.sale;

import duke.logic.command.sale.FilterSaleCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;

import static duke.logic.parser.sale.SaleParserUtil.createFilterDate;

import static duke.logic.parser.commons.CliSyntax.PREFIX_SALE_FROM;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SALE_TO;

public class FilterSaleCommandParser implements Parser<FilterSaleCommand> {
    @Override
    public FilterSaleCommand parse (String args) throws ParseException  {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args,
                PREFIX_SALE_FROM,
                PREFIX_SALE_TO
        );

        return new FilterSaleCommand(createFilterDate(map));
    }
}