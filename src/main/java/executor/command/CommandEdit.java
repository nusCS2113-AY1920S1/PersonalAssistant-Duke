package executor.command;

import duke.exception.DukeException;
import interpreter.Parser;
import storage.StorageManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class CommandEdit extends Command {
    protected int index = 0;
    private int receiptNumber = 0;
    private String newTag = null;
    private String newValue = null;
    private String newDate = null;
    private String typeOfReceipt;
    private ArrayList<String> changeTag = new ArrayList<>();
    private Double oldValue = 0.0;
    private Double changeValue = 0.0;
    private Double roundedChangeValue = 0.0;
    private ArrayList<String> oldTag;
    private LocalDate oldDate;
    private  LocalDate changeDate;
    private boolean moreThanTwoDP = false;
    private boolean isFutureDate = false;
    boolean isIncomeReceipt = false;
    boolean isExpensesReceipt = false;

    /**
     * Constructor explaining about the command.
     * @param userInput is the input from user
     */
    public CommandEdit(String userInput) {
        super();
        this.userInput = userInput;
        this.description = "Allows the user to edit parts of their previous receipt input \n"
                + "FORMAT : edit <index> /<part to be edited> <new-input>";
        this.commandType = CommandType.EDIT;
    }

    @Override
    public void execute(StorageManager storageManager) {
        try {
            this.infoCapsule.setOutputStr("");
            index = Integer.parseInt(Parser.parseForPrimaryInput(CommandType.EDIT, userInput)) - 1;
            receiptNumber = index + 1;
            indexChecker(storageManager);
            checkAndUpdateFlag(storageManager);
        } catch (DukeException f) {
            this.infoCapsule.setCodeCli();
            this.infoCapsule.setOutputStr(f.getMessage());
        } catch (NumberFormatException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Index has to be an INTEGER"
                    + "\nFORMAT : edit <index> /<part to be edited> <new-input>");
        }
    }

    /**
     * Function to check and update the receipt according to the flag variable input by user.
     * @param storageManager is the class to access the getter functions for wallet
     * @throws DukeException is the error message
     */
    private void checkAndUpdateFlag(StorageManager storageManager) throws DukeException {
        newTag = Parser.parseForFlag("tag", this.userInput);
        newValue = Parser.parseForFlag("value", this.userInput);
        newDate = Parser.parseForFlag("date", this.userInput);

        if (newTag != null && newValue != null || newTag != null && newDate != null
                || newValue != null && newDate != null) {
            throw new DukeException("More than 1 flag detected. \nOnly key in one flag: /tag or /value or /date");
        }
        if (newTag != null) {
            updateTag(storageManager);
            return;
        }
        if (newValue != null) {
            updateCashValue(storageManager);
            return;
        }
        if (newDate != null) {
            updateDate(storageManager);
            return;
        }
        throw new DukeException("Flag invalid. Valid input : "
                + "/tag"
                + "/value"
                + "/date");
    }

    /**
     * Function to check all the exception pertaining the index output of the user.
     * @param storageManager is the class to access the getter functions for wallet
     * @throws DukeException is the error message
     */
    private void indexChecker(StorageManager storageManager) throws DukeException {
        if (storageManager.getWallet().getReceipts().size() == 0) {
            throw new DukeException("No receipts to edit");
        }
        if (indexEmpty(userInput)) {
            throw new DukeException("No Index input detected");
        }
        if (index < 0 || index >= storageManager.getWallet().getReceipts().size()) {
            throw new DukeException("Index out of bounds.");
        }
    }

    /**
     * Boolean function to check if input string only contains alphabets.
     * @param name is the input from user
     * @return true if input string contains only alphabets
     */
    private boolean isAlpha(String name) {
        return name.matches("[a-zA-Z]+");
    }

    /**
     * Function to round up a Double from more than 2 DP to 2 DP.
     * @param value is the String value given by user
     * @return is the rounded up value of the user input
     */
    private static double round(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Function to check if user input has more than 2 decimal places
     * and if it is more than 2 decimal places it will be rounded up to 2 DP.
     */
    private void checkAndChangeToTwoDP() {
        if (BigDecimal.valueOf(changeValue).scale() > 2) {
            roundedChangeValue = round(changeValue);
            moreThanTwoDP = true;
        }
    }

    /**
     * Function to output a String message if the cash value input by user contains more than 2 decimal places.
     * @return is the output message
     */
    private String noteMoreThanTwoDP() {
        return "\nNOTE : The cash value had more then 2 decimal points, thus it was \nrounded up"
                + " from "
                + changeValue
                + " to "
                + roundedChangeValue;
    }

    /**
     * Function to update the tag with the new input by the user.
     * @param storageManager is the class to access the getter functions for wallet
     */
    private void updateTag(StorageManager storageManager) {
        oldTag = storageManager.getWallet().getReceipts().get(index).getTags();
        checkForIncomeReceipt(storageManager);
        changeTag.add(typeOfReceipt);
        changeTag.add(newTag);
        storageManager.getWallet().getReceipts().get(index).setTags(changeTag);
        if (newTag.isEmpty()) {
            String noteTagEmpty = "\nNOTE : Tag is empty";
            this.infoCapsule.setCodeCli();
            this.infoCapsule.setOutputStr(messageForTagChange() + noteTagEmpty);
        } else if (isAlpha(newTag)) {
            this.infoCapsule.setCodeCli();
            this.infoCapsule.setOutputStr(messageForTagChange());
        } else {
            String noteHasNonAlphabets = "\nNOTE : Tag contains other characters apart from alphabets";
            this.infoCapsule.setCodeCli();
            this.infoCapsule.setOutputStr(messageForTagChange() + noteHasNonAlphabets);
        }
    }


    /**
     * Function to check is it is an income receipt.
     * @param storageManager is the class to access the getter functions for wallet
     */
    private void checkForIncomeReceipt(StorageManager storageManager) {
        typeOfReceipt = storageManager.getWallet().getReceipts().get(index).getTags().get(0);
        if (typeOfReceipt.equals("Income")) {
            isIncomeReceipt = true;
        }
    }

    /**
     * Function to update the cash value with the new input by the user.
     * @param storageManager is the class to access the getter functions for wallet
     */
    private void updateCashValue(StorageManager storageManager) {
        try {
            if (cashValueEmpty(userInput)) {
                throw new DukeException("Cash value is empty. Please key in an INTEGER/DOUBLE value");
            }
            changeValue = Double.parseDouble(newValue);
            checkForIncomeReceipt(storageManager);
            if (isIncomeReceipt) {
                changeValue = (-changeValue);
            }
            if (BigDecimal.valueOf(changeValue).scale() > 2) {
                checkAndChangeToTwoDP();
            } else {
                roundedChangeValue = changeValue;
            }
            oldValue = storageManager.getWallet().getReceipts().get(index).getCashSpent();
            storageManager.getWallet().getReceipts().get(index).setCashSpent(roundedChangeValue);
            outputForCashValueChange();
        } catch (NumberFormatException | DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Cash value has be an INTEGER/DOUBLE");
        }
    }

    /**
     * Function to output the change in cash value.
     */
    private void outputForCashValueChange() {
        if (moreThanTwoDP) {
            if (isIncomeReceipt) {
                this.infoCapsule.setCodeCli();
                this.infoCapsule.setOutputStr(this.infoCapsule.getOutputStr() + "\n\n" + messageForIncomeChange()
                        + noteMoreThanTwoDP());
            } else {
                this.infoCapsule.setCodeCli();
                this.infoCapsule.setOutputStr(this.infoCapsule.getOutputStr() + "\n\n" + messageForExpenseChange()
                        + noteMoreThanTwoDP());
            }
        } else {
            if (isIncomeReceipt) {
                this.infoCapsule.setCodeCli();
                this.infoCapsule.setOutputStr(this.infoCapsule.getOutputStr() + "\n\n" + messageForIncomeChange());
            } else {
                this.infoCapsule.setCodeCli();
                this.infoCapsule.setOutputStr(this.infoCapsule.getOutputStr() + "\n\n" + messageForExpenseChange());
            }
        }
    }

    /**
     * Function to update the date with the new input by the user.
     * @param storageManager is the class to access the getter functions for wallet
     */
    private void updateDate(StorageManager storageManager) {
        try {
            changeDate = LocalDate.parse(newDate);
            checkIfDateIsInFuture();
            oldDate = storageManager.getWallet().getReceipts().get(index).getDate();
            storageManager.getWallet().getReceipts().get(index).setDate(changeDate);
            if (isFutureDate) {
                String noteFutureYear = "\nNOTE : The year input is in the future";
                this.infoCapsule.setCodeCli();
                this.infoCapsule.setOutputStr(this.infoCapsule.getOutputStr() + "\n\n" + messageForDateChange()
                        + noteFutureYear);
            } else {
                this.infoCapsule.setCodeCli();
                this.infoCapsule.setOutputStr(this.infoCapsule.getOutputStr() + "\n\n" + messageForDateChange());
            }
        } catch (DateTimeParseException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Invalid date input. FORMAT : YYYY-MM-DD");
        }
    }

    /**
     * Function to set boolean to true if the date input by user is in the future.
     */
    private void checkIfDateIsInFuture() {
        if (changeDate.isAfter(LocalDate.now())) {
            isFutureDate = true;
        }
    }

    /**
     * Function to output a String message if the date is successfully changed.
     * @return is the output message
     */
    private String messageForDateChange() {
        return "The date for receipt "
                + receiptNumber
                + " was changed from "
                + oldDate
                + " to "
                + newDate
                + ".";
    }

    /**
     * Function to output a String message if the cash value is successfully changed.
     * @return is the output message
     */
    private String messageForExpenseChange() {
        return "The cashspent for receipt "
                + receiptNumber
                + " was changed from "
                + oldValue
                + " to "
                + roundedChangeValue
                + ".";
    }

    /**
     * Function to output a String message if the cash value is successfully changed.
     * @return is the output message
     */
    private String messageForIncomeChange() {
        return "The income for receipt "
                + receiptNumber
                + " was changed from "
                + oldValue
                + " to "
                + roundedChangeValue
                + ".";
    }

    /**
     * Function to output a String message if the tag is successfully changed.
     * @return is the output message
     */
    private String messageForTagChange() {
        return "The tag for receipt "
                + receiptNumber
                + " was changed from "
                + oldTag
                + " to "
                + changeTag
                + ".";
    }

    /**
     * Boolean function to check if the index input by user is empty.
     * @param userInput is the index input by user
     * @return true if the index input by user is empty
     */
    private boolean indexEmpty(String userInput) {
        String indexStr = Parser.parseForPrimaryInput(CommandType.EDIT, userInput);
        return indexStr.isEmpty();
    }

    /**
     * Boolean function to check if the cash value input by user is empty.
     * @param userInput is the cash value input by user
     * @return true if userinput is empty
     */
    private boolean cashValueEmpty(String userInput) {
        String cashValueStr = Parser.parseForFlag("value", userInput);
        assert cashValueStr != null;
        return cashValueStr.isEmpty();
    }
}
