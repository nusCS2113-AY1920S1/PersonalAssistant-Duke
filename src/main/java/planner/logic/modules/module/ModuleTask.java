package planner.logic.modules.module;

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

    public ModuleInfoDetailed getModuleInfoDetailed() {
        return moduleInfoDetailed;
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
