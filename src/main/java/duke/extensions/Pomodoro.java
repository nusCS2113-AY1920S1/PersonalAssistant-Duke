package duke.extensions;

import java.util.Timer;

import duke.task.Task;

/**
 * Class that holds the instance of a pomodoro and manages the pomodoro timer task
 */
public class Pomodoro {
    private static final int ONE_MINUTE = 1000; // multiply by 60
    private static final int START_LONGBREAK_MINUTES = 15;
    private static final int START_SHORTBREAK_MINUTES = 5;
    private static final int START_WORK_MINUTES = 25;
    private static final Pomodoro instance = new Pomodoro();
    private State currState;
    private int currCycle;
    private Task pomodoroTask;
    private PomodoroTimerTask pomodoroTimerTask;
    private Timer timer;

    /**
     * Constructor method that creates a pomodoro that begins with a work cycle
     */
    private Pomodoro() {
        currCycle = 0;
        currState = State.WORK;
    }

    /**
     * Method that holds an instance of the pomodoro timer
     *
     * @return an instance of Pomodoro
     */
    public static Pomodoro getInstance() {
        return instance;
    }

    /**
     * Method that starts the timer by creating a new PomodoroTimerTask
     * Inputs period of the TimerTask based on what cycle it is at currently
     */
    public void startTimer() {
        timer = new Timer();
        switch (currState) {
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
        default:
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
        }
    }

    /**
     * Method that resets the state to the previous one
     */
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

    /**
     * Method that stops the timer and prints out the state that was completed
     */

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

    /**
     * Method that restarts the pomodoro cycle back to the start work cycle
     */
    public void restartPomodoro() {
        System.out.println("Pomodoro restarted!");
        currCycle = 0;
        currState = State.WORK;
    }

    public Task getPomodoroTask() {
        return pomodoroTask;
    }

    public void setPomodoroTask(Task t) {
        this.pomodoroTask = t;
    }

    /**
     * Method that prints out the current status of the pomodoro
     * Current state, number of cycles completed and amount of time left on current pomodoro
     */
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

    /**
     * Enum for the 3 states of a pomodoro
     */
    enum State {
        LONG_BREAK,
        SHORT_BREAK,
        WORK
    }
}