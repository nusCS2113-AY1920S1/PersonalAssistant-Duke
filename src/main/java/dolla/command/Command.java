package dolla.command;

import dolla.model.DollaData;
import dolla.ModeStringList;
import dolla.model.RecordList;
import dolla.ui.ListUi;
import dolla.exception.DollaException;
import dolla.ui.Ui;

/**
 * Command is an abstract class which all types of command will inherit from.
 */
public abstract class Command implements ModeStringList {
    protected int index;

    /**
     * This method is called to execute the titular command after every user input.
     * @throws DollaException handle exception
     */
    public abstract void execute(DollaData dollaData) throws DollaException;

    /**
     * Returns a string containing the data stored in the command.
     * @return string containing data stored in command.
     */
    public abstract String getCommandInfo();

    /**
     * Returns an integer variable from the specified string.
     * Returns 0 if the specified string is not of a number.
     * @param str String (of number) to be converted into integer type.
     * @return Integer type of the specified string.
     */
    protected int stringToInt(String str) {
        int newInt = 0;
        try {
            newInt = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            Ui.printInvalidNumberError(str);
        }
        return newInt;
    }

    protected Boolean recordDoesNotExist(int recordIndex) {
        return (recordIndex == - 1);
    }

    /**
     * Returns true is the given index is within the recordList.
     * @param recordList The recordList containing the record to be modified.
     * @return true if index is within the specified recordList.
     */
    protected boolean isIndexInList(RecordList recordList, String currMode) {
        if (index >= recordList.size()) {
            ListUi.printNoRecordAssocError(index, currMode);
            return false;
        } else {
            return true;
        }
    }
}
