package wallet.logic.parser;

import org.junit.jupiter.api.Test;
import wallet.logic.command.DeleteCommand;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeleteCommandParserTest {

    @Test
    public void parse_missingId_throwsArrayIndexOutOfBoundsException() {
        DeleteCommandParser parser = new DeleteCommandParser();
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            DeleteCommand command = parser.parse("expense");
        });
    }

    @Test
    public void parse_invalidId_throwsNumberFormatException() {
        DeleteCommandParser parser = new DeleteCommandParser();
        assertThrows(NumberFormatException.class, () -> {
            DeleteCommand command = parser.parse("expense abc");
        });
    }
}
