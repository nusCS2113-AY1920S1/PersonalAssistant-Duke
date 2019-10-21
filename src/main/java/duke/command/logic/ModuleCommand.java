package duke.command.logic;

import duke.exceptions.ModException;
import duke.modules.data.ModuleInfoDetailed;
import duke.util.CcaList;
import duke.util.JsonWrapper;
import duke.util.PlannerUi;
import duke.util.Storage;
import duke.util.commons.ModuleTasksList;

import java.util.HashMap;
import javax.xml.stream.events.Namespace;

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
