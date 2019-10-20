package cube.logic.parser;

import cube.logic.command.FindCommand;
import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;

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
                return new FindCommand(String.join(" ", Arrays.copyOfRange(args,2,args.length)),"NAME");
            case "-t":
                return new FindCommand(String.join(" ", Arrays.copyOfRange(args,2,args.length)),"TYPE");
        }
        return null;
    }
}
