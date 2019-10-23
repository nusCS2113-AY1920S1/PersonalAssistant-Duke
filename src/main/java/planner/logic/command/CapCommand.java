package planner.logic.command;

import java.util.HashMap;
import java.util.Scanner;

import planner.logic.exceptions.legacy.ModException;
import planner.logic.exceptions.legacy.ModMissingArgumentException;
import planner.logic.exceptions.planner.ModNotFoundException;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.cca.CcaList;
import planner.util.crawler.JsonWrapper;
import planner.ui.cli.PlannerUi;
import planner.util.storage.Storage;
import planner.logic.modules.module.ModuleTasksList;

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
    private float mcCount;

    private enum CommandType {
        OVERALL,
        MODULE,
        SEMESTER
    }

    private CommandType type;

    /**
     * Constructor for the CapCommand class where user can enquire information about their CAP.
     * Such as overall CAP and what-if reports about predicted CAP.
     * Input format can be in three forms
     * `cap` overall cap
     * `cap semester [x]` where x is some integer or some form of 1-1 or 1-2 to indicate year
     * `cap [moduleCode] to check predicted cap for a specific module from prerequisites
     * @param input User input
     */
    public CapCommand(String input) {
        command = input.split(" ");
        if (input.length() <= 4) {
            this.type = CommandType.OVERALL;
        } else if (command[1].equalsIgnoreCase("semester")) {
            this.type = CommandType.SEMESTER;
        } else {
            this.type = CommandType.MODULE;
        }
        mcCount = 0;
        currentCap = 0;
        projectedModuleCap = 0;
        projectedCap = 0;
    }
    /*public CapCommand(String input) throws ModEmptyCommandException
                                            , ModMissingArgumentException
                                                , ModCommandException {
        specificModuleCap.clear();
        currentCap = 0;
        projectedModuleCap = 0;
        try {
            this.command = input.split(" ");
            //completedModuleList.clear();
            if (command.length > 1) {
                addPrerequisiteCap(command[1]);
            } else {
                currentCap = addTotalCap(moduleList);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

            // init moduleCap if isDone


        */
    //}

    /**
     * Method for predicting CAP for a specific module.
     * @param specificModule something
     */
    public void addPrerequisiteCap(String specificModule) {
        /*
        try {

            for (ModuleInfoSummary module : moduleList){
                if (module.getDone() &&
                ModulePrerequisiteHashMap.get(specificModule).find(module.moduleCode))
                { // if module is completed, and is within the prerequisite tree
                    specificModuleCap.add(module);
                }
            }
            projectedModuleCap = addTotalCap(specificModuleCap);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        */
    }

    /*public double addTotalCap(ModuleList moduleList) throws ModEmptyListException {

        if (moduleList.isEmpty()) {
            throw new ModEmptyListException();
        }
        double tempCap = 0;
        int mcCount = 0;
        for (ModuleInfoSummary module : moduleList) {
            if (module.getDone()) {
                tempCap += module.getWeightage();
                mcCount += module.MC;
            }
        }
        return tempCap/mcCount;
    }
    */


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
        if (this.type.equals(CommandType.OVERALL)) {
            plannerUi.capStartMsg();
            calculateOverallCap(moduleTasksList, detailedMap, plannerUi, store, scanner);
        } else if (this.type.equals(CommandType.MODULE)) {
            //calculate the module's predicted cap from its prerequisites
        } else if (this.type.equals(CommandType.SEMESTER)) {
            //check modules taken this sem, calculate predicted cap for the sem according to user input
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
        double cumulativeCap = 0.00;
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
            float mcTemp = detailedMap.get(userInfo[0].toUpperCase()).getModuleCredit();
            mcCount += mcTemp;
            if (userInfo[1].isEmpty()) {
                throw new ModMissingArgumentException("Please input a letter grade for this module.");
            }
            cumulativeCap += (letterGradeToCap(userInfo[1].toUpperCase()) * mcTemp);
            userInput = scanner.nextLine();
        }
        double averageCap = cumulativeCap / mcCount;
        plannerUi.capMsg(averageCap);
    }
}
