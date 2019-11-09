package executor.command;

import duke.exception.DukeException;
import interpreter.Parser;
import storage.StorageManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class CommandBudget extends Command {

    private Double budgetAmount;

    /**
     * CommandBudget helps user to set the budget.
     * @param userInput String is the user entered input
     */
    public CommandBudget(String userInput) {
        super();
        this.userInput = userInput;
        this.description = " Sets user budget \n"
                + "FORMAT : budget $<amount>\n";
        this.commandType =  CommandType.BUDGET;
    }

    @Override
    public void execute(StorageManager storageManager) {
        try {
            this.budgetAmount = extractAmount();
            DecimalFormat decimalFormat = new DecimalFormat("#0.00");
            storageManager.getWallet().getReceipts().setBudget(this.budgetAmount);
            String percentageExceeded = percentageOfBudgetExceeded(storageManager.getWalletExpenses());
            String percentageUsedUp = percentageOfBudgetUsedUp(storageManager.getWalletExpenses());
            this.infoCapsule.setCodeToast();
            this.infoCapsule.setOutputStr("Budget updated to: $" + decimalFormat.format(this.budgetAmount) + "\n"
                    + percentageExceeded + "\n"
                    + percentageUsedUp + "\n"
                    + "\n");

        } catch (DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(e.getMessage());
            return;
        }
    }

    private Double extractAmount() throws DukeException {
        String userBudget = Parser.parseForPrimaryInput(this.commandType, this.userInput);
        try {
            userBudget = userBudget.trim().replace("$", "");
            Double amount = Double.parseDouble(userBudget);
            if (amount <= 0) {
                throw new Exception();
            }
            return amount;
        } catch (Exception e) {
            throw new DukeException("Please kindly follow the format : budget $<amount> \n"
                    + "Please enter an amount greater than zero!\n");
        }
    }

    private String percentageOfBudgetExceeded(Double amountSpent) throws DukeException {
        try {
            if (amountSpent <= getBudgetAmount()) {
                return "You are still good and safe as you did not overspend your budget ;) \n";
            }

            Double percent = ((amountSpent - getBudgetAmount()) / getBudgetAmount()) * 100;
            Double percentage = roundByDecimalPlace(percent, 2);
            return "Percentage of Budget Exceeded :" + percentage.toString() + "%";
        } catch (Exception e) {
            throw new DukeException("Unable to calculate percentage overspent!\n");
        }
    }

    private String percentageOfBudgetUsedUp(Double amountSpent) throws DukeException {
        try {
            if (amountSpent <= getBudgetAmount()) {
                Double percent = (amountSpent / getBudgetAmount()) * 100;
                Double percentage = roundByDecimalPlace(percent, 2);
                return "Percentage of Budget Used Up : " + percentage.toString() + "%";
            }

            return "You have already exceeded your budget !! \n";

        } catch (Exception e) {
            throw new DukeException("Unable to calculate percentage of budget used up!\n");
        }
    }


    private double roundByDecimalPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private Double getBudgetAmount() {
        return budgetAmount;
    }
}
