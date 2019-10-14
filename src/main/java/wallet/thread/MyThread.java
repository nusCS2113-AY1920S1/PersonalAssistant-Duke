package wallet.thread;

import wallet.logic.command.ReminderCommand;
import wallet.model.record.Loan;
import wallet.model.record.LoanList;
import wallet.ui.Ui;

public class MyThread implements Runnable {

    // to stop the thread
    private boolean isExit;
    private Thread thread;
    private int timeInSeconds;
    private int counter;
    private LoanList loanList;
    private Ui ui;

    /**
     * Constructs a custom thread.
     *
     * @param isExit        A boolean variable to continue running thread/terminate.
     * @param loanList      The LoanList object.
     * @param timeInSeconds The time in seconds.
     */
    public MyThread(boolean isExit, LoanList loanList, int timeInSeconds) {
        this.isExit = isExit;
        this.timeInSeconds = timeInSeconds;
        this.loanList = loanList;
        this.ui = new Ui();
        thread = new Thread(this);
        //System.out.println("New thread: " + thread);
        thread.start(); // Starting the thread
    }


    /**
     * Executes the thread.
     */
    public void run() {
        int i = 0;
        while (!isExit) {
            i++;
            try {
                counter = 1;
                ui.printLine();
                System.out.println("Remember to settle your loans soon!");
                for (Loan l : loanList.getLoanList()) {
                    if (!l.getIsSettled()) {
                        System.out.println(counter + ". " + l.toString());
                    }
                    counter++;
                }
                ui.printLine();
                Thread.sleep(timeInSeconds * 1000);
            } catch (InterruptedException e) {
                ui.printLine();
                System.out.println("Stopping thread...");
                ui.printLine();
            }
        }
    }

    /**
     * Stops the thread.
     */
    public void stop() {
        thread.interrupt();
        isExit = true;
    }
}


