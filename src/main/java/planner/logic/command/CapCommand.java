//@@author andrewleow97

package planner.logic.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.antlr.runtime.CharStreamState;
import planner.logic.exceptions.legacy.ModException;
import planner.logic.exceptions.legacy.ModMissingArgumentException;
import planner.logic.exceptions.planner.ModNoPrerequisiteException;
import planner.logic.exceptions.planner.ModNotFoundException;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.cca.CcaList;
import planner.logic.modules.module.ModuleTask;
import planner.logic.modules.module.ModuleTasksList;
import planner.util.crawler.JsonWrapper;
import planner.ui.cli.PlannerUi;
import planner.util.storage.Storage;
import planner.logic.exceptions.legacy.ModCommandException;
import planner.logic.exceptions.legacy.ModEmptyCommandException;
import planner.logic.exceptions.legacy.ModEmptyListException;

public class CapCommand extends ModuleCommand {

    /* TO-DO

    Cap report overall METHOD
    get list of done modules from tasklist, store as tuple? new class? (mcs, letter grade, s/u) in a new arraylist
    calculate mc weightage and cap, request for additional cap info if necessary eg. letter grade missing, s/u options
    show cap

    Cap what-if report one module METHOD
    get list of prereq/preclusions of that module, store in arraylist
    iterate through donemodules from tasklist, remove from above arraylist if done
    if empty, proceed, else throw new exception modules not completed
    calculate expected cap from the donemodules and their mc weightages
    print expected cap

    Cap what-if report overall METHOD ? dont know if needed
    repeat call above method for all 2k modules and above
    print expected cap @ graduation

    */

    public String[] command;
    //public ArrayList<ModuleInfoSummary> completedModuleList = new ArrayList<>();
    //public ModuleList specificModuleCap;
    private double currentCap;
    private double projectedModuleCap;
    private double projectedCap;
    private double mcCount;

    /**
     * Constructor for the CapCommand class where user can enquire information about their CAP.
     * Such as overall CAP and what-if reports about predicted CAP.
     * Input format can be in three forms
     * `cap` overall cap
     * `cap list` to calculate cap from grades in module list
     * `cap module to check predicted cap for a specific module from prerequisites
     */
    public CapCommand(Arguments args) {
        super(args);
        mcCount = 0;
        currentCap = 0;
        projectedModuleCap = 0;
        projectedCap = 0;
    }

    public boolean isComplete(String input) {
        return input.equalsIgnoreCase("done");
    }

    public double getCurrentCap() {
        return currentCap;
    }

    public double getProjectedModuleCap() {
        return projectedModuleCap;
    }

    public double getProjectedCap() {
        return projectedCap;
    }

    /**
     * Converts String grade to a double value according to NUS guidelines.
     */
    public double letterGradeToCap(String grade) {
        switch (grade) {
            case "A+":
            case "A":
                return 5.00;
            case "A-":
                return 4.50;
            case "B+":
                return 4.00;
            case "B":
                return 3.50;
            case "B-":
                return 3.00;
            case "C+":
                return 2.50;
            case "C":
                return 2.00;
            case "D+":
                return 1.50;
            case "D":
                return 1.00;
            case "F":
                return 0.50;
            default:
                return 0.00;
        }
    }

    /**
     * Execute of 3 different forms of user input according to the arguments of the user input.
     */
    @Override
    public void execute(HashMap<String, ModuleInfoDetailed> detailedMap,
                        ModuleTasksList moduleTasksList,
                        CcaList ccas,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper)
        throws ModException {
        Scanner scanner = new Scanner(System.in);
        switch (arg("toCap")) {
            case "overall":
                plannerUi.capStartMsg();
                calculateOverallCap(moduleTasksList, detailedMap, plannerUi, store, scanner);
                break;
            case "module":
                plannerUi.capModStartMsg();
                calculateModuleCap(moduleTasksList, detailedMap, plannerUi, store, scanner);
                break;
            case "list":
                List<ModuleTask> hold = moduleTasksList.getTasks();
                plannerUi.capListStartMsg(hold);
                calculateListCap(moduleTasksList, detailedMap, plannerUi, store, scanner, hold);
                break;
            default:
                throw new ModCommandException();
        }
    }

    /**
     * User will keep inputting "[moduleCode] [letterGrade]" until satisfied.
     * Then user inputs "done" and the user's CAP will be calculated and printed.
     */
    public void calculateOverallCap(ModuleTasksList moduleTasksList,
                                    HashMap<String, ModuleInfoDetailed> detailedMap,
                                    PlannerUi plannerUi,
                                    Storage store,
                                    Scanner scanner)
        throws ModMissingArgumentException, ModNotFoundException, ModEmptyCommandException {
        String userInput = scanner.nextLine();
        while (!isComplete(userInput)) {
            if (userInput.isEmpty()) {
                throw new ModEmptyCommandException();
                //"Please input a completed module and your grade for it,"
                //" or input done to finish and calculate your CAP"
            }
            String[] userInfo = userInput.split(" ");
            if (!detailedMap.containsKey(userInfo[0].toUpperCase())) {
                throw new ModNotFoundException();
            }
            double mcTemp = detailedMap.get(userInfo[0].toUpperCase()).getModuleCredit();
            if (!detailedMap.get(userInfo[0].toUpperCase()).getAttributes().isSu()
                || letterGradeToCap(userInfo[1].toUpperCase()) != 0.00) {
                mcCount += mcTemp;
            }
            if (userInfo[1].isEmpty()) {
                throw new ModMissingArgumentException("Please input a letter grade for this module.");
            }

            currentCap += (letterGradeToCap(userInfo[1].toUpperCase()) * mcTemp);
            userInput = scanner.nextLine();
        }
        double averageCap = currentCap / mcCount;
        plannerUi.capMsg(averageCap);
    }

    /**
     * Calculates a predicted CAP for a module based on the grades attained for it's prerequisites.
     */
    public void calculateModuleCap(ModuleTasksList moduleTasksList,
                                    HashMap<String, ModuleInfoDetailed> detailedMap,
                                    PlannerUi plannerUi,
                                    Storage store,
                                    Scanner scanner)
        throws ModNotFoundException,
        ModNoPrerequisiteException,
        ModEmptyListException {
        String moduleCode = scanner.next().toUpperCase();
        if (detailedMap.get(moduleCode).getPrerequisites().isEmpty() || detailedMap.get(moduleCode).getPrerequisites().isBlank()) {
            throw new ModNoPrerequisiteException();
        }
        if (!detailedMap.containsKey(moduleCode)) {
            throw new ModNotFoundException();
        }
        if (moduleTasksList.getTasks().isEmpty()) {
            throw new ModEmptyListException();
        }
        ArrayList<String> prunedModules = parsePrerequisiteTree(detailedMap.get(moduleCode).getPrerequisites());
        for (ModuleTask x : moduleTasksList.getTasks()) {
            if (prunedModules.contains(x.getModuleCode())) {
                if (letterGradeToCap(x.getGrade()) != 0.00) {
                    mcCount += x.getModuleCredit();
                    projectedModuleCap += letterGradeToCap(x.getGrade()) * x.getModuleCredit();
                }
                prunedModules.remove(x.getModuleCode());
            }
        }
         if (!prunedModules.isEmpty()) {
            for (String module : prunedModules) {
                boolean hasPreclusions = false;
                for (ModuleTask x : moduleTasksList.getTasks()) {
                    if (detailedMap.get(module).getPreclusion().contains(x.getModuleCode())) {
                        hasPreclusions = true;
                        mcCount += x.getModuleCredit();
                        projectedModuleCap += letterGradeToCap(x.getGrade()) * x.getModuleCredit();
                        break;
                    }
                }
                if (hasPreclusions) {
                    prunedModules.remove(module);
                }
            }
        }
         if (prunedModules.isEmpty()) {
             if (projectedModuleCap == 0 && mcCount == 0) {
                 plannerUi.capModMsg(0.00, moduleCode);
             } else {
                 double averageCap = projectedModuleCap / mcCount;
                 plannerUi.capModMsg(averageCap, moduleCode);
             }
         } else {
         plannerUi.capModuleIncompleteMsg(prunedModules);
         }
    }



    // make 2 more identical list of lists, remove from one if found in moduletask list / equivalent,
    // check if isempty, if it is then print cap score according to the cloned list of lists
    //}

    /**
     * Calculates CAP according to the modules with grades in the ModuleTaskList.
     */
    public void calculateListCap(ModuleTasksList moduleTasksList,
                                 HashMap<String, ModuleInfoDetailed> detailedMap,
                                 PlannerUi plannerUi,
                                 Storage store,
                                 Scanner scanner,
                                 List<ModuleTask> moduleList) {
        for (ModuleTask module : moduleList) {
            if (letterGradeToCap(module.getGrade()) != 0.00) {
                mcCount += module.getModuleCredit();
                projectedCap += (letterGradeToCap(module.getGrade()) * module.getModuleCredit());
            }
        }
        double averageCap = projectedCap / mcCount;
        if (projectedCap == 0 && mcCount == 0) {
            plannerUi.capListErrorMsg();
        } else {
            plannerUi.capMsg(averageCap);
        }
    }

    /**
     * Method to parse prerequisites from ModuleInfoDetailed and splice it into a List of Lists of String.
     * Overall is List of Lists, for each internal List it contains modules that are 'or'ed with each other
     * i.e taking one of the modules in the internal list is enough to fulfill one list of prerequisites
     * Across the whole list is modules that are 'and'ed with each other
     * The whole List of Lists must be complete and graded in order for prerequisites to be fulfilled
     *
     * @return A List of lists of string of prerequisite modules to be graded before calculating cap
     */
    public ArrayList<String> parsePrerequisiteTree(String prerequisites) {
        Matcher matcher = Pattern.compile("[a-zA-Z][a-zA-Z][a-zA-Z]?[0-9][0-9][0-9][0-9][a-zA-Z]?").matcher(prerequisites);
        ArrayList<String> prerequisiteModules = new ArrayList<>();
        while (matcher.find()) {
            prerequisiteModules.add(matcher.group());
        }
        return prerequisiteModules;
    }
}
