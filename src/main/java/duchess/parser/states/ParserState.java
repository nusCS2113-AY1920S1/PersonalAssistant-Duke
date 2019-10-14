package duchess.parser.states;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.Command;

public interface ParserState {
    public abstract Command parse(String input) throws DuchessException;
}
