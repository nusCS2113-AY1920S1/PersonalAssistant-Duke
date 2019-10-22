package planner.command;

import planner.exceptions.original.ModException;
import planner.exceptions.planner.ModNotFoundException;
import planner.modules.data.ModuleInfoDetailed;
import planner.modules.data.ModuleTask;
import planner.util.commons.JsonWrapper;
import planner.util.commons.ModuleTasksList;
import planner.util.commons.PlannerUi;
import planner.util.commons.Storage;
import planner.util.periods.CcaList;

import java.util.HashMap;

public class GradeCommand extends ModuleCommand {
    public GradeCommand(Arguments args) {
        super(args);
    }

    @Override
    public void execute(HashMap<String, ModuleInfoDetailed> detailedMap,
                        ModuleTasksList tasks,
                        CcaList ccas,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper) throws ModException {
        //grade moduleCode letterGrade
        String moduleCode = arg("moduleCode").toUpperCase();
        String letterGrade = arg("letterGrade").toUpperCase();
        ModuleInfoDetailed mod = detailedMap.get(moduleCode);
        ModuleTask temp = new ModuleTask(moduleCode, mod);
        if (!tasks.getTasks().contains(temp)) {
            mod.setGrade(letterGrade);
            ModuleTask temp2 = new ModuleTask(moduleCode, mod);
            tasks.getTasks().add(temp2);
            plannerUi.addedMsg(temp2);
            jsonWrapper.storeTaskListAsJson(tasks.getTasks(), store);
        } else if (tasks.getTasks().contains(temp)) {
            int location = tasks.getTasks().indexOf(temp);
            tasks.getTasks().get(location).setGrade(letterGrade);
        } else {
            throw new ModNotFoundException();
        }
    }
}
