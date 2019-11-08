//@@author namiwa

package planner.logic.command;

import java.util.HashMap;
import java.util.Timer;

import planner.credential.user.User;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.main.CliLauncher;
import planner.util.crawler.JsonWrapper;
import planner.ui.cli.PlannerUi;
import planner.util.storage.Storage;

public class EndCommand extends ModuleCommand {

    @Override
    public void execute(
            HashMap<String, ModuleInfoDetailed> detailedMap,
            PlannerUi plannerUi,
            Storage store,
            JsonWrapper jsonWrapper,
            User profile) {
        killAllTimers();
        plannerUi.goodbyeMsg();
        //System.exit(0); // Causes test cases to throw exceptions
        //Runtime.getRuntime().halt(0); //Forced kill
    }

    private void killAllTimers() {
        for (Timer timer: CliLauncher.timerPool) {
            timer.cancel();
        }
    }
}
