package planner.modules.data;

import planner.exceptions.original.ModBadGradeException;
import planner.exceptions.original.ModBadSuException;
import planner.modules.inherited.Task;

public class ModuleTask extends Task {

    private ModuleInfoDetailed moduleInfoDetailed;

    public ModuleTask(String code, ModuleInfoDetailed moduleInfoDetailed) {
        super(code);
        this.moduleInfoDetailed = moduleInfoDetailed;
    }

    public String getModuleCode() {
        return moduleInfoDetailed.getModuleCode();
    }

    public int getModuleCredit() {
        return moduleInfoDetailed.getModuleCredit();
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
}
