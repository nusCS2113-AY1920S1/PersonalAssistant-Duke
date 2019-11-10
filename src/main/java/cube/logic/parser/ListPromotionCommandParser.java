package cube.logic.parser;

import cube.logic.command.ListPromotionCommand;
import cube.logic.parser.exception.ParserException;

public class ListPromotionCommandParser implements ParserPrototype<ListPromotionCommand> {

    public ListPromotionCommand parse(String[] args) throws ParserException {
        return new ListPromotionCommand();
    }
}
