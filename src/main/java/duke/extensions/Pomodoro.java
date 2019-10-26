package duke.extensions;

import duke.task.Task;
import duke.ui.Ui;

import java.util.Timer;
import java.util.TimerTask;


public class Pomodoro {
    enum State {
        LONG_BREAK,
        SHORT_BREAK,
        WORK
    }

    private int ONE_MINUTE = 1000 * 5;
    private int START_LONGBREAK_MINUTES = 15;
    private int START_SHORTBREAK_MINUTES = 5;
    private int START_WORK_MINUTES = 25;

    State currState;
    int currCycle;
    Task pomodoroTask;
    Timer timer = new Timer();

    public Pomodoro() {
        currCycle = 0;
        currState = State.WORK;
    }

    private class pomodoroTimerTask extends TimerTask {
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

    public void startTimer(Ui ui) {
        switch (currState) {
            case WORK:
                System.out.println("Work Started");
          //      timer.schedule(new pomodoroTimerTask(timer, START_WORK_MINUTES), ONE_MINUTE, ONE_MINUTE);
                currCycle++;
                if (currCycle == 4) {
                    currState = State.LONG_BREAK;
                    currCycle = 0;
                } else {
                    currState = State.SHORT_BREAK;
                }
                break;
            case SHORT_BREAK:
                System.out.println("Short break started");
             //   timer.schedule(new pomodoroTimerTask(timer, START_SHORTBREAK_MINUTES), ONE_MINUTE, ONE_MINUTE);
                currState = State.WORK;
                break;
            case LONG_BREAK:
                System.out.println("Long break started");
           //     timer.schedule(new pomodoroTimerTask(timer, START_LONGBREAK_MINUTES), ONE_MINUTE, ONE_MINUTE);
                currState = State.WORK;
                break;
        }
    }

    public void resetState() {
        switch (currState) {
            case WORK:
                if (currCycle == 1) {
                    System.out.println("Back to long break cycle");
                    currState = State.LONG_BREAK;
                } else {
                    System.out.println("Back to short break cycle");
                    currState = State.SHORT_BREAK;
                }
                break;
            default:
                currState = State.WORK;
                System.out.println("Back to work cycle");
                break;
        }
    }

    //TODO pause features
    public void pauseTimer(Ui ui) {
        timer.cancel();
    }

    public void finishTimer(Ui ui) {
        timer.cancel();
    }

    public void setPomodoroTask(Task t) {
        this.pomodoroTask = t;
    }

    public Task getPomodoroTask() {
        return pomodoroTask;
    }

    public void getStatus() {

    }
}
