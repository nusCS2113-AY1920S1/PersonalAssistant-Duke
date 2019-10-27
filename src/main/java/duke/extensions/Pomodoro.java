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

    private int ONE_MINUTE = 1000; // multiply by 60
    private int START_LONGBREAK_MINUTES = 15;
    private int START_SHORTBREAK_MINUTES = 5;
    private int START_WORK_MINUTES = 25;

    State currState;
    int currCycle;
    Task pomodoroTask;
    PomodoroTimerTask pomodoroTimerTask;
    Timer timer;

    private static final Pomodoro instance = new Pomodoro();

    private Pomodoro() {
        currCycle = 0;
        currState = State.WORK;
    }

    public static Pomodoro getInstance() {
        return instance;
    }

    public void startTimer() {
        timer = new Timer();
        switch (currState) {
            case WORK:
                System.out.println("Work Started");
                pomodoroTimerTask = new PomodoroTimerTask(timer, START_WORK_MINUTES);
                timer.schedule(pomodoroTimerTask, ONE_MINUTE, ONE_MINUTE);
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
                pomodoroTimerTask = new PomodoroTimerTask(timer, START_SHORTBREAK_MINUTES);
                timer.schedule(pomodoroTimerTask, ONE_MINUTE, ONE_MINUTE);
                currState = State.WORK;
                break;
            case LONG_BREAK:
                System.out.println("Long break started");
                pomodoroTimerTask = new PomodoroTimerTask(timer, START_LONGBREAK_MINUTES);
                timer.schedule(pomodoroTimerTask, ONE_MINUTE, ONE_MINUTE);
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

    public void stopTimer() {
        String cState;
        switch (currState) {
            case LONG_BREAK:
            case SHORT_BREAK:
                cState = "Work";
                break;
            case WORK:
                cState = "Break";
                break;
            default:
                cState = "pomodorodododod";
        }

        System.out.println(cState + " has finished!");
        timer.cancel();
    }

    public void restartPomodoro() {
        System.out.println("Pomodoro restarted!");
        currCycle = 0;
        currState = State.WORK;
    }

    public void setPomodoroTask(Task t) {
        this.pomodoroTask = t;
    }

    public Task getPomodoroTask() {
        return pomodoroTask;
    }

    public void getStatus() {
        int minutesRemaining = pomodoroTimerTask.getMinutesRemaining();
        String cState;
        switch (currState) {
            case LONG_BREAK:
            case SHORT_BREAK:
                cState = "Work";
                break;
            case WORK:
                cState = "Break";
                break;
            default:
                cState = "pomodorodododod";
        }

        System.out.println("Current state: " + cState);
        System.out.println("Number of Pomodoro cycles: " + currCycle);
        System.out.println(minutesRemaining + " minutes left for your current pomodoro");
    }
}
