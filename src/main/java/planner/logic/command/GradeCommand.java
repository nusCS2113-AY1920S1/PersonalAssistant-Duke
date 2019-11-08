//@@author andrewleow97

package planner.logic.command;

import planner.credential.user.User;
import planner.logic.exceptions.legacy.ModException;
import planner.logic.exceptions.planner.ModNotFoundException;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTask;
import planner.util.crawler.JsonWrapper;
import planner.ui.cli.PlannerUi;
import planner.util.storage.Storage;

import java.util.HashMap;

public class GradeCommand extends ModuleCommand {

    String moduleCode;
    String letterGrade;

    /**
     * Constructor for GradeCommand.
     */
    public GradeCommand(Arguments args) {
        super(args);
        this.moduleCode = arg("moduleCode").toUpperCase();
        this.letterGrade = arg("letterGrade").toUpperCase();
    }

    /**
     * Constructor for testing.
     */
    public GradeCommand(String moduleCode, String letterGrade) {
        this.moduleCode = moduleCode;
        this.letterGrade = letterGrade;
    }

    /**
     * Allows users to grade modules in task list, or add modules with a grade attached if it is not in their list.
     */
    @Override
    public void execute(HashMap<String, ModuleInfoDetailed> detailedMap,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper,
                        User profile) throws ModException {
        if (!detailedMap.containsKey(moduleCode)) {
            throw new ModNotFoundException();
        }
        ModuleInfoDetailed mod = detailedMap.get(moduleCode);
        ModuleTask temp = new ModuleTask(moduleCode, mod);
        if (!profile.getModules().contains(temp)) { // if list does not have module requested, add it with a grade
            mod.setGrade(letterGrade);
            ModuleTask temp2 = new ModuleTask(moduleCode, mod);
            temp2.setTaskDone();
            profile.getModules().add(temp2);
            plannerUi.addedMsg(temp2);
        } else if (profile.getModules().contains(temp)) { // otherwise set grade
            int location = profile.getModules().indexOf(temp);
            profile.getModules().get(location).setGrade(letterGrade);
            profile.getModules().get(location).setTaskDone();
            plannerUi.gradedMsg(temp.getModuleCode(), letterGrade);
        } else {
            throw new ModNotFoundException();
        }
    }
}
