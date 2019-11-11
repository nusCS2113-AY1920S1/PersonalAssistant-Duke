package dolla.parser;

import dolla.command.RemoveBillCommand;
import dolla.command.Command;
import dolla.command.AddBillCommand;
import dolla.command.RemoveCommand;
import dolla.command.SortCommand;
import dolla.command.ErrorCommand;
import dolla.command.ShowBillListCommand;
import dolla.command.SearchCommand;
import dolla.command.AddDebtsCommand;
import dolla.command.ShowListCommand;
import dolla.command.RemoveNameCommand;
import dolla.command.ActionCommand;
import dolla.command.modify.PartialModifyDebtCommand;
import dolla.model.DollaData;
import dolla.command.modify.InitialModifyCommand;

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

        if (isListCmd()) {
            return new ShowListCommand(mode);

        } else if (isListBillCmd()) {
            return new ShowBillListCommand(mode);

        } else if (isAddDebtCmd()) {
            if (verifyDebtCommand()) {
                return new AddDebtsCommand(commandToRun, inputArray[1], amount, description, date);
            } else {
                return new ErrorCommand();
            }
            
        } else if (isBillCmd()) {
            ArrayList<String> nameList = new ArrayList<>();
            if (verifyAddBillCommand(nameList)) {
                return new AddBillCommand(BILL_COMMAND_BILL,Integer.parseInt(inputArray[1]), amount, nameList);
            } else {
                return new ErrorCommand();
            }

        } else if (isPaidCmd()) {
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

        } else if (isModifyCmd()) {
            if (verifyFullModifyCommand()) {
                return new InitialModifyCommand(inputArray[1]);
            } else if (verifyPartialModifyCommand()) {
                return new PartialModifyDebtCommand(modifyRecordNum, type, name, amount, description, date);
            } else {
                return new ErrorCommand();
            }

        } else if (isSearchCmd()) {
            if (verifyDebtSearchCommand()) {
                return new SearchCommand(mode, inputArray[1], inputArray[2]);
            } else {
                return new ErrorCommand();
            }

        } else if (isSortCmd()) {
            if (verifySort()) {
                return new SortCommand(mode, inputArray[1]);
            } else {
                return new ErrorCommand();
            }

        } else if (isRemoveCmd()) {
            RecordList recordList;
            DollaData dollaData = new DollaData();
            recordList = dollaData.getBillRecordList();
            if (verifyRemoveLength()) {
                if (verifyRemoveForDebtMode()) {
                    return new RemoveCommand(mode, inputArray[1]);
                } else if (verifyRemoveBill(recordList)) {
                    return new RemoveBillCommand(Integer.parseInt(inputArray[2]));
                } else {
                    return new ErrorCommand();
                }
            } else {
                return new ErrorCommand();
            }
        } else if (isActionCmd()) {
            return new ActionCommand(mode, commandToRun);

        } else {
            return invalidCommand();
        }
        return invalidCommand();
    }

    private boolean isListCmd() {
        return commandToRun.equals(DEBT_COMMAND_LIST);
    }

    private boolean isListBillCmd() {
        return commandToRun.equals(BILL_COMMAND_LIST);
    }

    private boolean isBillCmd() {
        return commandToRun.equals(BILL_COMMAND_BILL);
    }

    private boolean isPaidCmd() {
        return commandToRun.equals(BILL_COMMAND_PAID);
    }
}
