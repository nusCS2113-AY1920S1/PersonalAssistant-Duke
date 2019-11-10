package logic.parser;

import common.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author JasonChanWQ

public class ShowCommandParserTest {

    @Test
    public void showCommandParser_emptyInput_throwsException() {
        assertThrows(DukeException.class, () ->
                ShowCommandParser.parseShowCommand(""));
    }

    @Test
    public void showCommandParser_invalidShowType_throwsException() {
        assertThrows(DukeException.class, () ->
                ShowCommandParser.parseShowCommand("everything"));
    }

    @Test
    public void showCommandParser_emptyTaskIndex_throwsException() {
        assertThrows(DukeException.class, () ->
                ShowCommandParser.parseShowCommand("task"));
    }

    @Test
    public void showCommandParser_emptyMemberName_throwsException() {
        assertThrows(DukeException.class, () ->
                ShowCommandParser.parseShowCommand("member"));
    }

    @Test
    public void showCommandParser_invalidTaskIndex_throwsException() {
        assertThrows(DukeException.class, () ->
                ShowCommandParser.parseShowCommand("task a"));
    }
}
