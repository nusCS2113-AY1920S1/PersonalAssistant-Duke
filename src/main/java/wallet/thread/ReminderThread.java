package wallet.thread;

import wallet.logic.LogicManager;
import wallet.model.record.Loan;
import wallet.model.record.LoanList;
import wallet.ui.Ui;

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
     * @param loanList      The LoanList object.
     * @param timeInSeconds The time in seconds.
     */
    public ReminderThread(boolean autoRemind, LoanList loanList, int timeInSeconds) {

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
                counter = 1;
                ui.printLine();
                System.out.println("Remember to settle your loans soon!");
                for (Loan l : loanList.getLoanList()) {
                    if (!l.getIsSettled()) {
                        System.out.println(counter + ". " + l.toString());
                        counter++;
                    }
                }
                ui.printLine();
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


