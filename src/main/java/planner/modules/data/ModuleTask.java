package planner.modules.data;

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

    public float getModuleCredit() {
        return moduleInfoDetailed.getModuleCredit();
    }

    public ModuleInfoDetailed getModuleInfoDetailed() {
        return moduleInfoDetailed;
    }

    @Override
    public String toString() {
        return super.toString() + " | " + moduleInfoDetailed.toString();
    }
}
