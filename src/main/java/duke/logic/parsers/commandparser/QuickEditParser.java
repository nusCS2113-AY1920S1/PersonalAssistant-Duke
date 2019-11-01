package duke.logic.parsers.commandparser;

import duke.commons.exceptions.ParseException;
import duke.logic.commands.Command;
import duke.logic.commands.QuickEditCommand;
import duke.logic.parsers.ParserUtil;

public class QuickEditParser extends CommandParser {
    private int index;
    private String firstField;
    private String secondField;
    private String thirdField;

    /**
     * Parses user input into an Itinerary.
     *
     * @param inputBody The User input.
     * @throws ParseException If the input parsing fails.
     */
    public QuickEditParser(String inputBody) throws ParseException {
        index = ParserUtil.getIntegerIndexInList(0, 4, inputBody);
        firstField = ParserUtil.getFieldInList(1, 4, inputBody);
        secondField = ParserUtil.getFieldInList(2, 4, inputBody);
        thirdField = ParserUtil.getFieldInList(3, 4, inputBody);
    }

    @Override
    public Command parse() {
        return new QuickEditCommand(index, firstField, secondField,thirdField);
    }
}
