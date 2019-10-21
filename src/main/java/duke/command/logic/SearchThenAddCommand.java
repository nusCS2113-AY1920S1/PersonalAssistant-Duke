package duke.command.logic;

import duke.exceptions.ModCcaScheduleException;
import duke.exceptions.ModException;
import duke.exceptions.planner.ModNotFoundException;
import duke.modules.Cca;
import duke.modules.data.ModuleInfoDetailed;
import duke.modules.data.ModuleTask;
import duke.util.CcaList;
import duke.util.JsonWrapper;
import duke.util.PlannerUi;
import duke.util.Storage;
import duke.util.commons.ModuleTasksList;

import java.util.HashMap;

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
