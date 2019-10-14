package duke.command;

import duke.exceptions.ModException;
import duke.exceptions.ModEmptyListException;
import duke.modules.ModuleInfoSummary;
import duke.modules.Task;
import duke.util.Reminder;
import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;

import java.util.ArrayList;
import java.util.Scanner;

public class CapCommand extends Command {

    /* TO-DO

    TODO NEW SHIT make cap command for only user's input command (loop ask for user mod and letter grade, sort grade and get mod mc to get weightage) then then calculate cap


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
    private enum commandType{
        OVERALL,
        MODULE,
        SEMESTER
    }
    private commandType type;

    /**
     * Constructor for the CapCommand class where user can enquire information about their CAP.
     * Such as overall CAP and what-if reports about predicted CAP.
     * @param input
     */
    public CapCommand(String input) {
        command = input.split(" ");
        if (input.length() <= 4) {
            this.type = commandType.OVERALL;
        } else if (command[1].equalsIgnoreCase("semester")) {
            this.type = commandType.SEMESTER;
        } else {
            this.type = commandType.MODULE;
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


    /**
     * Method to calculate the total CAP based on the modules completed in the moduleList.
     * @param moduleList blah
     * @return something
     * @throws ModEmptyListException something exception
     */
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


    // for printing cap, use System.out.printf("%.2f", this.currentCap or this.projectedModuleCap);


    @Override
    public void execute(TaskList moduleList, Ui ui, Storage store, Reminder reminder) throws ModException {
        Scanner scanner = new Scanner(System.in);
        if (this.type.equals(commandType.OVERALL)) {
            ui.showLine();
            String userInput = scanner.nextLine();
            while (!isComplete(userInput)) {
                String[] userInfo = userInput.split(" ");
                mcCount += (moduleList.find(userInfo[0]).getModuleCredit; //todo replace this with hashmap when it is up
        }

       /*
        if (specificModuleCap.isEmpty()) {
            ui.showLine();
            ui.overallCapMsg();
            System.out.printf("%.2f\n", this.currentCap);
            ui.showLine();
        } else {
            ui.showLine();
            ui.specificCapMsg(command[1]);
            System.out.printf("%.2f\n", this.projectedModuleCap);
            ui.showLine();
        }
        */
        }
    }

    public boolean isComplete(String input) {
        if (input.equalsIgnoreCase("done")) {
            return true;
        }
        return false;
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

    public double letterGradeToCap(String grade) {
        switch(grade) {
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

    @Override
    public boolean isExit() {
        return false;
    }
}
