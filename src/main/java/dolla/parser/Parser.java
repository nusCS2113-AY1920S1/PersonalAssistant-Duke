package dolla.parser;

import dolla.ModeStringList;
import dolla.Time;
import dolla.model.RecordList;
import dolla.exception.DollaException;

import dolla.ui.Ui;
import dolla.ui.DebtUi;
import dolla.ui.SearchUi;
import dolla.ui.ModifyUi;
import dolla.ui.EntryUi;
import dolla.ui.SortUi;
import dolla.ui.RemoveUi;

import dolla.command.Command;
import dolla.command.ErrorCommand;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import static dolla.parser.LimitParser.verifyLimitType;
import static dolla.parser.LimitParser.verifyLimitDuration;

//@@author omupenguin
/**
 * Parser is an abstract class that loads the appropriate command according to the user's input.
 * It also ensures that the user's input for the command is valid, such as by checking the format
 * of the input, and the utilisation of correct terms.
 */
public abstract class Parser implements ParserStringList, ModeStringList {

    protected String mode;

    protected static String inputLine;
    protected static String[] inputArray;
    protected String commandToRun;

    protected String type;
    protected String name;
    protected String description;
    protected double amount;
    protected String duration;
    protected LocalDate date;
    protected String tagName;

    protected int modifyRecordNum;
    protected static int maxAmount = 1000000;
    protected static int appropriateDecimalPlace = 2;


    protected static final String TYPE_OWE = "owe";
    protected static final String TYPE_BORROW = "borrow";

    /**
     * Creates an instance of a parser.
     * @param inputLine The entire string containing the user's input.
     */
    public Parser(String inputLine) {
        this.inputLine = inputLine;
        this.inputArray = inputLine.split(SPACE);
        this.commandToRun = inputArray[0];
    }

    public abstract Command parseInput() throws DollaException;

    public static String getInputLine() {
        return inputLine;
    }

    /**
     * Splits the input from the user and assigns the relevant data into description and date variables.
     * If the incorrect format is given in the input, the corresponding alert will be printed.
     */
    public void extractDescTime() throws Exception {
        // dataArray[0] is command, amount and description, dataArray[1] is time and tag
        String[] dataArray = inputLine.split(" " + COMPONENT_DATE + " ");
        String dateString = (dataArray[1].split(" " + COMPONENT_TAG))[0];
        description = dataArray[0].split(inputArray[2] + " ")[1];
        try {
            date = Time.readDate(dateString.trim());
        } catch (DateTimeParseException e) {
            Ui.printDateFormatError();
            throw new DollaException(INVALID_DATE_EXCEPTION);
        }
    }


    private static Boolean amountIsInvalid(double amount) {
        return (amount <= 0 || amount >= maxAmount || BigDecimal.valueOf(amount).scale() > appropriateDecimalPlace);
    }

    /**
     * Returns a double variable from the specified string.
     * <p>
     *     Returns 0 if the specified string is not of a number.
     * </p>
     * <p>
     *     Mainly used for using the specified string for calculations in the command.
     *     IE. Accessing arrays.
     * </p>
     * @param str String (of number) to be converted into integer type.
     * @return Integer type of the specified string.
     */
    public static double stringToDouble(String str) throws Exception {
        double newDouble;
        try {
            newDouble = Double.parseDouble(str);
            boolean amountIsInvalid = amountIsInvalid(newDouble);
            if (amountIsInvalid) {
                throw new DollaException(DollaException.invalidAmount());
            }
        } catch (NumberFormatException e) {
            Ui.printInvalidNumberError(str);
            throw new NumberFormatException(INVALID_AMOUNT_EXCEPTION);
        }
        return newDouble;
    }

    /**
     * Alerts the user that the input is invalid, and returns an ErrorCommand.
     * @return an ErrorCommand
     */
    public Command invalidCommand() {
        Ui.printInvalidCommandError();
        return new ErrorCommand();
    }

    /**
     * Checks if the first word after 'add' is either 'income' or 'expense'.
     * @param s String to be analysed.
     * @return Either 'expense' or 'income' if either are passed in.
     * @throws Exception ???
     */
    public static String verifyAddType(String s) throws Exception {
        if (s.equals("income") || s.equals("expense")) {
            return s;
        } else {
            EntryUi.printInvalidEntryType();
            throw new DollaException(DollaException.invalidType());
        }
    }

    /**
     * Returns true if no error occurs while creating the required variables for 'addEntryCommand'.
     * Also splits description and time components in the process.
     * @return true if no error occurs.
     */
    public boolean verifyAddCommand() {
        try {
            type = verifyAddType(inputArray[1]);
            amount = stringToDouble(inputArray[2]);
            extractDescTime();
        } catch (IndexOutOfBoundsException e) {
            EntryUi.printInvalidEntryFormatError();
            return false;
        } catch (Exception e) {
            return false; // If error occurs, stop the method!
        }
        return true;
    }

    //@@author tatayu
    /**
     * Returns true if no error occurs while creating the required variables for 'addDebtCommand'.
     * Also splits name, description components in the process.
     * @return true if no error occurs.
     */
    public boolean verifyDebtCommand() {
        try {
            try {
                Integer.parseInt(inputArray[1]);
                DebtUi.printInvalidNameMessage();
                return false;
            } catch (Exception ignored) {
                //do nothing
            }
            amount = stringToDouble(inputArray[2]);
            String[] desc = inputLine.split(inputArray[2] + SPACE);
            String[] dateString = desc[1].split(" /due ");
            description = dateString[0];
            return checkTag(dateString[1]);
        } catch (Exception e) {
            DebtUi.printInvalidDebtFormatError();
            return false;
        }
    }

    //@@author tatayu
    /**
     * Returns true if no error occurs while creating the date for 'addDebtCommand'.
     * @param dateString the string that contains date and tag.
     * @return true if no error occurs.
     */
    private boolean checkTag(String dateString) {
        if (inputLine.contains(COMPONENT_TAG)) {
            String[] dateAndTag = dateString.split(COMPONENT_TAG);
            try {
                date = Time.readDate(dateAndTag[0].trim());
            } catch (DateTimeParseException e) {
                Ui.printDateFormatError();
                return false;
            }
            return true;
        } else {
            try {
                date = Time.readDate(dateString.trim());
            } catch (DateTimeParseException e) {
                Ui.printDateFormatError();
                return false;
            }
            return true;
        }
    }

    //@@author yetong1895
    /**
     * This method will check if the input contain an type to sort.
     * @return true is inputArray[1] contain something, false if inputArray[1] is invalid.
     */
    protected boolean verifySort() {
        if (inputArray.length < 2) {
            SortUi.printInvalidSort(mode);
            return false;
        } else {
            switch (mode) {
            case MODE_ENTRY:
                if (inputArray[1].equals(SORT_TYPE_AMOUNT)
                    || inputArray[1].equals(SORT_TYPE_DATE)
                    || inputArray[1].equals(SORT_TYPE_DESC)) {
                    return true;
                } else {
                    SortUi.printInvalidSort(mode);
                    return false;
                }
            case MODE_DEBT:
                if (inputArray[1].equals(SORT_TYPE_AMOUNT)
                    || inputArray[1].equals(SORT_TYPE_DATE)
                    || inputArray[1].equals(SORT_TYPE_DESC)
                    || inputArray[1].equals(SORT_TYPE_NAME)) {
                    return true;
                } else {
                    SortUi.printInvalidSort(mode);
                    return false;
                }
            case MODE_LIMIT:
                if (inputArray[1].equals(SORT_TYPE_DATE)) {
                    return true;
                } else {
                    SortUi.printInvalidSort(mode);
                    return false;
                }
            default:
                SortUi.printInvalidSort(mode);
                return false;
            }
        }
    }

    /**
     * The method will check if the user have entered a valid number to be removed.
     * @return true if there is a valid number or false otherwise.
     */
    protected boolean verifyRemove() {
        if (inputArray.length != 2) {
            RemoveUi.printInvalidRemoveMessage();
            return false;
        }
        try {
            Integer.parseInt(inputArray[1]);
        } catch (NumberFormatException e) {
            RemoveUi.printInvalidRemoveMessage();
            return false;
        }
        return true;
    }

    protected boolean verifyShortcut() {
        if (inputArray.length != 2) {
            //print error message;
            return false;
        }
        try {
            Integer.parseInt(inputArray[1]);
        } catch (NumberFormatException e) {
            RemoveUi.printInvalidRemoveMessage();
            return false;
        }
        return true;
    }

    //@@author omupenguin
    /**
     * Returns true if the only element in the input that follows 'modify' is a number.
     * @return true if the only element in the input that follows 'modify' is a number.
     */
    public boolean verifyFullModifyCommand() {
        if (inputArray.length != 2) {
            return false;
        }
        try {
            Integer.parseInt(inputArray[1]);
        } catch (Exception e) {
            ModifyUi.printInvalidFullModifyFormatError();
            return false;
        }
        return true;
    }

    /**
     * Returns true if the input has no formatting issues.
     * Also designates the correct information to the relevant variables.
     * @return true if the input has no formatting issues.
     */
    public boolean verifyPartialModifyCommand() {

        type = null;
        amount = -1;
        description = null;
        date = null;
        duration = null;
        name = null;
        tagName = null;

        try {
            modifyRecordNum = Integer.parseInt(inputArray[1]);
        } catch (Exception e) {
            ModifyUi.printInvalidFullModifyFormatError();
            return false;
        }

        boolean hasComponents = findComponents();

        if (!hasComponents) {
            ModifyUi.printInvalidPartialModifyFormatError();
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns true if the input contains a component to be edited in the current mode,
     * demarcated with strings like "/type", and the entered data is valid.
     * Also designates the correct information to the relevant variables.
     * @return true if the input contains a component to be edited in the current mode, and is followed
     *         by valid data relevant to the component.
     */
    private boolean findComponents() {
        boolean hasComponents;
        hasComponents = false;
        for (int i = 0; i < inputArray.length; i += 1) {
            String currStr = inputArray[i];

            if (isComponent(currStr)) {
                try {
                    String nextStr = inputArray[i + 1];

                    switch (mode) {
                    case MODE_ENTRY:
                        verifyEntryComponents(currStr, nextStr, i);
                        break;
                    case MODE_LIMIT:
                        verifyLimitComponents(currStr, nextStr);
                        break;
                    case MODE_DEBT:
                        verifyDebtComponents(currStr, nextStr, i);
                        break;
                    default:
                        break;
                    }

                } catch (ArrayIndexOutOfBoundsException e) {
                    ModifyUi.printMissingComponentInfoError(currStr);
                    return false;
                } catch (DateTimeParseException e) {
                    Ui.printDateFormatError();
                    return false;
                } catch (Exception e) {
                    return false;
                }

                hasComponents = true;
            }
        }
        return hasComponents;
    }

    /**
     * Checks if the string from input (currStr) represents a component of entry. If so, verify and assign
     * the components of entry with the new data (nextStr).
     * @param currStr to be checked if it's a component (ie. /type).
     * @param nextStr the new data to be used for the specified component.
     * @param index the index of currStr in the input array.
     * @throws Exception when the nextStr is not a valid input for component from currStr.
     */
    private void verifyEntryComponents(String currStr, String nextStr, int index) throws Exception {
        try {
            switch (currStr) {
            case COMPONENT_TYPE:
                type = verifyAddType(nextStr);
                break;
            case COMPONENT_AMOUNT:
                amount = stringToDouble(nextStr);
                break;
            case COMPONENT_DESC:
                description = parseDesc(index + 1);
                break;
            case COMPONENT_DATE:
                date = Time.readDate(nextStr);
                break;
            default:
                break;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Checks if the string from input (currStr) represents a component of limit. If so, verify and assign
     * the components of limit with the new data (nextStr).
     * @param currStr to be checked if it's a component (ie. /type).
     * @param nextStr the new data to be used for the specified component.
     * @throws Exception when the nextStr is not a valid input for component from currStr.
     */
    private void verifyLimitComponents(String currStr, String nextStr) throws Exception {
        switch (currStr) {
        case COMPONENT_TYPE:
            type = verifyLimitType(nextStr);
            break;
        case COMPONENT_AMOUNT:
            amount = stringToDouble(nextStr);
            break;
        case COMPONENT_DURATION:
            duration = verifyLimitDuration(nextStr);
            break;
        default:
            break;
        }
    }

    /**
     * Checks if the first word after 'add' is either 'income' or 'expense'.
     * @param s String to be analysed.
     * @return Either 'expense' or 'income' if either are passed in.
     * @throws Exception ???
     */
    public static String verifyDebtType(String s) throws Exception {
        if (s.equals(TYPE_OWE) || s.equals(TYPE_BORROW)) {
            return s;
        } else {
            DebtUi.printInvalidDebtType();
            throw new DollaException(DollaException.invalidType());
        }
    }

    private void verifyDebtComponents(String currStr, String nextStr, int index) throws Exception {
        try {
            switch (currStr) {
            case COMPONENT_TYPE:
                type = verifyDebtType(nextStr);
                break;
            case COMPONENT_NAME:
                name = parseDesc(index + 1);
                break;
            case COMPONENT_AMOUNT:
                amount = stringToDouble(nextStr);
                break;
            case COMPONENT_DESC:
                description = parseDesc(index + 1);
                break;
            case COMPONENT_DATE:
                date = Time.readDate(nextStr);
                break;
            default:
                break;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Returns true if the specified string is a editable component for the current mode.
     * @param s the string to be checked.
     * @return true if the specified string is a editable component for the current mode.
     */
    private boolean isComponent(String s) {
        switch (mode) {
        case MODE_ENTRY:
            switch (s) {
            case COMPONENT_TYPE:
            case COMPONENT_AMOUNT:
            case COMPONENT_DESC:
            case COMPONENT_DATE:
                return true;
            default:
                break;
            }
            break;
        case MODE_LIMIT:
            switch (s) {
            case COMPONENT_TYPE:
            case COMPONENT_AMOUNT:
            case COMPONENT_DURATION:
                return true;
            default:
                break;
            }
            break;
        case MODE_DEBT:
            switch (s) {
            case COMPONENT_TYPE:
            case COMPONENT_NAME:
            case COMPONENT_AMOUNT:
            case COMPONENT_DESC:
            case COMPONENT_DATE:
                return true;
            default:
                break;
            }
            break;
        default:
            break;
        }
        return false;
    }

    /**
     * Extracts and returns a string containing the new description of the record to be modified.
     * @param index The index of element in inputArray to start extracting from.
     * @return string containing the new description of the record to be modified.
     */
    private String parseDesc(int index) {
        String tempStr = EMPTY_STR;
        for (int i = index; i < inputArray.length; i += 1) {
            if (isComponent(inputArray[i])) {
                break;
            }
            tempStr = tempStr.concat(inputArray[i] + SPACE);
        }
        tempStr = tempStr.substring(0, tempStr.length() - 1);
        return tempStr;
    }

    //@@author tatayu
    /**
     * Check if the component is valid.
     * @param s string at the component position.
     * @return true if it is a valid component.
     */
    protected Boolean verifyDebtSearchComponent(String s) {
        return s.equals(SEARCH_DESCRIPTION) || s.equals(SEARCH_DATE) || s.equals(SEARCH_NAME);
    }

    /**
     *check if the people and amount is valid.
     * @return true if they are valid.
     */
    protected Boolean verifyPaidNumberAndName() {
        try {
            Integer.parseInt(inputArray[1]);
            if (inputArray[2] == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    //@@author tatayu
    /**
     * Check if the number is valid.
     * @param recordList the list that records all the bill.
     * @return true if it is a valid number.
     */
    protected Boolean verifyPaidCommand(RecordList recordList) {
        if (verifyPaidNumberAndName()) {
            if (Integer.parseInt(inputArray[1]) <= recordList.size() && Integer.parseInt(inputArray[1]) > 0) {
                return true;
            } else {
                DebtUi.printInvalidBillNumberError();
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Check if the search command for debt is valid.
     * @return true if the command is valid.
     */
    protected Boolean verifyDebtSearchCommand() {
        try {
            if (verifyDebtSearchComponent(inputArray[1]) && inputArray[2] != null) {
                return true;
            } else {
                return false;
            }
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            SearchUi.printInvalidSearchFormat();
            return false;
        }
    }

    /**
     * Check if the search command for entry is valid.
     * @return true if the command is valid.
     */
    protected Boolean verifyEntrySearchCommand() {
        try {
            if (verifyEntrySearchComponent(inputArray[1]) && inputArray[2] != null) {
                return true;
            } else {
                SearchUi.printInvalidEntrySearchComponent();
                return false;
            }
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            SearchUi.printInvalidSearchFormat();
            return false;
        }
    }

    /**
     * Check if the search command for limit is valid.
     * @return true if the command is valid.
     */
    protected Boolean verifyLimitSearchCommand() {
        try {
            if (verifyLimitSearchComponent(inputArray[1]) && inputArray[2] != null) {
                return true;
            } else {
                SearchUi.printInvalidLimitSearchComponent();
                return false;
            }
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            SearchUi.printInvalidSearchFormat();
            return false;
        }
    }

    /**
     * Check if the search command for shortcut is valid.
     * @return true if the command is valid.
     */
    protected Boolean verifyShortcutSearchCommand() {
        try {
            if (verifyShortcutSearchComponent(inputArray[1]) && inputArray[2] != null) {
                return true;
            } else {
                SearchUi.printInvalidShortcutSearchComponent();
                return false;
            }
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            SearchUi.printInvalidSearchFormat();
            return false;
        }
    }

    /**
     * Check if the component is valid.
     * @param s string at the component position.
     * @return true if it is a valid component.
     */
    protected Boolean verifyEntrySearchComponent(String s) {
        return s.equals(SEARCH_DESCRIPTION) || s.equals(SEARCH_DATE);
    }

    /**
     * Check if the component is valid.
     * @param s string at the component position.
     * @return true if it is a valid component.
     */
    protected Boolean verifyLimitSearchComponent(String s) {
        return s.equals(SEARCH_DURATION);
    }

    /**
     * check if the component is valid.
     * @param s string at the component position.
     * @return true if it is a valid component.
     */
    protected Boolean verifyShortcutSearchComponent(String s) {
        return s.equals(SEARCH_DESCRIPTION);
    }

    /**
     *check if the people and amount is valid.
     * @return true if they are valid.
     */
    protected Boolean verifyBillPeopleAndAmount() {
        try {
            Integer.parseInt(inputArray[1]);
            stringToDouble(inputArray[2]);
        } catch (Exception e) {
            DebtUi.printInvalidBillFormatError();
            return false;
        }
        return true;
    }

    /**
     * check if the add bill command is valid.
     * @param nameList the array list to store the names.
     * @return true if the command is valid.
     */
    protected Boolean verifyAddBillCommand(ArrayList<String> nameList) {
        try {
            if (verifyBillPeopleAndAmount()) {
                amount = stringToDouble(inputArray[2]);
                for (int i = 3; i < 3 + Integer.parseInt(inputArray[1]); i++) {
                    String name = inputArray[i];
                    nameList.add(name);
                }
            } else {
                return false;
            }
        } catch (IndexOutOfBoundsException e) {
            DebtUi.printWrongPeopleNumberMessage(Integer.parseInt(inputArray[1]));
            return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //@@author Weng-Kexin
    protected Boolean verifySetCommand() {
        try {
            type = verifyLimitType(inputArray[1]);
            amount = stringToDouble(inputArray[2]);
            duration = verifyLimitDuration(inputArray[3]);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
