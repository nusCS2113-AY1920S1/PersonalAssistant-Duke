package reminder;

import scene.ReminderScene;

import java.util.Timer;
import java.util.TimerTask;

class RemindTask extends TimerTask {
    Timer timer;

    RemindTask(Timer t) {
        timer = t;
    }

    public void run() {
        //retrieve word list and their meanings here
        new ReminderScene();
        System.out.println("Time up");
        timer.cancel(); //Terminate the timer thread
    }
}