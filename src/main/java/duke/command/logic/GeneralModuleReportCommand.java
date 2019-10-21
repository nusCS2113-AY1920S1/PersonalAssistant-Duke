package duke.command.logic;

import duke.modules.data.ModuleInfoDetailed;
import duke.modules.data.ModuleTask;
import duke.util.CcaList;
import duke.util.JsonWrapper;
import duke.util.PlannerUi;
import duke.util.Storage;
import duke.util.commons.ModuleTasksList;

import java.util.HashMap;

public class GeneralModuleReportCommand extends ModuleCommand {

    @Override
    public void execute(HashMap<String, ModuleInfoDetailed> detailedMap,
                        ModuleTasksList tasks,
                        CcaList ccas,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper) {
        plannerUi.geModReport();
        int count = 1;
        for (int i = 0; i < tasks.getTasks().size(); i++) {
            String moduleCode = tasks.getTasks().get(i).getModuleInfoDetailed().getModuleCode();
            if (moduleCode.startsWith("GE")) {
                ModuleTask temp = tasks.getTasks().get(i);
                System.out.println(count + ". " + temp);
                count++;
            }
        }
    }
}
