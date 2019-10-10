package duke.logic.parser.sale;

import duke.logic.command.sale.AddSaleCommand;
import duke.logic.parser.commons.*;
import duke.logic.parser.exceptions.ParseException;
import duke.model.order.Customer;
import duke.model.sale.Sale;

import static duke.logic.parser.commons.CliSyntax.*;

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
                Double.parseDouble(map.getValue(PREFIX_SALE_VALUE).get()),
                TimeParser.convertStringToDate(map.getValue(PREFIX_SALE_DATE).orElse("now")),
                map.getValue(PREFIX_SALE_REMARKS).orElse("N/A")
        );

        return new AddSaleCommand(sale);
    }
}
