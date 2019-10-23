package planner.command;

import java.util.HashMap;
import java.util.HashSet;

import planner.exceptions.original.ModCcaScheduleException;
import planner.exceptions.original.ModException;
import planner.exceptions.planner.ModClashesException;
import planner.exceptions.planner.ModNotFoundException;
import planner.modules.inherited.Cca;
import planner.modules.data.ModuleInfoDetailed;
import planner.modules.data.ModuleTask;
import planner.util.periods.CcaList;
import planner.util.commons.JsonWrapper;
import planner.util.commons.PlannerUi;
import planner.util.commons.Storage;
import planner.util.commons.ModuleTasksList;

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
