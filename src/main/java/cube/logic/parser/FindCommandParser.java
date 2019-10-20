package cube.logic.parser;

import cube.logic.command.FindCommand;
import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;
import cube.model.FoodList;

import java.util.Arrays;

public class FindCommandParser implements ParserPrototype<FindCommand> {

    public FindCommand parse(String[] args) throws ParserException {
        if (args.length < 3) {
            throw new ParserException(ParserErrorMessage.NOT_ENOUGH_PARAMETER);
        }

        switch (args[1]) {
            case "-i":
                return new FindCommand(Integer.parseInt(args[2]),"INDEX");
            case "-n":
                return new FindCommand(new ParserUtil().findFullString(args,2),"NAME");
            case "-t":
                String type  = new ParserUtil().findFullString(args,2);
                int sortIndex = -1;
                for (int i = 1; i < args.length; i ++) {
                    if (args[i].equals("-sort")) {
                        sortIndex = i;
                    }
                }
                if (sortIndex!=-1) {
                    return new FindCommand(type,"TYPE", FoodList.SortType.valueOf(args[sortIndex+1].toUpperCase()));
                }
                return new FindCommand(type,"TYPE");
        }
        return null;
    }
}
