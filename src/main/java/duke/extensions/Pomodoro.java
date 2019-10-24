package duke.extensions;
import duke.ui.Ui;
import java.util.Timer;
import java.util.TimerTask;


public class Pomodoro {
    enum Cycle {
        LONG_BREAK,
        SHORT_BREAK,
        WORK
    }
    private int ONE_MINUTE = 1000 * 5;
    private int START_LONGBREAK_MINUTES = 15;
    private int START_SHORTBREAK_MINUTES = 5;
    private int START_WORK_MINUTES = 25;

    class pomodoroTimerTask extends TimerTask {
        Timer t;
        int minutesRemaining;

        pomodoroTimerTask(Timer t, int minutesRemaining) {
            this.minutesRemaining = minutesRemaining;
            this.t = t;
        }

        public void run() {
            minutesRemaining -= 1;
            System.out.println(minutesRemaining + " minutes left");

            if (minutesRemaining == 0) {
                System.out.println("Pomodoro completed");
                t.cancel();
            }
        }
    }

    public void workTimer(Ui ui) {
        Timer timer = new Timer();
        System.out.println("Work Started");
        timer.schedule(new pomodoroTimerTask(timer, START_WORK_MINUTES), ONE_MINUTE, ONE_MINUTE);
    }

    public void shortBreakTimer(Ui ui) {
        Timer timer = new Timer();
        System.out.println("Short break started");
        timer.schedule(new pomodoroTimerTask(timer, START_SHORTBREAK_MINUTES), ONE_MINUTE, ONE_MINUTE);
    }

    public void longBreakTimer(Ui ui) {
        Timer timer = new Timer();
        System.out.println("Long break started");
        timer.schedule(new pomodoroTimerTask(timer, START_LONGBREAK_MINUTES), ONE_MINUTE, ONE_MINUTE);
    }
}
