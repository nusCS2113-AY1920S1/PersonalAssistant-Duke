//@@author LongLeCE

package planner.logic.command;

import planner.logic.modules.cca.CcaList;
import planner.util.crawler.JsonWrapper;
import planner.ui.cli.PlannerUi;
import planner.util.storage.Storage;

import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTasksList;
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
