package logic.parser;

import common.DukeException;
import logic.command.Command;
import logic.command.match.MatchMemberCommand;
import logic.command.match.MatchTaskCommand;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;

//@@author JustinChia1997
public class MatchCommandParserTest {
    @Test
    public void matchCommandParser_MatchTask_Success() throws DukeException {
        Command testingCommand = MatchCommandParser.parseMatch("task 1");
        MatchTaskCommand correctCommand = new MatchTaskCommand(1);
        assertEquals(correctCommand.getClass(), testingCommand.getClass());
    }

    @Test
    public void matchCommandParser_MatchMember_Success() throws DukeException {
        Command testingCommand = MatchCommandParser.parseMatch("member john");
        MatchMemberCommand correctCommand = new MatchMemberCommand("john");
        assertEquals(correctCommand.getClass(), testingCommand.getClass());
    }

    @Test
    public void matchCommandParser_MatchTaskWrongInputString_Failure() throws DukeException {
        assertThrows(DukeException.class, () -> MatchCommandParser.parseMatch("task testing"));
    }

    @Test
    public void matchCommandParser_MatchTaskNoInput_Failure() throws DukeException {
        assertThrows(DukeException.class, () -> MatchCommandParser.parseMatch("task "));
    }

    @Test
    public void matchCommandParser_MatchMemberNoInput_Failure() throws DukeException {
        assertThrows(DukeException.class, () -> MatchCommandParser.parseMatch("member "));
    }

}
