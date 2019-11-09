package dolla.action;

import dolla.command.Command;
import dolla.command.action.state.RedoStateList;
import dolla.command.action.state.UndoStateList;
import dolla.exception.DollaException;
import dolla.model.DollaData;
import dolla.model.Record;
import dolla.parser.MainParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author yetong1895
public class IntegratedActionTest implements ActionTestStringList {

    @Test
    public void testUndoAndRedo1() throws DollaException {
        DollaData dollaData = new DollaData();
        Command c = MainParser.handleInput(dollaData.getMode(), MODE_ENTRY);
        c.execute(dollaData);

        c = MainParser.handleInput(dollaData.getMode(), TEST_INPUT1);
        c.execute(dollaData);
        c = MainParser.handleInput(dollaData.getMode(), TEST_INPUT2);
        c.execute(dollaData);
        c = MainParser.handleInput(dollaData.getMode(), COMMAND_UNDO);
        c.execute(dollaData);

        assertEquals(1,dollaData.getListSize(MODE_ENTRY));
        Record record = dollaData.getRecordFromList(MODE_ENTRY, 0);
        assertEquals(TEST_OUTPUT1, record.getUserInput());

        c = MainParser.handleInput(dollaData.getMode(), COMMAND_REDO);
        c.execute(dollaData);
        assertEquals(2,dollaData.getListSize(MODE_ENTRY));
        record = dollaData.getRecordFromList(MODE_ENTRY, 1);
        assertEquals(TEST_OUTPUT2, record.getUserInput());

        c = MainParser.handleInput(dollaData.getMode(), COMMAND_UNDO);
        c.execute(dollaData);
        c = MainParser.handleInput(dollaData.getMode(), COMMAND_UNDO);
        c.execute(dollaData);
        assertEquals(0,dollaData.getListSize(MODE_ENTRY));
        assertEquals(0, UndoStateList.getSize(MODE_ENTRY));
        assertEquals(2, RedoStateList.getSize(MODE_ENTRY));
    }

    @Test
    public void testUndoAndRedo2() throws DollaException {
        DollaData dollaData = new DollaData();
        Command c = MainParser.handleInput(dollaData.getMode(), MODE_ENTRY);
        c.execute(dollaData);

        c = MainParser.handleInput(dollaData.getMode(), TEST_INPUT3);
        c.execute(dollaData);
        c = MainParser.handleInput(dollaData.getMode(), TEST_INPUT2);
        c.execute(dollaData);
        c = MainParser.handleInput(dollaData.getMode(), TEST_INPUT1);
        c.execute(dollaData);

        c = MainParser.handleInput(dollaData.getMode(), COMMAND_UNDO);
        c.execute(dollaData);
        c = MainParser.handleInput(dollaData.getMode(), COMMAND_UNDO);
        c.execute(dollaData);

        assertEquals(1,dollaData.getListSize(MODE_ENTRY));
        Record record = dollaData.getRecordFromList(MODE_ENTRY, 0);
        assertEquals(TEST_OUTPUT3, record.getUserInput());

        c = MainParser.handleInput(dollaData.getMode(), TEST_INPUT4);
        c.execute(dollaData);

        record = dollaData.getRecordFromList(MODE_ENTRY, 1);
        assertEquals(TEST_OUTPUT4,record.getUserInput());
        assertEquals(0, RedoStateList.getSize(MODE_ENTRY)); //check if redo have been cleared.

        c = MainParser.handleInput(dollaData.getMode(), COMMAND_UNDO);
        c.execute(dollaData);
        c = MainParser.handleInput(dollaData.getMode(), COMMAND_UNDO);
        c.execute(dollaData);
        assertEquals(0,dollaData.getListSize(MODE_ENTRY));
        assertEquals(0, UndoStateList.getSize(MODE_ENTRY));
        assertEquals(2, RedoStateList.getSize(MODE_ENTRY));
    }
}
