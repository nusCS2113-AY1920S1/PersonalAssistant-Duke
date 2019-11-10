//@@author matthewng1996

package wallet.thread;

import wallet.logic.command.CurrencyCommand;
import wallet.model.Wallet;
import wallet.model.currency.Currency;
import wallet.model.record.Expense;
import wallet.model.record.Loan;

public class CurrencyThread implements Runnable {
    private Wallet wallet;
    private Thread thread;
    private String givenCountry;
    private boolean isExit = false;

    /**
     * Custom thread.
     */
    public CurrencyThread(Wallet walletParam, String type) {
        thread = new Thread(this);
        thread.start();
        wallet = walletParam;
        givenCountry = type;
    }

    /**
     * Runs the threat and stops when pie chart is completed.
     */
    @Override
    public void run() {
        for (Currency c : wallet.getCurrencyList().getCurrencyList()) {
            if (c.getCountry().toLowerCase().equals(givenCountry)) {
                updateExpenseAndLoans(wallet, c);
                System.out.println(CurrencyCommand.MESSAGE_SUCCESSFUL_CONVERSION + givenCountry);
                isExit = true;
            }
        }
        if (!isExit) {
            System.out.println(CurrencyCommand.MESSAGE_NO_CURRENCY + givenCountry);
        }
        stop();
    }

    /**
     * Stops the threat from running.
     */
    public void stop() {
        thread.interrupt();
    }

    /**
     * This method updates the amount in expenses and loans but does not save it.
     * @param wallet Wallet object
     * @param newCurrency newly chosen currency for conversion
     */
    public void updateExpenseAndLoans(Wallet wallet, Currency newCurrency) {
        if (newCurrency != null) {
            for (Expense e : wallet.getExpenseList().getExpenseList()) {
                e.setAmount(e.getAmount()
                        / wallet.getCurrencyList().getCurrentCurrency().getValue()
                        * newCurrency.getValue());
            }

            for (Loan l : wallet.getLoanList().getLoanList()) {
                l.setAmount(l.getAmount()
                        / wallet.getCurrencyList().getCurrentCurrency().getValue()
                        * newCurrency.getValue());
            }
            wallet.getCurrencyList().getCurrentCurrency().setCurrentCurrency(false);
            newCurrency.setCurrentCurrency(true);
            wallet.getCurrencyList().setCurrentCurrency(newCurrency);
        }
    }
}
