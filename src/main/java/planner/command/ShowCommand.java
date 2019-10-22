package planner.command;

import planner.modules.inherited.Cca;
import planner.util.periods.CcaList;
import planner.util.commons.JsonWrapper;
import planner.util.commons.PlannerUi;
import planner.util.commons.Storage;

import planner.modules.data.ModuleInfoDetailed;
import planner.modules.data.ModuleTask;
import planner.util.commons.ModuleTasksList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShowCommand extends ModuleCommand {

    private static Set<String> coreModList;

    public ShowCommand(Arguments args) {
        super(args);
    }

    private Set<String> getCoreModList() {
        if (coreModList == null) {
            coreModList = new HashSet<>();
            coreModList.add("CG1111");
            coreModList.add("CG1112");
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
        }
        return coreModList;
    }

    @Override
    public void execute(HashMap<String, ModuleInfoDetailed> detailedMap,
                        ModuleTasksList tasks,
                        CcaList ccas,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper) {
        switch (arg("toShow")) {
            case "cca": {
                plannerUi.listCcaMsg();
                int counter = 0;
                for (Cca cca : ccas) {
                    plannerUi.println(++counter + " " + cca);
                }
                break;
            }

            case ("core"): {
                plannerUi.coreModReport();
                int count = 1;
                Set<String> coreModList = getCoreModList();
                for (int i = 0; i < tasks.getTasks().size(); i++) {
                    String moduleCode = tasks.getTasks().get(i).getModuleInfoDetailed().getModuleCode();
                    if (coreModList.contains(moduleCode)) {
                        ModuleTask temp = tasks.getTasks().get(i);
                        System.out.println(count + ". " + temp);
                        count++;
                    }
                }
                break;
            }

            case ("ge"): {
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
                break;
            }

            case ("ue"): {
                plannerUi.ueModReport();
                int count = 1;
                Set<String> coreModList = getCoreModList();
                for (int i = 0; i < tasks.getTasks().size(); i++) {
                    String moduleCode = tasks.getTasks().get(i).getModuleInfoDetailed().getModuleCode();
                    if ((!coreModList.contains(moduleCode)) && !moduleCode.startsWith("GE")) {
                        ModuleTask temp = tasks.getTasks().get(i);
                        System.out.println(count + ". " + temp);
                        count++;
                    }
                }
                break;
            }

            case "module":
            default: {
                plannerUi.listMsg();
                int counter = 1;
                List<ModuleTask> hold = tasks.getTasks();
                for (ModuleTask temp : hold) {
                    System.out.print(counter++ + " ");
                    plannerUi.showObject(temp);
                }
                break;
            }
        }
    }
}
