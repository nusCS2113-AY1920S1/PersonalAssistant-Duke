package dolla.parser;

import dolla.Tag;
import dolla.Time;
import dolla.action.Repeat;
import dolla.command.Command;
import dolla.command.AddEntryCommand;
import dolla.command.AddActionCommand;
import dolla.command.ShowListCommand;
import dolla.command.ErrorCommand;
import dolla.command.InitialModifyCommand;
import dolla.command.SortCommand;
import dolla.command.SearchCommand;
import dolla.command.RemoveCommand;
import dolla.command.modify.PartialModifyEntryCommand;
import dolla.task.Entry;
import dolla.ui.ModifyUi;

public class EntryParser extends Parser {

    private int recordNum;

    private static final String COMPONENT_TYPE = "/type";
    private static final String COMPONENT_DESC = "/desc";
    private static final String COMPONENT_AMOUNT = "/amount";
    private static final String COMPONENT_DATE = "/on";
    private static final String COMPONENT_TAG = "/tag";

    private static final String ENTRY_COMMAND_REDO = "redo";
    private static final String ENTRY_COMMAND_UNDO = "undo";
    private static final String ENTRY_COMMAND_REPEAT = "repeat";

    public EntryParser(String inputLine) {
        super(inputLine);
        this.mode = MODE_ENTRY;
    }

    @Override
    public Command parseInput() {

        if (commandToRun.equals(ENTRY_COMMAND_LIST)) { //show entry list
            return new ShowListCommand(mode);
        } else if (commandToRun.equals(ENTRY_COMMAND_ADD)) {
            if (verifyAddCommand()) {
                Tag t = new Tag();
                Entry entry = new Entry(inputArray[1], stringToDouble(inputArray[2]), description, date);
                t.handleTag(inputLine, inputArray, entry);
                return processAdd();
            } else {
                return new ErrorCommand();
            }
        } else if (commandToRun.equals(COMMAND_MODIFY)) {
            if (verifyFullModifyCommand()) {
                return new InitialModifyCommand(inputArray[1]);
            } else if (verifyPartialModifyEntryCommand()) {
                return new PartialModifyEntryCommand(recordNum, type, amount, description, date);
            } else {
                return new ErrorCommand();
            }
        } else if (commandToRun.equals(COMMAND_SORT)) {
            return new SortCommand(mode, inputArray[1]);
        } else if (commandToRun.equals("search")) {
            String component = inputArray[1];
            String content = inputArray[2];
            return new SearchCommand(mode, component, content);
        } else if (commandToRun.equals(COMMAND_REMOVE)) { //TODO: indexoutofbound exception
            return new RemoveCommand(mode, inputArray[1]);
        } else if (commandToRun.equals(ENTRY_COMMAND_REDO)
                || commandToRun.equals(ENTRY_COMMAND_UNDO)
                || commandToRun.equals(ENTRY_COMMAND_REPEAT)) {
            return new AddActionCommand(mode, commandToRun);
        } else {
            return invalidCommand();
        }
    }

    /**
     * Returns true if the input has no formatting issues.
     * Also designates the correct information to the relevant variables.
     * @return true if the input has no formatting issues.
     */
    public boolean verifyPartialModifyEntryCommand() {

        boolean flag = false;
        //ArrayList<String> errorList = new ArrayList<String>();
        type = null;
        amount = -1;
        description = null;
        date = null;

        try {
            recordNum = Integer.parseInt(inputArray[1]);
        } catch (Exception e) {
            ModifyUi.printInvalidFullModifyFormatError();
            return false;
        }

        for (int i = 0; i < inputArray.length; i += 1) {
            String currStr = inputArray[i];

            if (isEntryComponent(currStr)) {
                String nextStr = inputArray[i+1];
                //System.out.println(currStr +" "+ nextStr);
                try {
                    switch (currStr) {
                    case COMPONENT_TYPE:
                        type = verifyAddType(nextStr);
                        break;
                    case COMPONENT_AMOUNT:
                        amount = stringToDouble(nextStr);
                        break;
                    case COMPONENT_DESC:
                        description = parseEntryDesc(inputArray, i+1);
                        break;
                    case COMPONENT_DATE:
                        date = Time.readDate(nextStr);
                        break;
                    case COMPONENT_TAG:
                        //TODO
                        break;
                    }
                } catch (Exception e) {
                    ModifyUi.printInvalidPartialModifyFormatError();
                    return false;
                }
                flag = true;
            }
        }

        if (flag == false) {
            ModifyUi.printInvalidPartialModifyFormatError();
            return false;
        }
        return true;
    }

    public boolean isEntryComponent(String s) {
        switch (s) {
        case COMPONENT_TYPE:
        case COMPONENT_AMOUNT:
        case COMPONENT_DESC:
        case COMPONENT_DATE:
        case COMPONENT_TAG:
            return true;
        }
        return false;
    }

    public String parseEntryDesc(String[] inputArray, int index) {
        String tempStr = "";
        for (int i = index; i < inputArray.length; i += 1) {
            if (isEntryComponent(inputArray[i])) {
                break;
            }
            tempStr = tempStr.concat(inputArray[i] + " ");
        }
        // TODO: Remove the whitespace at the back
        tempStr = tempStr.substring(0, tempStr.length()-1);
        return tempStr;
    }

    //@@author yetong1895
    private Command processAdd() {
        Command addEntry;
        Repeat.setRepeatInput("entry", inputLine);
        if (undoFlag == 1) { //undo input
            addEntry = new AddEntryCommand(inputArray[1], stringToDouble(inputArray[2]),
                    description, date, prevPosition);
            resetUndoFlag();
        } else if (redoFlag == 1) {
            addEntry = new AddEntryCommand(inputArray[1], stringToDouble(inputArray[2]),
                    description, date, -2);
        } else { //normal input, prePosition is -1
            addEntry = new AddEntryCommand(inputArray[1], stringToDouble(inputArray[2]),
                    description, date, -1);
            resetRedoFlag();
        }
        return addEntry;
    }
}


