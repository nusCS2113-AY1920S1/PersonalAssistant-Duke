//@@author kyawtsan99

package planner;

import planner.main.CliLauncher;

import java.util.Timer;

public class ModTimer extends Timer {
    public ModTimer() {
        super();
        CliLauncher.timerPool.add(this);
    }
}
