package wallet.ui;

import wallet.model.Wallet;
import wallet.model.record.Loan;
import wallet.model.record.LoanList;

class MyThread implements Runnable {

    // to stop the thread
    private boolean isExit;
    private Thread thread;
    private int timeInSeconds;
    private int counter;
    LoanList loanList;
    private Ui ui;

    MyThread(boolean isExit, LoanList loanList, int timeInSeconds) {
        this.isExit = isExit;
        this.timeInSeconds = timeInSeconds;
        this.loanList = loanList;
        this.ui = new Ui();
        thread = new Thread(this);
        //System.out.println("New thread: " + thread);
        thread.start(); // Starting the thread
    }


    // execution of thread starts from run() method
    public void run() {
        int i = 0;
        while (!isExit) {
            i++;
            try {
                counter = 1;
                ui.printLine();
                System.out.println("Remember to settle your loans soon!");
                for (Loan l : loanList.getLoanList()) {
                    if (!l.isSettled()) {
                        System.out.println(counter + ". " + l.toString());
                    }
                    counter++;
                }
                ui.printLine();
                Thread.sleep(timeInSeconds * 1000);
            } catch (InterruptedException e) {
                System.out.println("Stopping auto reminders...:");
            }
        }
    }

    // for stopping the thread
    public void stop(){
        thread.interrupt();
        isExit = true;
    }
}


