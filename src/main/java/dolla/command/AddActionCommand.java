package dolla.command;

import dolla.DollaData;
import dolla.action.Redo;
import dolla.action.Repeat;
import dolla.action.Undo;
import dolla.parser.MainParser;
import dolla.parser.Parser;
import dolla.storage.StorageWrite;
import dolla.task.Entry;
import dolla.task.EntryList;
import dolla.task.Record;

import java.util.ArrayList;

public class AddActionCommand extends Command {
    private String mode;
    private String command;
    private static final String EMPTY_STACK_MESSAGE = "empty stack";
    private static final String NULL_MESSAGE = "null";

    //@@author yetong1895
    public AddActionCommand (String mode, String command) {
        this.mode = mode;
        this.command = command;
    }

    @Override
    public void execute(DollaData dollaData) throws Exception {
        switch (command) {
        case "undo":
            ArrayList<Record> records = Undo.processState(mode);
            dollaData.setRecordList(records);
            System.out.println("an undo entry have performed");
            break;
        case "redo":

            break;
        default:
            break;
        }

    }

    @Override
    public String getCommandInfo() {
        return null;
    }

}
