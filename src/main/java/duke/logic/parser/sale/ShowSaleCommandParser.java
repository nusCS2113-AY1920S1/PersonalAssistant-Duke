package duke.logic.parser.sale;

<<<<<<< HEAD
import duke.logic.command.sale.ShowSaleCommand;
=======
import duke.commons.core.Message;
import duke.logic.command.sale.ShowSaleCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
>>>>>>> 3a39a7b24bf58f7fb4e367e31649b0b5511c1186
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;

public class ShowSaleCommandParser implements Parser<ShowSaleCommand> {
    @Override
    public ShowSaleCommand parse(String args) throws ParseException {
<<<<<<< HEAD
        System.out.println(args);
=======
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args);

        if (!map.getPreamble().isBlank()) {
            throw new ParseException(Message.MESSAGE_UNKNOWN_COMMAND);
        }

>>>>>>> 3a39a7b24bf58f7fb4e367e31649b0b5511c1186
        return new ShowSaleCommand();
    }
}
