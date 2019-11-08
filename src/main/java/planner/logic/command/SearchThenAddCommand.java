//@@author namiwa

package planner.logic.command;

import java.util.HashMap;
import java.util.HashSet;

import planner.credential.user.User;
import planner.logic.exceptions.legacy.ModCcaScheduleException;
import planner.logic.exceptions.legacy.ModException;
import planner.logic.exceptions.planner.ModNotFoundException;
import planner.logic.exceptions.planner.ModClashesException;
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
                System.out.println(cca);
                if (profile.getCcas().clashes(cca)) {
                    throw new ModCcaScheduleException();
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
                    if (arg("begin") != null) {
                        temp = new ModuleTask(upperModuleCode, mod, arg("begin"), arg("end"),
                                arg("dayOfWeek"));
                    } else {
                        temp = new ModuleTask(upperModuleCode, mod);
                    }
                    HashSet<TaskWithMultipleWeeklyPeriod> checkSet = profile.getModules().getSetModuleTask();
                    if (checkSet.contains(temp)) {
                        throw new ModClashesException();
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
