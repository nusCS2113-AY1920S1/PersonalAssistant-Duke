package eggventory.logic.commands.add;

import eggventory.model.LoanList;
import eggventory.model.StockList;
import eggventory.storage.Storage;
import eggventory.logic.commands.Command;
import eggventory.commons.enums.CommandType;
import eggventory.ui.Ui;

//@@author cyanoei
public class AddLoanCommand extends Command {

    private String stockCode;
    private String matricNo;
    private int quantity;

    /**
     * Constructor for addLoanCommand.
     * @param type the type of command.
     * @param stockCode the stockCode of the loan to be added.
     * @param matricNo the matric number of the person making the loan.
     * @param quantity the quantity loaned.
     */
    public AddLoanCommand(CommandType type, String matricNo, String stockCode, int quantity) {
        super(type);
        this.stockCode = stockCode;
        this.matricNo = matricNo;
        this.quantity = quantity;
    }

    private boolean stockExists() {
        if (LoanList.getStockLoanedQuantity(stockCode) == -1) {
            return false;
        }

        return true;
    }

    private boolean sufficientStock() {
        if (LoanList.getStockLoanedQuantity(stockCode) - quantity < 0) {
            return false;
        }
        return true;
    }

    /**
     * Method to execute the AddLoanCommand.
     * @param list the stockList.
     * @param ui the ui for printing to cli/gui.
     * @param storage the storage.
     * @return the print string for assertion in testing.
     */
    public String execute(StockList list, Ui ui, Storage storage) {
        String output = "";
        if (!stockExists()) {
            output += "OOPS that stock does not exist!";
        } else if (!sufficientStock()) {
            output = ("OOPS there is insufficient stock to loan out!");
        } else {
            LoanList.addLoan(stockCode, matricNo, quantity);
            output = (String.format("Nice, I have added this loan for you: \n"
                    + "Stock: %s | Person: %s | Quantity: %d", stockCode, matricNo, quantity));
        }

        ui.print(output);

        return output;
    }
}
