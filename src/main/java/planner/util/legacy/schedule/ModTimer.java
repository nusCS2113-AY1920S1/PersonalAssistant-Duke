//@@author kyawtsan99

package planner.util.legacy.schedule;

import planner.main.CliLauncher;

import java.util.Timer;

public class ModTimer extends Timer {
    public ModTimer() {
        super();
        CliLauncher.timerPool.add(this);
    }
}
