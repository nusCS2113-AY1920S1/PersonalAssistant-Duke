package dolla.parser;

import dolla.command.Command;
import dolla.command.modify.InitialModifyCommand;
import dolla.command.ShowListCommand;
import dolla.command.ShowBillListCommand;
import dolla.command.ErrorCommand;
import dolla.command.AddDebtsCommand;
import dolla.command.AddBillCommand;
import dolla.command.SortCommand;
import dolla.command.ActionCommand;
import dolla.command.RemoveCommand;
import dolla.command.SearchCommand;
import dolla.command.RemoveNameCommand;

import dolla.model.DollaData;
import dolla.model.RecordList;
import dolla.ui.DebtUi;

import java.util.ArrayList;

//@@author tatayu
/**
 * DebtsParser is a class that handles the input command and
 * execute the command according to the command under the debt mode.
 */
public class DebtsParser extends Parser {

    public DebtsParser(String inputLine) {
        super(inputLine);
        this.mode = MODE_DEBT;
    }

    @Override
    public Command parseInput() {
        if (commandToRun.equals(DEBT_COMMAND_LIST)) { //show debt list
            return new ShowListCommand(mode);
        } else if (commandToRun.equals(BILL_COMMAND_LIST)) { //show bill list
            return new ShowBillListCommand(mode);
        } else if (commandToRun.equals(DEBT_COMMAND_OWE) || commandToRun.equals(DEBT_COMMAND_BORROW)) {
            if (verifyDebtCommand()) {
                return new AddDebtsCommand(commandToRun, inputArray[1], amount, description, date);
            } else {
                return new ErrorCommand();
            }
        } else if (commandToRun.equals(BILL_COMMAND_BILL)) {
            ArrayList<String> nameList = new ArrayList<>();
            if(verifyAddBillCommand(nameList)) {
                return new AddBillCommand(BILL_COMMAND_BILL,Integer.parseInt(inputArray[1]), amount, nameList);
            } else {
                return new ErrorCommand();
            }

        } else if (commandToRun.equals(BILL_COMMAND_PAID)) {
            RecordList recordList;
            DollaData dollaData = new DollaData();
            recordList = dollaData.getBillRecordList();
            if (recordList.size() == 0) {
                DebtUi.printEmptyBillMessage();
            } else {
                if (verifyPaidCommand(recordList)) {
                    return new RemoveNameCommand(Integer.parseInt(inputArray[1]), inputArray[2]);
                } else {
                    DebtUi.printInvalidPaidFormatError();
                    return new ErrorCommand();
                }
            }
        } else if (commandToRun.equals(COMMAND_MODIFY)) {
            if (verifyFullModifyCommand()) {
                return new InitialModifyCommand(inputArray[1]);
            } else if (verifyPartialModifyCommand()) {
                // TODO
            } else {
                return new ErrorCommand();
            }
        } else if (commandToRun.equals(COMMAND_SEARCH)) {
            if (verifyDebtSearchCommand()) {
                return new SearchCommand(mode, inputArray[1], inputArray[2]);
            } else {
                return new ErrorCommand();
            }
        } else if (commandToRun.equals(COMMAND_SORT)) {
            if (verifySort()) {
                return new SortCommand(mode, inputArray[1]);
            } else {
                return new ErrorCommand();
            }
        } else if (commandToRun.equals(COMMAND_REMOVE)) {
            if (verifyRemove()) {
                return new RemoveCommand(mode, inputArray[1]);
            } else {
                return new ErrorCommand();
            }
        } else if (commandToRun.equals(COMMAND_REDO)
                || commandToRun.equals(COMMAND_UNDO)
                || commandToRun.equals(COMMAND_REPEAT)) {
            return new ActionCommand(mode, commandToRun);
        } else {
            return invalidCommand();
        }
        return new ErrorCommand();
    }
}
