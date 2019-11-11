//@@author kyawtsan99

package planner.logic.command;

import java.util.Timer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import planner.main.CliLauncher;
import planner.util.legacy.schedule.ModTimer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReminderCommandTest {
    private String reminderMessage = "_______________________________\n"
            +
            "Please remember to update your module information!\n"
            +
            "To do so, you can input the update command in the following format:\n"
            +
            "update module\n";
    private Timer lastTimerElement = new Timer();

    @DisplayName("Testing TimerPool Array List")
    @Test
    public void reminderMessageTrue() throws InterruptedException {
        Timer time = new ModTimer();
        int timerPoolSize = CliLauncher.timerPool.size();
        lastTimerElement =  CliLauncher.timerPool.get(timerPoolSize - 1);
        assertEquals(lastTimerElement, time);
    }
}