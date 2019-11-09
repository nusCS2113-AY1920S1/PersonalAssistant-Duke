package executor.command;

import interpreter.Parser;
import storage.StorageManager;
import ui.ReceiptTracker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class CommandEdit extends Command {
    protected String userInput;
    protected int index = 0;
    protected int receiptNumber = 0;
    protected String newTag = null;
    protected String newValue = null;
    protected String newDate = null;
    protected Double oldValue = 0.0;
    protected Double changeValue = 0.0;
    protected Double roundedChangeValue = 0.0;
    protected ArrayList<String> oldTag;
    protected LocalDate oldDate;

    /**
     * Constructor explaining about the command.
     * @param userInput is the input from user
     */
    public CommandEdit(String userInput) {
        super();
        this.userInput = userInput;
        this.description = "Allows the user to edit parts of their previous receipt input \n"
                + "FORMAT : edit <index> /<part to be editted> <new-input>";
        this.commandType = CommandType.EDIT;
    }

    @Override
    public void execute(StorageManager storageManager) {
        ReceiptTracker receiptTracker = new ReceiptTracker();
        boolean moreThanTwoDP = false;
        try {
            if (indexEmpty(userInput, CommandType.EDIT)) {
                outputError("No Index input detected");
                return;
            }
            index = Integer.parseInt(Parser.parseForPrimaryInput(CommandType.EDIT, userInput)) - 1;
            receiptNumber = index + 1;

            if (index < 0 || index >= storageManager.getWallet().getReceipts().size()) {
                outputError("Index out of bounds.");
                return;
            }

            newTag = Parser.parseForFlag("tag", this.userInput);
            newValue = Parser.parseForFlag("value", this.userInput);
            newDate = Parser.parseForFlag("date", this.userInput);

            if (newTag != null) {
                ArrayList<String> changeTag = new ArrayList<String>();
                changeTag.add(newTag);
                oldTag = storageManager.getWallet().getReceipts().get(index).getTags();
                storageManager.getWallet().getReceipts().get(index).setTags(changeTag);

                if (newTag.isEmpty()) {
                    outputErrorMessageOnGui(messageForTagChange() + noteTagEmpty());
                } else if (isAlpha(newTag)) {
                    outputErrorMessageOnGui(messageForTagChange());
                } else {
                    outputErrorMessageOnGui(messageForTagChange() + noteHasNonAlphabets());
                }
            } else if (newValue != null) {
                try {
                    if (cashValueEmpty(userInput)) {
                        outputError("Cash value is empty. Please key in an INTEGER/DOUBLE value");
                        return;
                    }
                    changeValue = Double.parseDouble(newValue);
                    if (BigDecimal.valueOf(changeValue).scale() > 2) {
                        roundedChangeValue = round(changeValue, 2);
                        moreThanTwoDP = true;
                    }
                    oldValue = storageManager.getWallet().getReceipts().get(index).getCashSpent();
                    storageManager.getWallet().getReceipts().get(index).setCashSpent(roundedChangeValue);
                    if (moreThanTwoDP) {
                        outputErrorMessageOnGui(messageForValueChange() + noteMoreThanTwoDP());
                    } else {
                        outputErrorMessageOnGui(messageForValueChange());
                    }
                } catch (NumberFormatException e) {
                    outputError("Cash value has be an INTEGER/DOUBLE");
                }

            } else if (newDate != null) {
                try {
                    LocalDate changeDate = LocalDate.parse(newDate);
                    oldDate = storageManager.getWallet().getReceipts().get(index).getDate();
                    storageManager.getWallet().getReceipts().get(index).setDate(changeDate);
                    outputErrorMessageOnGui(messageForDateChange());
                } catch (DateTimeParseException e) {
                    outputError("Invalid date input. FORMAT : YYYY-MM-DD");
                }
            } else {
                outputError("Flag invalid. Valid input : tag/value/date");
            }
        } catch (NumberFormatException e) {
            outputError("Index has be an INTEGER");
        }
    }

    /**
     * Boolean function to check if input string only contains alphabets.
     * @param name is the input from user
     * @return true if input string contains only alphabets
     */
    public boolean isAlpha(String name) {
        return name.matches("[a-zA-Z]+");
    }

    /**
     * Function to round up a Double from more than 2 DP to 2 DP.
     * @param value is the String value given by user
     * @param places is the number of decimal places to which the user input is rounded up to
     * @return is the rounded up value of the user input
     */
    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Function to output a String message as a pop-up below GUI when an error is encountered.
     * @param data is the output message
     */
    public void outputError(String data) {
        this.infoCapsule.setCodeError();
        this.infoCapsule.setOutputStr(data);
    }

    /**
     * Function to output a String message on the GUI when an error is encountered.
     * @param errorMessage is the output message
     */
    public void outputErrorMessageOnGui(String errorMessage) {
        this.infoCapsule.setCodeCli();
        this.infoCapsule.setOutputStr(errorMessage);
    }

    /**
     * Function to output a String message if the cash value input by user contains more than 2 decimal places.
     * @return is the output message
     */
    public String noteMoreThanTwoDP() {
        String output = "\nNOTE : The cash value had more then 2 decimal points, thus it was \nrounded up"
                + " from "
                + changeValue
                + " to "
                + roundedChangeValue;
        return output;
    }

    /**
     * Function to output a String message if the tag input by user contains characters apart from alphabets.
     * @return is the output message
     */
    public String noteHasNonAlphabets() {
        return "\nNOTE : Tag contains other characters apart from alphabets";
    }

    /**
     * Function to output a String message if the tag input by user is empty.
     * @return is the output message
     */
    public String noteTagEmpty() {
        return "\nNOTE : Tag is empty";
    }

    /**
     * Function to output a String message if the date is successfully changed.
     * @return is the output message
     */
    public String messageForDateChange() {
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
    public String messageForValueChange() {
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
    public String messageForTagChange() {
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
     * @param command is the command that is called to access the primary input of userInput
     * @return true if the index input by user is empty
     */
    public boolean indexEmpty(String userInput, CommandType command) {
        String indexStr = Parser.parseForPrimaryInput(command, userInput);
        if (indexStr.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Boolean function to check if the cash value input by user is empty.
     * @param userInput is the cash value input by user
     * @return true if userinput is empty
     */
    public boolean cashValueEmpty(String userInput) {
        String cashValueStr = Parser.parseForFlag("value", userInput);
        if (cashValueStr.isEmpty()) {
            return true;
        }
        return false;
    }
}
