//@@author namiwa

package planner.logic.command;

import java.util.HashMap;
import java.util.HashSet;

import planner.credential.user.User;
import planner.logic.exceptions.legacy.ModScheduleException;
import planner.logic.exceptions.legacy.ModException;
import planner.logic.exceptions.planner.ModNotFoundException;
import planner.logic.exceptions.planner.ModSameModuleException;
import planner.logic.modules.cca.Cca;
import planner.logic.modules.legacy.task.TaskWithMultipleWeeklyPeriod;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTask;
import planner.util.crawler.JsonWrapper;
import planner.ui.cli.PlannerUi;
import planner.util.storage.Storage;

public class SearchThenAddCommand extends ModuleCommand {

    public SearchThenAddCommand(Arguments args) {
        super(args);
    }

    @Override
    public void execute(HashMap<String, ModuleInfoDetailed> detailedMap,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper,
                        User profile) throws ModException {
        switch (arg("toAdd")) {
            case ("cca"): {
                Cca cca = new Cca(arg("name"), arg("begin"), arg("end"), arg("dayOfWeek"));
                if (profile.getAllTasks().clashes(cca)) {
                    throw new ModScheduleException("CCA");
                }
                profile.getCcas().add(cca);
                plannerUi.addedMsg(cca);
                break;
            }

            case ("module"):
            default: {
                String moduleCode = arg("moduleCode");
                String upperModuleCode = moduleCode.toUpperCase().trim();
                if (detailedMap.containsKey(upperModuleCode)) {
                    ModuleInfoDetailed mod = detailedMap.get(upperModuleCode);
                    ModuleTask temp;
                    if (!profile.getModules().findExact(upperModuleCode).isEmpty()) {
                        throw new ModSameModuleException();
                    }
                    if (arg("begin") != null) {
                        temp = new ModuleTask(upperModuleCode, mod, arg("begin"), arg("end"),
                                arg("dayOfWeek"));
                    } else {
                        temp = new ModuleTask(upperModuleCode, mod);
                    }
                    if (profile.getAllTasks().clashes(temp)) {
                        throw new ModScheduleException();
                    }
                    profile.getModules().add(temp);
                    plannerUi.addedMsg(temp);
                } else {
                    throw new ModNotFoundException();
                }
                break;
            }
        }
    }
}
