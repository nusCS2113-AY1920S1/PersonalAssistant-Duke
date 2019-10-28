package reminder;

import javafx.application.Platform;

import java.util.Timer;
import java.util.TimerTask;

class RemindTask extends TimerTask {
    Timer timer;

    public RemindTask(Timer t) {
        timer = t;
    }

    public void run() {
        //retrieve word list and their meanings here
        Platform.runLater(() -> {
            new ReminderPopup();
        });
        System.out.println("Time up");
        timer.cancel(); //Terminate the timer thread
    }
}