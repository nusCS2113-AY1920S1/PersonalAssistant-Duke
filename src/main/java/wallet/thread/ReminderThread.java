//@@author A0171206R

package wallet.thread;

import wallet.logic.LogicManager;
import wallet.model.record.Loan;
import wallet.model.record.LoanList;
import wallet.ui.Ui;

import java.util.ArrayList;

public class ReminderThread implements Runnable {

    private boolean autoRemind;
    private Thread thread;
    private int timeInSeconds;
    private int counter;
    private LoanList loanList;
    private Ui ui;

    /**
     * Constructs a ReminderThread object.
     *
     * @param autoRemind    Whether there is an auto reminder.
     * @param timeInSeconds The time in seconds.
     */
    public ReminderThread(boolean autoRemind, int timeInSeconds) {

        this.autoRemind = autoRemind;
        this.timeInSeconds = timeInSeconds;
        this.loanList = loanList;
        this.ui = new Ui();
        thread = new Thread(this);
        thread.start(); // Starting the thread
    }


    /**
     * Prints the lists of unsettled loans.
     */
    public void run() {
        while (LogicManager.getWallet().getLoanList().checkUnsettledLoan() && autoRemind) {
            try {
                ArrayList<Loan> loanList = LogicManager.getWallet().getLoanList().getLoanList();
                System.out.println("Reminder to settle your loans soon!");
                Ui.printLoanTableHeaders();
                for (Loan l : loanList) {
                    if (!l.getIsLend() && !l.getIsSettled()) {
                        System.out.printf("| %-4d |  %-7s  | %-40s | $%-7.2f | %-10s |   %-11s   | %-18s | %-19s |\n",
                                l.getId(), "No", l.getDescription(), l.getAmount(), l.getDate(), "Borrow from",
                                l.getPerson().getName(), l.getPerson().getPhoneNum());
                    } else if (l.getIsLend() && !l.getIsSettled()) {
                        System.out.printf("| %-4d |  %-7s  | %-40s | $%-7.2f | %-10s |   %-11s   | %-18s | %-19s |\n",
                                l.getId(), "No", l.getDescription(), l.getAmount(), l.getDate(), "Lend to",
                                l.getPerson().getName(), l.getPerson().getPhoneNum());
                    }
                }
                Ui.printLoanTableClose();
                Thread.sleep(timeInSeconds * 1000);
            } catch (InterruptedException e) {
                ui.printLine();
                System.out.println("Stopping thread...");
                ui.printLine();
            }
        }
        if (!LogicManager.getWallet().getLoanList().checkUnsettledLoan()) {
            thread.interrupt();
            LogicManager.getReminder().setAutoRemind(false);
            autoRemind = false;
            System.out.println("Turning off auto reminders because all loans have been settled!");
        }
    }

    /**
     * Stops the thread.
     */
    public void stop() {
        autoRemind = false;
        thread.interrupt();
    }
}


