package executor.command;

import interpreter.Parser;
import storage.StorageManager;
import ui.UiCode;

import java.time.Year;

public class CommandGetSpendingByYear extends Command {
    private String userInput;

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
        String yearStr = Parser.parseForPrimaryInput(CommandType.EXPENDEDYEAR, userInput);
        try {
            if (yearStr.isEmpty()) {
                this.infoCapsule.setCodeError();
                this.infoCapsule.setOutputStr("No year input detected.\nFORMAT : expendedyear 2019\n");
                return;
            }
            if (yearStr.length() != 4) {
                this.infoCapsule.setCodeError();
                this.infoCapsule.setOutputStr("Year input contains lesser/extra number of variables.\n"
                        + " FORMAT : expendedyear 2019\n");
                return;
            }

            int year = Integer.parseInt(yearStr);
            if (year > Year.now().getValue()) {
                this.infoCapsule.setCodeError();
                this.infoCapsule.setOutputStr("Future year entered is invalid!" + "\n");
                return;
            }

            if (year < 1800) {
                this.infoCapsule.setCodeError();
                this.infoCapsule.setOutputStr("Year is too far back into the past" + "\n");
                return;
            }
            Double totalMoney = storageManager.getReceiptsByYear(year).getTotalCashSpent();
            this.infoCapsule.setUiCode(UiCode.CLI);
            this.infoCapsule.setOutputStr("The total amount of money spent in " + year + " : $" + totalMoney + "\n");

        } catch (Exception e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Year input is either a double or contains String values.\n"
                    + "FORMAT : expendedyear 2019\n");
        }
    }
}

