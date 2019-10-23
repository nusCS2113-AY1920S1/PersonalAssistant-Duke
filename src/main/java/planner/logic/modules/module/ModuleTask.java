package planner.logic.modules.module;

import planner.logic.exceptions.planner.ModBadGradeException;
import planner.logic.exceptions.planner.ModBadSuException;
import planner.logic.modules.legacy.task.Task;

public class ModuleTask extends Task {

    private ModuleInfoDetailed moduleInfoDetailed;

    public ModuleTask(String code, ModuleInfoDetailed moduleInfoDetailed) {
        super(code);
        this.moduleInfoDetailed = moduleInfoDetailed;
    }

    public String getModuleCode() {
        return moduleInfoDetailed.getModuleCode();
    }

    public Double getModuleCredit() {
        return moduleInfoDetailed.getModuleCredit();
    }

    public String getModuleLevel() {
        return moduleInfoDetailed.getModuleLevel();
    }

    public ModuleInfoDetailed getModuleInfoDetailed() {
        return moduleInfoDetailed;
    }

    public String getGrade() {
        return moduleInfoDetailed.getGrade();
    }

    public void setGrade(String letterGrade) throws ModBadSuException, ModBadGradeException {
        moduleInfoDetailed.setGrade(letterGrade);
    }

    @Override
    public String toString() {
        return super.toString() + " | " + moduleInfoDetailed.toString();
    }

    @Override
    public String type() {
        return "module";
    }
}
