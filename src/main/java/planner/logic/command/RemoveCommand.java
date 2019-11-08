//@@author namiwa

package planner.logic.command;

import planner.credential.user.User;
import planner.logic.exceptions.legacy.ModEmptyListException;
import planner.logic.exceptions.legacy.ModException;
import planner.logic.exceptions.legacy.ModOutOfBoundException;
import planner.logic.modules.legacy.task.TaskWithMultipleWeeklyPeriod;
import planner.util.crawler.JsonWrapper;
import planner.ui.cli.PlannerUi;
import planner.util.storage.Storage;
import java.util.HashMap;

import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTask;

public class RemoveCommand extends ModuleCommand {

    public RemoveCommand(Arguments args) {
        super(args);
    }

    @Override
    public void execute(HashMap<String, ModuleInfoDetailed> detailedMap,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper,
                        User profile) throws ModException {
        int index = arg("index", Integer.class) - 1;
        switch (arg("toRemove")) {
            case "cca": {
                if (profile.getCcas().size() == 0) {
                    throw new ModEmptyListException("ccas");
                }
                if (index < 0 || index >= profile.getCcas().size()) {
                    throw new ModOutOfBoundException();
                }
                TaskWithMultipleWeeklyPeriod delCca = profile.getCcas().get(index);
                plannerUi.deleteMsg(delCca);
                profile.getCcas().remove(index);
                break;
            }

            case "module":
            default: {
                if (index < 0 || index >= profile.getModules().size() || profile.getModules().isEmpty()) {
                    throw new ModOutOfBoundException();
                }
                ModuleTask delMod = profile.getModules().get(index);
                plannerUi.deleteMsg(delMod);
                profile.getModules().remove(index);
                break;
            }
        }
    }
}
