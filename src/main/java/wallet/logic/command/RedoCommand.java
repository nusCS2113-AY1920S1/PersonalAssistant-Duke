//@@author A0171206R
package wallet.logic.command;

import wallet.logic.LogicManager;
import wallet.model.Wallet;
import wallet.model.WalletList;

public class RedoCommand extends Command{

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Moving forward to later state...";
    public static final String MESSAGE_ALREADY_LATEST_STATE = "There is nothing more to be redone!";


    private WalletList walletList;
    private int newState;



    public RedoCommand() {
        this.walletList = LogicManager.getWalletList();
    }

    @Override
    public boolean execute(Wallet wallet) {
        if(walletList.getState() == walletList.getWalletList().size() - 1) {
            System.out.println(MESSAGE_ALREADY_LATEST_STATE);
            return false;
        }
        //System.out.println("In redo command");
        //System.out.println("Current state loan list:  " + walletList.getState());
        //Ui.printLoanTable(walletList.getWalletList().get(walletList.getState()).getLoanList().getLoanList());
        newState = walletList.getState() + 1;
        walletList.setState(newState);
        //System.out.println("new state loan list:  " + walletList.getState());
        //Ui.printLoanTable(walletList.getWalletList().get(walletList.getState()).getLoanList().getLoanList());

        LogicManager.getWalletList().getWalletList().get(LogicManager.getWalletList().getState()).getLoanList().setModified(true);
        LogicManager.getWalletList().getWalletList().get(LogicManager.getWalletList().getState()).getContactList().setModified(true);
        LogicManager.getWalletList().getWalletList().get(LogicManager.getWalletList().getState()).getBudgetList().setModified((true));
        LogicManager.getWalletList().getWalletList().get(LogicManager.getWalletList().getState()).getExpenseList().setModified(true);

        return false;
    }
}
