package planner.command;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import planner.exceptions.original.ModException;
import planner.exceptions.original.ModMissingArgumentException;
import planner.exceptions.planner.ModNotFoundException;
import planner.modules.data.ModuleInfoDetailed;
import planner.util.periods.CcaList;
import planner.util.commons.JsonWrapper;
import planner.util.commons.PlannerUi;
import planner.util.commons.Storage;
import planner.util.commons.ModuleTasksList;
import planner.modules.data.ModuleTask;
import net.sourceforge.argparse4j.inf.Argument;

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
    private int mcCount;

    /**
     * Constructor for the CapCommand class where user can enquire information about their CAP.
     * Such as overall CAP and what-if reports about predicted CAP.
     * Input format can be in three forms
     * `cap` overall cap
     * `cap semester [x]` where x is some integer or some form of 1-1 or 1-2 to indicate year
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
     * Execute of 3 different forms of user input according to the enum state of this CapCommand class.
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
                //calculate the module's predicted cap from its prerequisites
            case "list":
                List<ModuleTask> hold = moduleTasksList.getTasks();
                plannerUi.capListStartMsg(hold);
                calculateListCap(moduleTasksList, detailedMap, plannerUi, store, scanner, hold);
                break;
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
        throws ModMissingArgumentException, ModNotFoundException {
        String userInput = scanner.nextLine();
        while (!isComplete(userInput)) {
            if (userInput.isEmpty()) {
                throw new ModMissingArgumentException("Please input a completed module and your grade for it,"
                    +
                    " or input done to finish and calculate your CAP");
            }
            String[] userInfo = userInput.split(" ");
            if (!detailedMap.containsKey(userInfo[0].toUpperCase())) {
                throw new ModNotFoundException();
            }
            int mcTemp = detailedMap.get(userInfo[0].toUpperCase()).getModuleCredit();
            if (!detailedMap.get(userInfo[0].toUpperCase()).getAttributes().isSu() || letterGradeToCap(userInfo[1].toUpperCase()) != 0.00) {
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

    public void calculateModuleCap(ModuleTasksList moduleTasksList,
                                    HashMap<String, ModuleInfoDetailed> detailedMap,
                                    PlannerUi plannerUi,
                                    Storage store,
                                    Scanner scanner)
        throws ModMissingArgumentException, ModNotFoundException {
        boolean isPrerequisiteCompleted = true;
        String moduleCode = scanner.nextLine().toUpperCase();
        if (!detailedMap.containsKey(moduleCode)) {
            throw new ModNotFoundException();
        }
        List<List<String>> prunedModules = parsePrerequisiteTree(moduleCode, detailedMap);
        List<List<String>> toCalculate = prunedModules;
        for (int i = 0; i < toCalculate.size(); i++) {

        }

        // make 2 more identical list of lists, remove from one if found in moduletask list / equivalent,
        // check if isempty, if it is then print cap score according to the cloned list of lists
        //}
    }

    public void calculateListCap(ModuleTasksList moduleTasksList,
                                 HashMap<String, ModuleInfoDetailed> detailedMap,
                                 PlannerUi plannerUi,
                                 Storage store,
                                 Scanner scanner,
                                 List<ModuleTask> moduleList) {
        for (ModuleTask module : moduleList) {
            if (!module.getModuleInfoDetailed().getAttributes().isSu() || letterGradeToCap(module.getGrade()) != 0.00) {
                mcCount += module.getModuleCredit();
            }
            projectedModuleCap += (letterGradeToCap(module.getGrade()) * module.getModuleCredit());
        }
        double averageCap = projectedModuleCap / mcCount;
        plannerUi.capMsg(averageCap);
    }

    public List<List<String>> parsePrerequisiteTree(String prerequisites, HashMap<String, ModuleInfoDetailed> detailedMap) {
        //regex([a-zA-Z][a-zA-Z][0-9][0-9][0-9][0-9]|and|or) to get only module codes, and and ors into string array
        // (check for and after because some have AY19/20 and after, then need to reject those 'ands')
        // need to logic and/or from array to cut down size of array
        String[] initialParsedModules = prerequisites.split("[a-zA-Z][a-zA-Z][0-9][0-9][0-9][0-9][a-zA-Z]?|and|or|equivalent");
        List<List<String>> prunedModules = null;
        int j = 0;
        /* EXAMPLES
            prerequisite":"((CS2010 or its equivalent) or CS2020 or (CS2040 or its equivalent)) and (MA1100 or (CS1231 or its equivalent))"
            prerequisite":"CG2027/EE2027 (for AY2017 intake & after)
            prerequisite":"EE2028 or CG2028 (for AY2017 intake & after)
            prerequisite":"((CS2010 or its equivalent) or CS2020 or (CS2040 or its equivalent)) and CS2102
            prerequisite":"CG2027/EE2027 (for AY2017 intake & after) ; EE2021 (for AY2016 intake & prior)
        */
        // what does or its equivalent mean? get preclusion of mod and add it to the pruned
        // make 2 more identical list of lists, remove from one if found in moduletask list / equivalent,
        // check if isempty, if it is then print cap score according to the cloned list of lists
        // for 'or' check next input, if not equivalent, then add to same i dont increase i, if is check after if still or vs and
        // if and, add to list i, move i
        for (int i = 0; i < initialParsedModules.length; i++) {
            if (initialParsedModules[i].equals("equivalent")) {
                String[] preclusions = detailedMap.get(initialParsedModules[i-1]).getPreclusion().split("[a-zA-Z][a-zA-Z][0-9][0-9][0-9][0-9][a-zA-Z]?");
                //preclusion should be all ors, just split and add to pruned without incrementing j
                for (String x : preclusions) {
                    prunedModules.get(j).add(x);
                }

            }
        }

        return prunedModules;
    }
}
