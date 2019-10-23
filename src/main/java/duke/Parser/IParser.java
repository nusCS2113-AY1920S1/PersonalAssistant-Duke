package duke.Parser;

import java.io.FileNotFoundException;

import java.text.ParseException;


public interface IParser {
    /**
     * Method.
     * @param input command.
     * @throws FileNotFoundException Exception.
     */
    void parseCommand(String input) throws FileNotFoundException;

}
