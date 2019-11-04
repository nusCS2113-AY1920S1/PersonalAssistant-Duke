package dolla.parser;

import dolla.Tag;


import dolla.command.AddLimitCommand;
import dolla.command.Command;
import dolla.command.ErrorCommand;
import dolla.command.ShowListCommand;
import dolla.command.RemoveCommand;
import dolla.command.SearchCommand;
import dolla.command.SortCommand;
import dolla.command.AddActionCommand;
import dolla.task.Limit;
import dolla.ui.LimitUi;
import dolla.ui.SearchUi;
import dolla.ui.Ui;

/**
 * This class handles all limit related parsing (set, edit, remove).
 */
//@@author Weng-Kexin
public class LimitParser extends Parser {


    protected LimitParser(String inputLine) {
        super(inputLine);
        this.mode = MODE_LIMIT;
    }

    @Override
    public Command parseInput() {
        if (commandToRun.equals(ParserStringList.LIMIT_COMMAND_LIST)) {
            return new ShowListCommand(mode);
        } else if (commandToRun.equals(ParserStringList.LIMIT_COMMAND_SET)) {
            if (verifySetLimitCommand()) {
                String typeStr = inputArray[1];
                double amountInt = stringToDouble(inputArray[2]);
                String durationStr = inputArray[3];
                Limit limit = new Limit(typeStr, amountInt, durationStr);
                Tag t = new Tag();
                t.handleTag(inputLine, inputArray, limit);
                return new AddLimitCommand(typeStr, amountInt, durationStr);
            } else {
                LimitUi.invalidSetCommandPrinter();
                return new ErrorCommand();
            }
        } else if (commandToRun.equals(ParserStringList.COMMAND_REMOVE)) {
            if (verifyRemove()) {
                return new RemoveCommand(mode, inputArray[1]);
            } else {
                return new ErrorCommand();
            }
        } else if (commandToRun.equals(COMMAND_MODIFY)) {
            if (verifyFullModifyCommand()) {
                // TODO: Update when ready
                //return new InitialModifyCommand(inputArray[1]);
                Ui.printUpcomingFeature();
                return new ErrorCommand();
            } else if (verifyPartialModifyCommand()) {
                // TODO:
                return new ErrorCommand();
            } else {
                return new ErrorCommand();
            }
        } else if (commandToRun.equals(ParserStringList.COMMAND_SEARCH)) {
            String component = null;
            String content = null;
            try {
                if (verifyDebtSearchComponent(inputArray[1]) && inputArray[2] != null) {
                    component = inputArray[1];
                    content = inputArray[2];
                } else {
                    SearchUi.printInvalidDebtSearchComponent();
                }
            } catch (NullPointerException e) {
                SearchUi.printInvalidSearchFormat();
                return new ErrorCommand();
            } catch (IndexOutOfBoundsException e) {
                SearchUi.printInvalidSearchFormat();
                return new ErrorCommand();
            }
            return new SearchCommand(mode, component, content);
        } else if (commandToRun.equals(ParserStringList.COMMAND_SORT)) {
            if (verifySort()) {
                return new SortCommand(mode, inputArray[1]);
            } else {
                return new ErrorCommand();
            }
        } else if (commandToRun.equals(COMMAND_REDO)
                || commandToRun.equals(COMMAND_UNDO)
                || commandToRun.equals(COMMAND_REPEAT)) {
            return new AddActionCommand(mode, commandToRun);
        } else {
            return invalidCommand();
        }
    }
}