//@@author kyawtsan99

package planner.logic.command;

import planner.credential.user.User;
import planner.logic.modules.cca.Cca;
import planner.util.crawler.JsonWrapper;
import planner.ui.cli.PlannerUi;
import planner.util.storage.Storage;

import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTask;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ShowCommand extends ModuleCommand {

    private static Set<String> coreModList = new HashSet<>();

    public ShowCommand(Arguments args) {
        super(args);
    }

    /**
     * Function to return a string set containing all the core module codes.
     * @return coreModList
     */
    private Set<String> getCoreModList() {
        if (coreModList.size() == 0) {
            setCoreMods(coreModList);
        }
        return coreModList;
    }

    /**
     * Function to add all core module codes into a string set.
     * @param coreModList takes in a set of string and populates it with CEG core module codes.
     */
    public static void setCoreMods(Set<String> coreModList) {
        coreModList.add("CG1111");
        coreModList.add("CG1112");
        coreModList.add("CS1010");
        coreModList.add("CS1231");
        coreModList.add("MA1511");
        coreModList.add("MA1512");
        coreModList.add("MA1508E");
        coreModList.add("CG2023");
        coreModList.add("CG2027");
        coreModList.add("CG2028");
        coreModList.add("CG2271");
        coreModList.add("CS2040C");
        coreModList.add("CS2101");
        coreModList.add("CS2113T");
        coreModList.add("EE2026");
        coreModList.add("EG2401A");
        coreModList.add("ST2334");
        coreModList.add("CG3207");
        coreModList.add("CP3380");
        coreModList.add("EG3611A");
        coreModList.add("CG4002");
        coreModList.add("EE4204");
    }

    //public static void checkGeClash

    @Override
    public void execute(HashMap<String, ModuleInfoDetailed> detailedMap,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper,
                        User profile) {
        switch (arg("toShow")) {
            case "cca": {
                plannerUi.listCcaMsg();
                int count = 1;
                for (Cca cca : profile.getCcas()) {
                    plannerUi.println(count++ + ". " + cca);
                }
                break;
            }

            case ("core"): {
                plannerUi.coreModReport();
                int count = 1;
                Set<String> coreModList = getCoreModList();
                for (ModuleTask task : profile.getModules()) {
                    String moduleCode = task.getModuleCode();
                    if (coreModList.contains(moduleCode)) {
                        plannerUi.println(count++ + ". " + task);
                    }
                }
                plannerUi.coreModLeft();
                int numOfCoreMods = 22;
                plannerUi.println(numOfCoreMods - count + 1);
                break;
            }

            case ("ge"): {
                plannerUi.geModReport();
                int count = 1;
                int gehCount = 0;
                int geqCount = 0;
                int gesCount = 0;
                int gerCount = 0;
                int getCount = 0;
                int geTypeCount = 0;
                for (ModuleTask task : profile.getModules()) {
                    String moduleCode = task.getModuleInfoDetailed().getModuleCode();
                    if (moduleCode.startsWith("GE")) {
                        plannerUi.println(count++ + ". " + task);
                    }
                    if (moduleCode.charAt(2) == 'H') {
                        gehCount++;
                        if (gehCount  > 1) {
                            geTypeCount += 0;
                        } else {
                            geTypeCount++;
                        }
                    } else if (moduleCode.charAt(2) == 'Q') {
                        geqCount++;
                        if (geqCount  > 1) {
                            geTypeCount += 0;
                        } else {
                            geTypeCount++;
                        }
                    } else if (moduleCode.charAt(2) == 'S') {
                        gesCount++;
                        if (gesCount  > 1) {
                            geTypeCount += 0;
                        } else {
                            geTypeCount++;
                        }
                    } else if (moduleCode.charAt(2) == 'R') {
                        gerCount++;
                        if (gerCount  > 1) {
                            geTypeCount += 0;
                        } else {
                            geTypeCount++;
                        }
                    } else if (moduleCode.charAt(2) == 'T') {
                        getCount++;
                        if (getCount  > 1) {
                            geTypeCount += 0;
                        } else {
                            geTypeCount++;
                        }
                    }
                }

                if (gehCount > 1 || geqCount > 1 || gesCount > 1 || gerCount > 1 || getCount > 1) {
                    System.out.println("\n"
                                        +
                                        "There are more than one type of GE modules added.\n"
                                        +
                                        "Please add only one type of GE module each.");
                }

                plannerUi.geModLeft();
                int numOfGeMods = 5;
                plannerUi.println(numOfGeMods - geTypeCount);
                break;
            }

            case ("ue"): {
                plannerUi.ueModReport();
                int count = 1;
                Set<String> coreModList = getCoreModList();
                for (ModuleTask task : profile.getModules()) {
                    String moduleCode = task.getModuleInfoDetailed().getModuleCode();
                    if ((!coreModList.contains(moduleCode)) && !moduleCode.startsWith("GE")) {
                        plannerUi.println(count++ + ". " + task);
                    }
                }
                plannerUi.ueModLeft();
                int numOfUeMods = 8;
                plannerUi.println(numOfUeMods - count + 1);
                break;
            }

            case "module":
            default: {
                plannerUi.listMsg();
                int count = 1;
                for (ModuleTask temp : profile.getModules()) {
                    plannerUi.print(count++ + ". ");
                    plannerUi.showObject(temp);
                }
                break;
            }
        }
    }
}
