//@@author A0171206R

package wallet.logic.command;

import wallet.logic.LogicManager;
import wallet.model.Wallet;
import wallet.model.WalletList;
import wallet.ui.Ui;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Reverting back to previous state...";
    public static final String MESSAGE_ALREADY_INITIAL_STATE = "There is nothing more to be undone!";

    private WalletList walletList;
    private int newState;

    public UndoCommand() {
        this.walletList = LogicManager.getWalletList();
    }

    @Override
    public boolean execute(Wallet wallet) {

        if (walletList.getState() == 0) {
            Ui.printError(MESSAGE_ALREADY_INITIAL_STATE);
            return false;
        }

        newState = walletList.getState() - 1;
        walletList.setState(newState);

        LogicManager.getWalletList().getWalletList().get(LogicManager.getWalletList().getState())
                .getLoanList().setModified(true);

        LogicManager.getWalletList().getWalletList().get(LogicManager.getWalletList().getState())
                .getContactList().setModified(true);

        LogicManager.getWalletList().getWalletList().get(LogicManager.getWalletList().getState())
                .getBudgetList().setModified((true));

        LogicManager.getWalletList().getWalletList().get(LogicManager.getWalletList().getState())
                .getExpenseList().setModified(true);

        System.out.println(MESSAGE_SUCCESS);
        return false;
    }
}
