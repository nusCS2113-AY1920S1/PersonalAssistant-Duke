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
    void parse() {
        parser.setParserState(new ParserStateStub());
        assertThrows(
            DuchessException.class,
            () -> parser.parse("will throw exception"),
            "Stub test");
    }

    private class ParserStateStub extends ParserState {
        @Override
        public Command parse(String input) throws DuchessException {
            throw new DuchessException("Stub test");
        }

        @Override
        public Command continueParsing(Map<String, String> parameters) throws DuchessException {
            throw new DuchessException("Stub test");
        }
    }
}