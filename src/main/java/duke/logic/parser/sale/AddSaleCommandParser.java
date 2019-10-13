package duke.logic.parser.sale;

import duke.logic.command.sale.AddSaleCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.commons.TimeParser;
import duke.logic.parser.exceptions.ParseException;
import duke.model.sale.Sale;

import static duke.logic.parser.commons.CliSyntax.PREFIX_SALE_DATE;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SALE_DESCRIPTION;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SALE_REMARKS;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SALE_VALUE;

public class AddSaleCommandParser implements Parser<AddSaleCommand> {
    public AddSaleCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args,
                PREFIX_SALE_DESCRIPTION,
                PREFIX_SALE_VALUE,
                PREFIX_SALE_DATE,
                PREFIX_SALE_REMARKS
        );

        Sale sale = new Sale(
                map.getValue(PREFIX_SALE_DESCRIPTION).orElse("N/A"),
                TimeParser.convertStringToDate(map.getValue(PREFIX_SALE_DATE).orElse("now")),
                Double.parseDouble(map.getValue(PREFIX_SALE_VALUE).get()),
                map.getValue(PREFIX_SALE_REMARKS).orElse("N/A")
        );

        return new AddSaleCommand(sale);
    }
}
