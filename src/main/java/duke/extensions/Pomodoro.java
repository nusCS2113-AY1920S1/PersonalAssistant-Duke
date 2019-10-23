package duke.extensions;
import java.util.Timer;


public class Pomodoro {

    enum Cycle {
        LONG_BREAK,
        SHORT_BREAK,
        WORK
    }

    private int LONGBREAK = 15;
    private int SHORTBREAK = 5;
    private int WORK = 25;


    public void pomodoroTimer() {
        Timer timer = new Timer();
    }
}
