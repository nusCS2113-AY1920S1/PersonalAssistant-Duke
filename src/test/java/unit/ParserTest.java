package unit;

import org.junit.jupiter.api.Test;
import spinbox.Parser;
import spinbox.commands.Command;
import spinbox.exceptions.InputException;
import spinbox.exceptions.SpinBoxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ParserTest {

    @Test
    void testParser_passValidCommands_parserReturnsValidCommandAfterBuildingCommand() throws SpinBoxException {
        try {
            String userInput1 = "random";
            Command command = Parser.parse(userInput1);
            fail();
        } catch (InputException e) {
            assertEquals("Invalid Input\n\nPlease provide a valid command:\n"
                    + "'<action> <page> / <content>' or 'bye'", e.getMessage());
        }
    }
}