package planner.command;

import planner.modules.data.ModuleInfoDetailed;
import planner.modules.data.ModuleTask;
import planner.util.periods.CcaList;
import planner.util.commons.JsonWrapper;
import planner.util.commons.PlannerUi;
import planner.util.commons.Storage;
import planner.util.commons.ModuleTasksList;

import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class CoreModuleReportCommand extends ModuleCommand {

    private Set<String> coreModList = new HashSet<>();

    private Set<String> getCoreModList() {
        coreModList.add("CG111");
        coreModList.add("CG112");
        coreModList.add("CS1010");
        coreModList.add("CS1231");
        coreModList.add("MA1511");
        coreModList.add("MA1512");
        coreModList.add("M1508E");
        coreModList.add("CG2023");
        coreModList.add("CG2027");
        coreModList.add("CG2028");
        coreModList.add("CG2271");
        coreModList.add("CS2040C");
        coreModList.add("CS2101");
        coreModList.add("EE2026");
        coreModList.add("EG2401A");
        coreModList.add("ST2334");
        coreModList.add("CG3207");
        coreModList.add("CP3380");
        coreModList.add("EG3611A");
        coreModList.add("CG4002");
        coreModList.add("EE4204");
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
            String moduleCode = tasks.getTasks().get(i).getModuleInfoDetailed().getModuleCode();
            if (coreModList.contains(moduleCode)) {
                ModuleTask temp = tasks.getTasks().get(i);
                System.out.println(count + ". " + temp);
                count++;
            }
        }
    }
}
