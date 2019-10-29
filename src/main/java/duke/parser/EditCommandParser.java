package duke.parser;

import java.util.ArrayList;
import java.util.Optional;

import duke.command.EditCommand;
import duke.exception.DukeException;

public class EditCommandParser implements Parser<EditCommand> {

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

    private int getIndexFromCommand(String fullEditArg) throws DukeException {
        String[] temp = fullEditArg.split(" ", 2);
        try {
            int indexNo = Integer.parseInt(temp[0]);
            return indexNo;
        } catch (NumberFormatException e) {
            throw new DukeException("Please enter a valid index");
        }
    }

    public EditCommand parse(Optional<String> filter, String args) throws DukeException {
        ArrayList<KeywordAndField> keywordAndFields = getKeywordAndFields(args);
        int index = getIndexFromCommand(args);
        return new EditCommand(filter, index, keywordAndFields);
    }
}
