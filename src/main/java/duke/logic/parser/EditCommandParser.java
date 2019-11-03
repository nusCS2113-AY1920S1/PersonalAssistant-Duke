package duke.logic.parser;

import java.util.ArrayList;
import java.util.Optional;

import duke.logic.command.EditCommand;
import duke.exception.DukeException;

/**
 * Class that implements a Parser and handles the parsing of parameters into the edit command
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Method that takes in a string and breaks it down to an array list of a tuple keyword and field class
     * @param rawParameters String of the inputs of the edit
     * @return an array list of the keyword and field class
     * @throws DukeException throws an exception of theres no field inputted
     */
    private ArrayList<KeywordAndField> getKeywordAndFields(String rawParameters) throws DukeException {
        String[] splitParameters = rawParameters.split(" -");
        ArrayList<KeywordAndField> keywordAndEdits = new ArrayList<KeywordAndField>();
        if (splitParameters.length == 1) {
            throw new DukeException("Please enter something for me to edit!");
        }

        for (int i = 1; i < splitParameters.length; i++) {
            String[] s = splitParameters[i].split(" ", 2);
            if (s.length == 1) {
                throw new DukeException("Please enter something for me to edit!");
            } else {
                keywordAndEdits.add(new KeywordAndField(s[0], s[1]));
            }
        }
        return keywordAndEdits;
    }

    /**
     * Method that gets the correct index from the command
     * @param fullEditArg String of the command
     * @return the index
     * @throws DukeException throws an error if format of index is not numerical
     */
    private int getIndexFromCommand(String fullEditArg) throws DukeException {
        String[] temp = fullEditArg.split(" ", 2);
        try {
            int indexNo = Integer.parseInt(temp[0]) - 1;
            return indexNo;
        } catch (NumberFormatException e) {
            throw new DukeException("Please enter a valid index");
        }
    }

    /**
     * Parse command that takes in a filter and a string
     * @param filter of the task
     * @param args arguments of the command
     * @return an edit command with the right parameters
     * @throws DukeException throws a duke exception
     */
    public EditCommand parse(Optional<String> filter, String args) throws DukeException {
        ArrayList<KeywordAndField> keywordAndFields = getKeywordAndFields(args);
        int index = getIndexFromCommand(args);
        return new EditCommand(filter, index, keywordAndFields);
    }
}
