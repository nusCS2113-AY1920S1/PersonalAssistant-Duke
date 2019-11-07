// @@author namiwa

package planner.logic.command;

import java.util.HashMap;

import planner.credential.user.User;
import planner.logic.exceptions.legacy.ModException;
import planner.logic.modules.cca.CcaList;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTasksList;
import planner.ui.cli.PlannerUi;
import planner.util.crawler.JsonWrapper;
import planner.util.storage.Storage;

public class UpdateModuleInfo extends ModuleCommand {

    public UpdateModuleInfo(Arguments args) {
        super(args);
    }

    @Override
    public void execute(
            HashMap<String, ModuleInfoDetailed> detailedMap,
            ModuleTasksList tasks,
            CcaList ccas,
            PlannerUi plannerUi,
            Storage store,
            JsonWrapper jsonWrapper,
            User profile) throws ModException {

        String year = arg("academicYear");
        jsonWrapper.runRequests(year, store);
        detailedMap.putAll(jsonWrapper.getModuleDetailedMap());
        tasks.setTasks(jsonWrapper.readJsonTaskList(store));
        plannerUi.showUpdatedMsg();
    }
}
