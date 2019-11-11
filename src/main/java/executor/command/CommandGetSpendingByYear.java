package executor.command;

import duke.exception.DukeException;
import interpreter.Parser;
import storage.StorageManager;
import ui.UiCode;

import java.time.Year;

public class CommandGetSpendingByYear extends Command {
    private String userInput;
    private String yearStr;
    private int year;

    /**
     * Constructor to explain about the method.
     * @param userInput userInput from CLI
     */
    public CommandGetSpendingByYear(String userInput) {
        super();
        this.commandType = CommandType.EXPENDEDYEAR;
        this.userInput = userInput;
        this.description = "Provides the user the total expenditure for the year stated. \n"
                + "FORMAT : expendedyear <year>\n";
    }

    @Override
    public void execute(StorageManager storageManager) {
        try {
            checkUserInput();
            checkIfYearCredible();
            outputOfExpenditure(storageManager);
        } catch (DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(e.getMessage());
        } catch (Exception e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Year input is either a double or contains String values.\n"
                    + "FORMAT : expendedyear 2019\n");
        }
    }

    /**
     * Function to check if the userinput has a valid year.
     * @throws DukeException is the error message
     */
    private void checkUserInput() throws DukeException {
        yearStr = Parser.parseForPrimaryInput(CommandType.EXPENDEDYEAR, userInput);
        if (yearStr.isEmpty()) {
            throw new DukeException("No year input detected.\nFORMAT : expendedyear 2019\n");
        }
        if (yearStr.length() != 4) {
            throw new DukeException("Year input contains lesser/extra number of variables.\n"
                    + " FORMAT : expendedyear 2019\n");
        }
    }

    /**
     * Function to check the credibility of the year.
     * @throws DukeException is the error message
     */
    private void checkIfYearCredible() throws DukeException {
        year = Integer.parseInt(yearStr);
        if (year > Year.now().getValue()) {
            throw new DukeException("Future year entered is invalid!" + "\n");
        }

        if (year < 1800) {
            this.infoCapsule.setCodeError();
            throw new DukeException("Year is too far back into the past" + "\n");
        }
    }

    /**
     * Function to ouput the total expenditure.
     * @param storageManager is the class that contains all the getter functions for the wallet
     * @throws DukeException is the error message
     */
    private void outputOfExpenditure(StorageManager storageManager) throws DukeException {
        Double totalMoney = storageManager.getReceiptsByYear(year).getTotalExpenses();
        this.infoCapsule.setUiCode(UiCode.CLI);
        this.infoCapsule.setOutputStr("The total amount of money spent in " + year + " : $" + totalMoney + "\n");
    }
}

