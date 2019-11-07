package planner.util.legacy.schedule;

import java.util.TimerTask;
import planner.ui.cli.PlannerUi;

public class ScheduledTask extends TimerTask {

    public void run() {
        new PlannerUi().reminderMsg();
    }
}
