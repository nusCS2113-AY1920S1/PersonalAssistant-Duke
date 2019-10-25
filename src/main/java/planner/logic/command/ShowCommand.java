//@@author kyawtsan99

package planner.logic.command;

import planner.logic.modules.cca.Cca;
import planner.logic.modules.cca.CcaList;
import planner.util.crawler.JsonWrapper;
import planner.ui.cli.PlannerUi;
import planner.util.storage.Storage;

import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTask;
import planner.logic.modules.module.ModuleTasksList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShowCommand extends ModuleCommand {

    private static Set<String> coreModList = new HashSet<>();

    public ShowCommand(Arguments args) {
        super(args);
    }

    private Set<String> getCoreModList() {
        if (coreModList.size() == 0) {
            setCoreMods(coreModList);
        }
        return coreModList;
    }

    /**
     * Function to return a string set containing all core module codes.
     * @param coreModList takes in a set of string and populates it with CEG core module codes.
     */
    public static void setCoreMods(Set<String> coreModList) {
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
                int count = 0;
                for (Cca cca : ccas) {
                    plannerUi.println(count++ + " " + cca);
                }
                break;
            }

            case ("core"): {
                plannerUi.coreModReport();
                int count = 0;
                Set<String> coreModList = getCoreModList();
                for (int i = 0; i < tasks.getTasks().size(); i++) {
                    String moduleCode = tasks.getTasks().get(i).getModuleCode();
                    if (coreModList.contains(moduleCode)) {
                        ModuleTask temp = tasks.getTasks().get(i);
                        System.out.println(count++ + ". " + temp);
                    }
                }
                plannerUi.coreModLeft();
                int numOfCoreMods = 22;
                System.out.println(numOfCoreMods - count);
                break;
            }

            case ("ge"): {
                plannerUi.geModReport();
                int count = 0;
                for (int i = 0; i < tasks.getTasks().size(); i++) {
                    String moduleCode = tasks.getTasks().get(i).getModuleInfoDetailed().getModuleCode();
                    if (moduleCode.startsWith("GE")) {
                        ModuleTask temp = tasks.getTasks().get(i);
                        System.out.println(count++ + ". " + temp);
                    }
                }
                plannerUi.geModLeft();
                int numOfGeMods = 5;
                System.out.println(numOfGeMods - count);
                break;
            }

            case ("ue"): {
                plannerUi.ueModReport();
                int count = 0;
                Set<String> coreModList = getCoreModList();
                for (int i = 0; i < tasks.getTasks().size(); i++) {
                    String moduleCode = tasks.getTasks().get(i).getModuleInfoDetailed().getModuleCode();
                    if ((!coreModList.contains(moduleCode)) && !moduleCode.startsWith("GE")) {
                        ModuleTask temp = tasks.getTasks().get(i);
                        System.out.println(count++ + ". " + temp);
                    }
                }
                plannerUi.ueModLeft();
                int numOfUeMods = 8;
                System.out.println(numOfUeMods - count);
                break;
            }

            case "module":
            default: {
                plannerUi.listMsg();
                int count = 1;
                List<ModuleTask> hold = tasks.getTasks();
                for (ModuleTask temp : hold) {
                    System.out.print(count++ + " ");
                    plannerUi.showObject(temp);
                }
                break;
            }
        }
    }
}
