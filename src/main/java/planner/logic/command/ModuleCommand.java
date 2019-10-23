package planner.logic.command;

import planner.logic.exceptions.legacy.ModException;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.cca.CcaList;
import planner.util.crawler.JsonWrapper;
import planner.ui.cli.PlannerUi;
import planner.util.storage.Storage;
import planner.logic.modules.module.ModuleTasksList;

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
