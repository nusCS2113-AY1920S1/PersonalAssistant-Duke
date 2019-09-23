package wallet.ui;

import wallet.model.Wallet;
import wallet.model.record.Loan;
import wallet.model.record.LoanList;

class MyThread implements Runnable {

    // to stop the thread
    private boolean exit;
    private Thread thread;
    private int timeInSeconds;
    private int counter;
    private Ui ui;
    private Wallet wallet;

    MyThread(boolean exit, LoanList loanList, int timeInSeconds) {
        this.exit = exit;
        this.timeInSeconds = timeInSeconds;
        thread = new Thread(this);
        System.out.println("New thread: " + thread);
        thread.start(); // Starting the thread
    }


    // execution of thread starts from run() method
    public void run() {
        int i = 0;
        while (!exit) {
            System.out.println(i);
            i++;
            try {
                Thread.sleep(timeInSeconds * 1000);
                counter = 1;
                ui.printLine();
                System.out.println("Remember to settle your loans soon!");
                for (Loan l : wallet.getLoanList().getLoanList()) {
                    if (!l.isSettled()) {
                        System.out.println(counter + ". " + l.toString());
                    }
                    counter++;
                }
                ui.printLine();
            } catch (InterruptedException e) {
                System.out.println("Caught:" + e);
            }
        }
        System.out.println("Stopped.");
    }

    // for stopping the thread
    public void stop() {
        exit = true;
    }
}


