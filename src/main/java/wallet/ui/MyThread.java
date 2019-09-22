package wallet.ui;

import wallet.model.record.RecordList;

class MyThread implements Runnable {

    // to stop the thread
    private boolean exit;

    private String name;
    Thread thread;

    MyThread(boolean exit, RecordList recordList) {
        this.exit = exit;
        thread = new Thread(this);
        System.out.println("New thread: " + thread);
        thread.start(); // Starting the thread
    }


    // execution of thread starts from run() method
    public void run() {
        int i = 0;
        while (!exit) {
            System.out.println(name + ": " + i);
            i++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Caught:" + e);
            }
        }
        System.out.println(name + " Stopped.");
    }

    // for stopping the thread
    public void stop() {
        exit = true;
    }
}


