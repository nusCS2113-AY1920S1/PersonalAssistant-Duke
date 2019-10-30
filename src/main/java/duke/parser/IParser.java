package duke.parser;

import java.io.FileNotFoundException;


public interface IParser {
    /**
     * Method.
     *
     * @param input command.
     * @throws FileNotFoundException Exception.
     */
    void parseCommand(String input) throws FileNotFoundException;

}
