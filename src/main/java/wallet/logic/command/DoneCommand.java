package wallet.logic.command;

import wallet.logic.LogicManager;
import wallet.model.Wallet;
import wallet.model.record.Loan;
import wallet.ui.Ui;

/**
 * The DoneCommand sets the isSettled attribute in
 * the Loan object to be true.
 */
public class DoneCommand extends Command {
    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_SUCCESS_DONE = "This loan is now settled:";
    public static final String MESSAGE_FAILURE_DONE = "There is an error in settling the loan.";
    public static final String MESSAGE_FAILURE_NO_SUCH_ID = "There is no such id in loans!";
    public static final String MESSAGE_FAILURE_ALREADY_DONE = "The loan has already been settled!";

    private int id;

    /**
     * Constructs a DoneCommand object.
     *
     * @param id The id of the Loan object.
     */
    public DoneCommand(int id) {
        this.id = id;
    }

    /**
     * Lists the Record objects in any list and returns false.
     *
     * @param wallet The Wallet object.
     * @return false.
     */
    @Override
    public boolean execute(Wallet wallet) {
        try {
            Loan loan = wallet.getLoanList().findLoanWithId(id);
            if (loan != null) {
                if (loan.getIsSettled()) {
                    System.out.println(MESSAGE_FAILURE_ALREADY_DONE);
                    return false;
                }
                loan.setIsSettled(true);
            } else {
                System.out.println(MESSAGE_FAILURE_NO_SUCH_ID);
                return false;
            }
            wallet.getLoanList().setModified(true);
            System.out.println(MESSAGE_SUCCESS_DONE);
            Ui.printLoanTableHeaders();
            Ui.printLoanRow(loan);
            Ui.printLoanTableClose();
            if (!LogicManager.getWallet().getLoanList().checkUnsettledLoan()) {
                LogicManager.getReminder().autoRemindStop();
                System.out.println("Great! All loans have been settled!");
            }
        } catch (Exception e) {
            System.out.println(MESSAGE_FAILURE_DONE);
        }
        return false;
    }
}
