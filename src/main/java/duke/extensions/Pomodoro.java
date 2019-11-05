package duke.extensions;

import java.util.ArrayList;
import java.util.Timer;

import duke.exception.DukeException;
import duke.task.Task;

/**
 * Enum for the 3 states of a pomodoro
 */
enum State {
    LONG_BREAK,
    SHORT_BREAK,
    WORK
}

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
    private ArrayList<Task> pomodoroTaskList = new ArrayList<>();
    private Timer timer;
    private PomodoroTimerTask pomodoroTimerTask;
    private Brainteasers brainTeasers = new Brainteasers();
    private String question = "none";

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
     * Checks whether timer has started in the first place by checking pomodoroTimerTask minutes remaining
     * After timer has started, changes the cycle to the next cycle
     */
    public void startTimer() throws DukeException {
        if (pomodoroTimerTask != null && pomodoroTimerTask.getMinutesRemaining() > 0) {
            throw new DukeException("A timer has already started, please end that one first before starting a " +
                    "new timer!");
        }
        timer = new Timer();
        switch (currState) {
        case SHORT_BREAK:
            System.out.println("Pomodoro Short break started!");
            System.out.println("-------------------Here is a brain teaser for you to ponder on-------------------");
            question = brainTeasers.getRandom();
            System.out.println(question);
            System.out.println("----------------------Type 'pomo answer' to get the answer!----------------------");
            pomodoroTimerTask = new PomodoroTimerTask(timer, START_SHORTBREAK_MINUTES);
            timer.schedule(pomodoroTimerTask, ONE_MINUTE, ONE_MINUTE);
            currState = State.WORK;
            break;
        case LONG_BREAK:
            System.out.println("Pomodoro Long break started!");
            System.out.println("-------------------Here is a brain teaser for you to ponder on-------------------");
            question = brainTeasers.getRandom();
            System.out.println(question);
            System.out.println("----------------------Type 'pomo answer' to get the answer!----------------------");
            pomodoroTimerTask = new PomodoroTimerTask(timer, START_LONGBREAK_MINUTES);
            timer.schedule(pomodoroTimerTask, ONE_MINUTE, ONE_MINUTE);
            currState = State.WORK;
            break;
        default:
            System.out.println("Pomodoro Work Started!");

            System.out.println("---------------You have the following tasks to complete this pomodoro-----" +
                    "----------");
            if (pomodoroTaskList.size() == 0) {
                System.out.println("Guess you are a free man, or would you like to assign some tasks to your" +
                        " pomodoro? \ntype 'pomo assign INDEX'");
            } else {
                for (int i = 0; i < pomodoroTaskList.size(); i++) {
                    Task temp = pomodoroTaskList.get(i);
                    System.out.println(temp.getDescription());
                }
            }
            System.out.println("--------------------------------  Stay focused fam  ----------------------" +
                    "----------");
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
     * Method to get the answer from the brainteaser class
     *
     * @return the answer to the question that was posted
     */
    public String getAnswer() {
        return brainTeasers.getAnswer(question);
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
        pomodoroTimerTask.setMinutesRemaining(0);
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


    public void setPomodoroTask(Task t) {
        pomodoroTaskList.add(t);
        System.out.println("You have successfully assigned this task to your pomodoro:");
        System.out.println(t.getDescription());
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
}
