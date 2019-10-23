package planner.logic.command;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import planner.logic.modules.cca.CcaList;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTask;
import planner.logic.modules.module.ModuleTasksList;
import planner.ui.cli.PlannerUi;
import planner.util.crawler.JsonWrapper;
import planner.util.storage.Storage;

public class CoreModuleReportCommand extends ModuleCommand {

    private Set<String> coreModList = new HashSet<>();

    private Set<String> getCoreModList() {
        ShowCommand.setCoreMods(coreModList);
        return coreModList;
    }

    @Override
    public void execute(HashMap<String, ModuleInfoDetailed> detailedMap,
                        ModuleTasksList tasks,
                        CcaList ccas,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper) {
        plannerUi.coreModReport();
        int count = 1;
        coreModList = getCoreModList();

        for (int i = 0; i < tasks.getTasks().size(); i++) {
            String moduleCode = tasks.getTasks().get(i).getModuleInfoDetailed().getModuleCode().toUpperCase();
            if (coreModList.contains(moduleCode)) {
                ModuleTask temp = tasks.getTasks().get(i);
                System.out.println(count + ". " + temp);
                count++;
            }
        }
    }
}
