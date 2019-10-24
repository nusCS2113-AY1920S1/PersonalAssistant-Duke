//@@author A0171206R

package wallet.logic.command;

import wallet.logic.LogicManager;
import wallet.model.Wallet;
import wallet.model.WalletList;
import wallet.ui.Ui;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    private WalletList walletList;
    private int newState;

    public UndoCommand() {
        this.walletList = LogicManager.getWalletList();
    }

    @Override
    public boolean execute(Wallet wallet) {
        System.out.println("In undo command");
        System.out.println("Current state loan list:  " + walletList.getState());
        Ui.printLoanTable(walletList.getWalletList().get(walletList.getState()).getLoanList().getLoanList());
        newState = walletList.getState() - 1;
        walletList.setState(newState);
        System.out.println("new state loan list:  " + walletList.getState());
        Ui.printLoanTable(walletList.getWalletList().get(walletList.getState()).getLoanList().getLoanList());
       // Ui.printContactTable(walletList.getWalletList().get(walletList.getState()).getContactList().getContactList());
       // Ui.printExpenseTable(walletList.getWalletList().get(walletList.getState()).getExpenseList().getExpenseList());
        LogicManager.setWallet(walletList.getWalletList().get(newState));
        LogicManager.getWallet().getLoanList().setModified(true);
        LogicManager.getWallet().getContactList().setModified(true);
        LogicManager.getWallet().getBudgetList().setModified((true));
        LogicManager.getWallet().getExpenseList().setModified(true);
        return false;
    }
}
