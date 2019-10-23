package planner.command;

import planner.util.periods.CcaList;
import planner.util.commons.JsonWrapper;
import planner.util.commons.PlannerUi;
import planner.util.commons.Storage;

import planner.modules.data.ModuleInfoDetailed;
import planner.util.commons.ModuleTasksList;
import java.util.HashMap;

public class ClearCommand extends ModuleCommand {

    public ClearCommand(Arguments args) {
        super(args);
    }

    @Override
    public void execute(HashMap<String, ModuleInfoDetailed> detailedMap,
                        ModuleTasksList tasks,
                        CcaList ccas,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper) {
        String toClear = arg("toClear");
        plannerUi.clearMsg(toClear);
        boolean confirm = plannerUi.confirm();
        if (confirm) {
            switch (toClear) {
                case ("modules"): {
                    tasks.clearAll();
                    break;
                }

                case ("ccas"): {
                    ccas.clear();
                    break;
                }

                // TODO: Add clear data capability
                // case ("data"): {
                // break;
                // }

                default: {
                    break;
                }
            }
            plannerUi.clearedMsg(toClear);
            jsonWrapper.storeTaskListAsJson(tasks.getTasks(), store);
        } else {
            plannerUi.abortMsg();
        }
    }
}
