package eggventory.logic.commands.add;

import eggventory.commons.enums.CommandType;
import eggventory.logic.QuantityManager;
import eggventory.logic.commands.Command;
import eggventory.model.LoanList;
import eggventory.model.PersonList;
import eggventory.model.StockList;
import eggventory.model.items.Stock;
import eggventory.storage.Storage;
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

    /**
     * Determines if this Stock exists, checked by the stockCode.
     * @param list the stockList being checked.
     * @return true if the stock exists inside the list.
     */
    private boolean stockExists(StockList list) {
        if (list.findStock(stockCode) == null) {
            return false;
        }

        return true;
    }

    private boolean personExists() {
        if (PersonList.findPerson(matricNo) == -1) {
            return false;
        }
        return true;
    }

    private boolean sufficientStock(StockList list) {
        if (list.getStockQuantity(stockCode) - LoanList.getStockLoanedQuantity(stockCode) - quantity < 0) {
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
    @Override
    public String execute(StockList list, Ui ui, Storage storage) {
        String output = "";
        if (!personExists()) {
            output += String.format("Sorry, the person with matric number \"%s\" does not exist!", matricNo);

        } else if (!stockExists(list)) {
            output += String.format("Sorry, that stock with StockCode \"%s\" does not exist!", stockCode);

        } else if (!sufficientStock(list)) {
            output = ("OOPS there is insufficient stock to loan out!");

        } else {
            LoanList.addLoan(matricNo, stockCode, quantity);

            Stock stock = list.findStock(stockCode);

            String personName = PersonList.getName(matricNo);
            String stockDescription = stock.getDescription();
            storage.save(list);

            output = (String.format("Nice, I have added this loan for you: \n"
                    + "Person: %s | Stock: %s | Quantity: %d", personName, stockDescription, quantity));

            output += QuantityManager.checkMinimum(stock);
        }

        ui.print(output);

        return output;
    }

    // Used by load function
    public void executeLoadLoanList(LoanList savedLoanList) {
        savedLoanList.addLoan(matricNo, stockCode, quantity);
    }

}
