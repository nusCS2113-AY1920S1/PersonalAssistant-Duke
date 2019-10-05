package seedu.hustler.ui.timer;

public class timerManager {

    protected timer countdownTimer;
    protected static Thread countdownThread;

    public timerManager() {
	    countdownTimer = new timer();
            countdownThread = new Thread(countdownTimer);
    }

    public void setTimer(String time) {
        String[] timeParts = time.split(" ");
        countdownTimer = new timer(timeParts[0], timeParts[1], timeParts[2]);
    }

    public void startTimer() {
        countdownThread = new Thread(countdownTimer);
        countdownThread.start();
    }

    public static void stopTimer() {
            countdownThread.interrupt();
    }
}
