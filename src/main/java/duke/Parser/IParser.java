package duke.Parser;

import java.io.FileNotFoundException;
import java.text.ParseException;

public interface IParser {
    /**
     * A method to parse commands.
     * @param input command.
     */
    void parseCommand(String input) throws FileNotFoundException, ParseException;
}
