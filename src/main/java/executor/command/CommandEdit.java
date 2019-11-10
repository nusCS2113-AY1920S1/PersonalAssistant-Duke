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
    protected String userInput;
    protected int index = 0;
    private int receiptNumber = 0;
    private String newTag = null;
    private String newValue = null;
    private String newDate = null;
    private ArrayList<String> changeTag = new ArrayList<>();
    private Double oldValue = 0.0;
    private Double changeValue = 0.0;
    private Double roundedChangeValue = 0.0;
    private ArrayList<String> oldTag;
    private LocalDate oldDate;
    private  LocalDate changeDate;
    private boolean moreThanTwoDP = false;
    private boolean isFutureDate = false;

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
            index = Integer.parseInt(Parser.parseForPrimaryInput(CommandType.EDIT, userInput)) - 1;
            receiptNumber = index + 1;
            indexChecker(storageManager);
            newTag = Parser.parseForFlag("tag", this.userInput);
            newValue = Parser.parseForFlag("value", this.userInput);
            newDate = Parser.parseForFlag("date", this.userInput);
            checkAndUpdateFlag(storageManager);
        } catch (NumberFormatException e) {
            outputError("Index has be an INTEGER");
        } catch (DukeException f) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(f.getMessage());
        }
    }

    /**
     * Function to check and update the receipt according to the flag variable input by user.
     * @param storageManager is the class to access the getter functions for wallet
     * @throws DukeException is the error message
     */
    private void checkAndUpdateFlag(StorageManager storageManager) throws DukeException {
        if (newTag != null) {
            updateTag(storageManager);
        } else if (newValue != null) {
            updateCashValue(storageManager);
        } else if (newDate != null) {
            updateDate(storageManager);
        } else {
            throw new DukeException("Flag invalid. Valid input : tag/value/date");
        }
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
     * @param places is the number of decimal places to which the user input is rounded up to
     * @return is the rounded up value of the user input
     */
    private static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Function to check if user input has more than 2 decimal places
     * and if it is more than 2 decimal places it will be rounded up to 2 DP.
     */
    private void checkAndChangeToTwoDP() {
        if (BigDecimal.valueOf(changeValue).scale() > 2) {
            roundedChangeValue = round(changeValue, 2);
            moreThanTwoDP = true;
        }
    }

    /**
     * Function to output a String message as a pop-up below GUI when an error is encountered.
     * @param data is the output message
     */
    private void outputError(String data) {
        this.infoCapsule.setCodeError();
        this.infoCapsule.setOutputStr(data);
    }

    /**
     * Function to output a String message on the GUI when an error is encountered.
     * @param errorMessage is the output message
     */
    private void outputMessageOnGui(String errorMessage) {
        this.infoCapsule.setCodeCli();
        this.infoCapsule.setOutputStr(errorMessage);
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
     * Function to return a output string if the user input date is in the future.
     * @return is the output string
     */
    private String noteFutureYear() {
        return "\nNOTE : The year input is in the future";
    }

    /**
     * Function to output a String message if the tag input by user contains characters apart from alphabets.
     * @return is the output message
     */
    private String noteHasNonAlphabets() {
        return "\nNOTE : Tag contains other characters apart from alphabets";
    }

    /**
     * Function to output a String message if the tag input by user is empty.
     * @return is the output message
     */
    private String noteTagEmpty() {
        return "\nNOTE : Tag is empty";
    }

    /**
     * Function to update the tag with the new input by the user.
     * @param storageManager is the class to access the getter functions for wallet
     */
    private void updateTag(StorageManager storageManager) {
        changeTag.add(newTag);
        oldTag = storageManager.getWallet().getReceipts().get(index).getTags();
        storageManager.getWallet().getReceipts().get(index).setTags(changeTag);
        if (newTag.isEmpty()) {
            outputMessageOnGui(messageForTagChange() + noteTagEmpty());
        } else if (isAlpha(newTag)) {
            outputMessageOnGui(messageForTagChange());
        } else {
            outputMessageOnGui(messageForTagChange() + noteHasNonAlphabets());
        }
    }

    /**
     * Function to update the cash value with the new input by the user.
     * @param storageManager is the class to access the getter functions for wallet
     */
    private void updateCashValue(StorageManager storageManager) {
        try {
            if (cashValueEmpty(userInput)) {
                outputError("Cash value is empty. Please key in an INTEGER/DOUBLE value");
                return;
            }
            changeValue = Double.parseDouble(newValue);
            checkAndChangeToTwoDP();
            oldValue = storageManager.getWallet().getReceipts().get(index).getCashSpent();
            storageManager.getWallet().getReceipts().get(index).setCashSpent(roundedChangeValue);
            if (moreThanTwoDP) {
                outputMessageOnGui(messageForValueChange() + noteMoreThanTwoDP());
            } else {
                outputMessageOnGui(messageForValueChange());
            }
        } catch (NumberFormatException e) {
            outputError("Cash value has be an INTEGER/DOUBLE");
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
                outputMessageOnGui(messageForDateChange() + noteFutureYear());
            } else {
                outputMessageOnGui(messageForDateChange());
            }
        } catch (DateTimeParseException e) {
            outputError("Invalid date input. FORMAT : YYYY-MM-DD");
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
    private String messageForValueChange() {
        return "The cashspent for receipt "
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
                + newTag
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
