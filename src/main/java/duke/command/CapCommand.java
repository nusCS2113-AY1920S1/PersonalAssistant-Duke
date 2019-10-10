package duke.command;

import duke.exceptions.*;
import duke.modules.ModuleInfoSummary;
import duke.modules.Task;
import duke.util.Reminder;
import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;

import java.util.ArrayList;

public class CapCommand extends Command {

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
    public ModuleList specificModuleCap;
    private float currentCap;
    private float projectedModuleCap;
    private int MCCount;
    /**
     * Constructor for the CapCommand class where user can enquire information about their CAP such as overall CAP and what-if reports about predicted CAP
     * @param input
     * @throws ModEmptyCommandException
     * @throws ModMissingArgumentException
     * @throws ModCommandException
     */
    public CapCommand(String input) throws ModEmptyCommandException, ModMissingArgumentException, ModCommandException {
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
    }

    /**
     * Method for predicting CAP for a specific module
     * @param specificModule
     */
    public void addPrerequisiteCap(String specificModule) {
        try {
            for (ModuleInfoSummary module : moduleList){
                if (module.getDone() && ModulePrerequisiteHashMap.get(specificModule).find(module.moduleCode)) { // if module is completed, and is within the prerequisite tree
                    specificModuleCap.add(module);
                }
            }
            projectedModuleCap = addTotalCap(specificModuleCap);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method to calculate the total CAP based on the modules completed in the moduleList
     * @param moduleList
     * @return
     * @throws ModEmptyListException
     */
    public float addTotalCap(ModuleList moduleList) throws ModEmptyListException {
        if (moduleList.isEmpty()) {
            throw new ModEmptyListException();
        }
        float tempCap = 0;
        int MCCount = 0;
        for (ModuleInfoSummary module : moduleList) {
            if (module.getDone()) {
                tempCap += module.getWeightage();
                MCCount += module.MC;
            }
        }
        return tempCap/MCCount;
    }

    // for printing cap, use System.out.printf("%.2f", this.currentCap or this.projectedModuleCap);
    @Override
    public void execute(TaskList moduleList, Ui ui, Storage store, Reminder reminder) throws ModException {
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
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
