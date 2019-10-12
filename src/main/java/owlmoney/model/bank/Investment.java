package owlmoney.model.bank;

import owlmoney.model.transaction.Transaction;
import owlmoney.model.transaction.TransactionList;
import owlmoney.ui.Ui;

public class Investment extends Bank {

    private static final String INVESTMENT = "investment";

    /**
     * Creates an instance of an investment account.
     *
     * @param name          The name of the bank account.
     * @param currentAmount The current amount of money in  the bank account.
     */
    public Investment(String name, double currentAmount) {
        super(name, currentAmount);
        this.type = INVESTMENT;
        this.transactions = new TransactionList();
    }


    @Override
    public void addInExpenditure(Transaction exp, Ui ui) {
        if (exp.getAmount() > this.getCurrentAmount()) {
            ui.printError("Bank account cannot have a negative amount");
        } else {
            transactions.addExpenditureToList(exp, ui);
            deductFromAmount(exp.getAmount());
        }
    }

    @Override
    public void deleteExpenditure(int exId, Ui ui) {
        addToAmount(transactions.deleteExpenditureFromList(exId, ui));
    }
}
