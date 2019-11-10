// @@author namiwa

package planner.logic.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import planner.credential.user.User;
import planner.logic.exceptions.legacy.ModCommandException;
import planner.logic.exceptions.legacy.ModEmptyCommandException;
import planner.logic.exceptions.legacy.ModException;
import planner.logic.exceptions.planner.ModUpdateErrorException;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.ui.cli.PlannerUi;
import planner.util.crawler.JsonWrapper;
import planner.util.datetime.NattyWrapper;
import planner.util.storage.Storage;

public class UpdateModuleCommand extends ModuleCommand {

    public UpdateModuleCommand(Arguments args) {
        super(args);
    }

    @Override
    public void execute(
            HashMap<String, ModuleInfoDetailed> detailedMap,
            PlannerUi plannerUi,
            Storage store,
            JsonWrapper jsonWrapper,
            User profile) throws ModException {
        String year = arg("moduleDataUpdate");
        if (year.equals("module")) {
            NattyWrapper nattyWrapper = new NattyWrapper();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
            String thisYear = nattyWrapper.dateToLocalDateTime("this year").format(formatter);
            String nextYear = nattyWrapper.dateToLocalDateTime("next year").format(formatter);
            year = thisYear + "-" + nextYear;
            jsonWrapper.runRequests(year, store);
            detailedMap.putAll(jsonWrapper.getModuleDetailedMap());
            profile.getModules().setTasks(jsonWrapper.readJsonTaskList(store));
            plannerUi.showUpdatedMsg();
        } else {
            throw new ModUpdateErrorException();
        }
    }
}
