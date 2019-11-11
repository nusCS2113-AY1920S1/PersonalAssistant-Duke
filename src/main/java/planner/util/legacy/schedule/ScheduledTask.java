//@@author kyawtsan99

package planner.util.legacy.schedule;

import java.util.TimerTask;
import planner.ui.cli.PlannerUi;

public class ScheduledTask extends TimerTask {

    private PlannerUi plannerUi;

    public ScheduledTask(PlannerUi plannerUi) {
        super();
        this.plannerUi = plannerUi;
    }

    public void run() {
        plannerUi.reminderMsg();
    }
}
