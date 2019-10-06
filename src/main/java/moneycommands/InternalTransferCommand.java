package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Parser;
import controlpanel.Ui;
import money.Account;
import money.BankTracker;

import java.text.ParseException;
import java.time.LocalDate;

public class InternalTransferCommand extends MoneyCommand {

    private boolean add;
    private String description;
    private double amt;
    private LocalDate date;

    public InternalTransferCommand(String inputString) throws ParseException {
        String status = inputString.split(" ")[0];
        if (status.equals("deposit")) {
            add = true;
            inputString = inputString.replaceFirst("deposit ", "");
        } else if (status.equals("withdraw")) {
            add = false;
            inputString = inputString.replaceFirst("withdraw ", "");
        }

        amt = Double.parseDouble(inputString.split(" ")[0]);
        date = Parser.shortcutTime(inputString.split(" /at ")[1]);
        String temp = inputString.split(" /at ")[0];
        description = temp.split(" ", 2)[1];
    }

    /**
     * This method labels whether this command means ceasing the overall program.
     * @return this command will not cease the overall program.
     */
    @Override
    public boolean isExit() {
        return false;
    }


    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        BankTracker bankTracker = account.findTrackerByName(description);
        if (add) {
            bankTracker.addAmt(amt);
        } else {
            if (bankTracker.getAmt() < amt) {
                throw new DukeException("Sorry, FG only allow non-zero balance. Here is the account info: \n"
                        + bankTracker.getBankAccountInfo());
            }
            bankTracker.addAmt(0-amt);
        }
        bankTracker.updateDate(date);
        ui.appendToOutput("  Got it. Here is the current information about this account:\n    "
                + bankTracker.getBankAccountInfo() + "\n");
    }
}
