package reminder;

import javafx.application.Platform;
import storage.Storage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

class RemindTask extends TimerTask {
    Timer timer;
    ArrayList<String> wordArrayList;
    String reminderInfo;

    public RemindTask(Timer t, ArrayList<String> wordList, String remindInfo) {
        timer = t;
        wordArrayList = wordList;
        reminderInfo = remindInfo;
    }

    public void run() {
        Platform.runLater(() -> {
            new ReminderPopup(wordArrayList, reminderInfo);
        });
        timer.cancel(); //Terminate the timer thread
    }
}