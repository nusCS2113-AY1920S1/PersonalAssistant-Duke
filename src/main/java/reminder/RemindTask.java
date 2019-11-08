package reminder;

import javafx.application.Platform;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

class RemindTask extends TimerTask {
    Timer timer;
    ArrayList<String> wordArrayList;

    public RemindTask(Timer t, ArrayList<String> wordList) {
        timer = t;
        wordArrayList = wordList;
    }

    public void run() {
        //retrieve word list and their meanings here
        Platform.runLater(() -> {
            new ReminderPopup(wordArrayList);
        });
        timer.cancel(); //Terminate the timer thread
    }
}