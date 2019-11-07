//@@author LongLeCE

package planner.logic.command;

import java.util.HashMap;

import planner.logic.exceptions.legacy.ModException;
import planner.logic.modules.cca.CcaList;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTasksList;
import planner.ui.cli.PlannerUi;
import planner.util.crawler.JsonWrapper;
import planner.util.storage.Storage;

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
                case ("module"): {
                    tasks.clearAll();
                    break;
                }

                case ("cca"): {
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
