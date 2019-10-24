//@@author andrewleow97

package planner.logic.command;

import planner.logic.exceptions.legacy.ModException;
import planner.logic.exceptions.planner.ModNotFoundException;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTask;
import planner.util.crawler.JsonWrapper;
import planner.logic.modules.module.ModuleTasksList;
import planner.ui.cli.PlannerUi;
import planner.util.storage.Storage;
import planner.logic.modules.cca.CcaList;

import java.util.HashMap;

public class GradeCommand extends ModuleCommand {

    /**
     * Constructor for GradeCommand.
     */
    public GradeCommand(Arguments args) {
        super(args);
    }

    /**
     * Allows users to grade modules in task list, or add modules with a grade attached if it is not in their list.
     */
    @Override
    public void execute(HashMap<String, ModuleInfoDetailed> detailedMap,
                        ModuleTasksList tasks,
                        CcaList ccas,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper) throws ModException {
        String moduleCode = arg("moduleCode").toUpperCase();
        String letterGrade = arg("letterGrade").toUpperCase();
        if (!detailedMap.containsKey(moduleCode)) {
            throw new ModNotFoundException();
        }
        ModuleInfoDetailed mod = detailedMap.get(moduleCode);
        ModuleTask temp = new ModuleTask(moduleCode, mod);
        if (!tasks.getTasks().contains(temp)) { // if list does not have module requested, add it with a grade
            mod.setGrade(letterGrade);
            ModuleTask temp2 = new ModuleTask(moduleCode, mod);
            temp2.setTaskDone();
            tasks.getTasks().add(temp2);
            plannerUi.addedMsg(temp2);
            jsonWrapper.storeTaskListAsJson(tasks.getTasks(), store);
        } else if (tasks.getTasks().contains(temp)) { // otherwise set grade
            int location = tasks.getTasks().indexOf(temp);
            tasks.getTasks().get(location).setGrade(letterGrade);
            tasks.getTasks().get(location).setTaskDone();
            plannerUi.gradedMsg(temp.getModuleCode(), letterGrade);
            jsonWrapper.storeTaskListAsJson(tasks.getTasks(), store);
        } else {
            throw new ModNotFoundException();
        }
    }
}
