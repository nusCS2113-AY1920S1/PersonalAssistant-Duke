package dolla.parser;

import dolla.Tag;

import dolla.command.Command;

import dolla.command.AddLimitCommand;
import dolla.command.RemoveCommand;
import dolla.command.ShowListCommand;
import dolla.command.ErrorCommand;
import dolla.command.SortCommand;
import dolla.command.SearchCommand;

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
            boolean verifiedSetCommand = verifySetLimitCommand();
            if (verifiedSetCommand) {
                String typeStr = inputArray[1];
                double amountInt = findLimitAmount();
                String durationStr = inputArray[3];
                Limit limit = new Limit(typeStr, amountInt, durationStr);
                Tag t = new Tag();
                t.handleTag(inputLine, inputArray, limit);
                return new AddLimitCommand(typeStr, amountInt, durationStr);
            } else {
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
            String component;
            String content;
            try {
                component = verifySearchCommand(inputArray[1]);
                content = inputArray[2];
            } catch (IndexOutOfBoundsException e) {
                SearchUi.printInvalidSearchFormat();
                return new ErrorCommand();
            } catch (Exception e) {
                return new ErrorCommand();
            }
            return new SearchCommand(mode, component, content);
        } else if (commandToRun.equals(ParserStringList.COMMAND_SORT)) {
            if (verifySort()) {
                return new SortCommand(mode, inputArray[1]);
            } else {
                return new ErrorCommand();
            }
        } else {
            return invalidCommand();
        }
    }
}