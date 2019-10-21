package planner.command.logic;

import java.util.HashMap;

import planner.exceptions.ModCcaScheduleException;
import planner.exceptions.ModException;
import planner.exceptions.planner.ModNotFoundException;
import planner.modules.Cca;
import planner.modules.data.ModuleInfoDetailed;
import planner.modules.data.ModuleTask;
import planner.util.CcaList;
import planner.util.JsonWrapper;
import planner.util.PlannerUi;
import planner.util.Storage;
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
                if (detailedMap.containsKey(moduleCode)) {
                    ModuleInfoDetailed mod = detailedMap.get(moduleCode);
                    ModuleTask temp = new ModuleTask(moduleCode, mod);
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
