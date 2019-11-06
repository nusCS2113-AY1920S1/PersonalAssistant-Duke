package duchess.parser;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.Command;
import duchess.parser.states.ParserState;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {
    private final Parser parser = new Parser();

    @Test
    public void parse_parses() {
        parser.setParserState(new ParserStateStub());
        assertThrows(
            DuchessException.class,
            () -> parser.parse("will throw exception"),
            "Stub test");
    }

    @Test
    public void continueParsing_parses() {
        parser.setParserState(new ParserStateStub());
        assertThrows(
            DuchessException.class,
            () -> parser.continueParsing(Util.parameterizeWithoutCommand("test command")),
            "Stub test 2");
    }

    private class ParserStateStub extends ParserState {
        @Override
        public Command parse(String input) throws DuchessException {
            throw new DuchessException("Stub test");
        }

        @Override
        public Command continueParsing(Map<String, String> parameters) throws DuchessException {
            throw new DuchessException("Stub test 2");
        }
    }
}