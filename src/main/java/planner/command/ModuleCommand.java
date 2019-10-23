package planner.command;

import planner.exceptions.original.ModException;
import planner.modules.data.ModuleInfoDetailed;
import planner.util.periods.CcaList;
import planner.util.commons.JsonWrapper;
import planner.util.commons.PlannerUi;
import planner.util.commons.Storage;
import planner.util.commons.ModuleTasksList;

import java.util.HashMap;

public abstract class ModuleCommand {

    Arguments args;

    public ModuleCommand() {
    }

    public ModuleCommand(Arguments args) {
        this.args = args;
    }

    <T> T arg(String name, Class<T> type) {
        return type.cast(this.args.get(name));
    }

    String arg(String name) {
        return this.arg(name, String.class);
    }

    public abstract void execute(
            HashMap<String, ModuleInfoDetailed> detailedMap,
            ModuleTasksList tasks,
            CcaList ccas,
            PlannerUi plannerUi,
            Storage store,
            JsonWrapper jsonWrapper) throws ModException;
}
