package logic.parser;

import common.DukeException;
import logic.parser.list.ListCommandParser;
import logic.parser.list.ListMembersParser;
import logic.parser.list.ListTasksAllParser;
import logic.parser.list.ListTasksParser;
import logic.parser.list.ListTasksTodoParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ListCommandParserTest {

    @Test
    public void listCommand_wrong_listType() {
        assertThrows(DukeException.class, () ->
                ListCommandParser.parseListCommand("others"));
    }

    @Test
    public void listMember_wrong_listType() {
        assertThrows(DukeException.class, () ->
                ListMembersParser.parseListMembers("something"));
    }

    @Test
    public void listTask_wrong_listType() {
        assertThrows(DukeException.class, () ->
                ListTasksParser.parseListTasks("something"));
    }

    @Test
    public void listTask_all_wrong_listType() {
        assertThrows(DukeException.class, () ->
               ListTasksAllParser.parseListTasksAll("something"));
    }

    @Test
    public void listTask_todo_wrong_listType() {
        assertThrows(DukeException.class, () ->
                ListTasksTodoParser.parseListTasksTodo("something"));
    }
}
