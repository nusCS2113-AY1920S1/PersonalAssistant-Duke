//@@author namiwa

package planner.logic.command;

import java.util.HashMap;
import java.util.HashSet;

import planner.logic.exceptions.legacy.ModCcaScheduleException;
import planner.logic.exceptions.legacy.ModException;
import planner.logic.exceptions.planner.ModNotFoundException;
import planner.logic.exceptions.planner.ModClashesException;
import planner.logic.modules.cca.Cca;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTask;
import planner.logic.modules.cca.CcaList;
import planner.util.crawler.JsonWrapper;
import planner.ui.cli.PlannerUi;
import planner.util.storage.Storage;
import planner.logic.modules.module.ModuleTasksList;

public class SearchThenAddCommand extends ModuleCommand {

    public SearchThenAddCommand(Arguments args) {
        super(args);
    }

    @Override
    public void execute(HashMap<String, ModuleInfoDetailed> detailedMap,
                        ModuleTasksList tasks,
                        CcaList ccas,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper) throws ModException {
        switch (arg("toAdd")) {
            case ("cca"): {
                Cca cca = new Cca(arg("name"), arg("begin"), arg("end"), arg("dayOfWeek"));
                if (ccas.clashes(cca)) {
                    throw new ModCcaScheduleException();
                }
                ccas.add(cca);
                plannerUi.addedMsg(cca);
                break;
            }

            case ("module"):
            default: {
                String moduleCode = arg("moduleCode");
                String upperModuleCode = moduleCode.toUpperCase().trim();
                if (detailedMap.containsKey(upperModuleCode)) {
                    ModuleInfoDetailed mod = detailedMap.get(upperModuleCode);
                    ModuleTask temp = new ModuleTask(upperModuleCode, mod);
                    HashSet<ModuleTask> checkSet = tasks.getSetModuleTask();
                    if (checkSet.contains(temp)) {
                        throw new ModClashesException();
                    }
                    tasks.getTasks().add(temp);
                    plannerUi.addedMsg(temp);
                    jsonWrapper.storeTaskListAsJson(tasks.getTasks(), store);
                } else {
                    throw new ModNotFoundException();
                }
                break;
            }
        }
    }
}
