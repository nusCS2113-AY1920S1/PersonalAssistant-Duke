//@@author namiwa

package planner.logic.command;

import planner.credential.user.User;
import planner.logic.exceptions.legacy.ModException;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.util.crawler.JsonWrapper;
import planner.ui.cli.PlannerUi;
import planner.util.storage.Storage;

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
            PlannerUi plannerUi,
            Storage store,
            JsonWrapper jsonWrapper,
            User profile) throws ModException;

    public void call(
            HashMap<String, ModuleInfoDetailed> detailedMap,
            PlannerUi plannerUi,
            Storage store,
            JsonWrapper jsonWrapper,
            User profile) throws ModException {
        execute(detailedMap, plannerUi, store, jsonWrapper, profile);
        profile.saveProfile();
    }
}
