package planner.logic.command;

import planner.logic.modules.module.ModuleInfoDetailed;
import planner.credential.user.CredentialManager;
import planner.credential.user.User;
import planner.ui.cli.PlannerUi;
import planner.util.crawler.JsonWrapper;
import planner.util.storage.Storage;

import java.util.HashMap;

public class SetPasswordCommand extends ModuleCommand {

    public SetPasswordCommand(Arguments args) {
        super(args);
    }

    @Override
    public void execute(HashMap<String, ModuleInfoDetailed> detailedMap,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper, User profile) {
        String oldPassword = null;
        if (profile.isPasswordProtected()) {
            oldPassword = CredentialManager.requirePassword(plannerUi);
        }
        if (profile.setPassword(arg("password"), oldPassword)) {
            plannerUi.println("Password set successfully!");
        } else {
            plannerUi.println("Password set failed!");
        }
    }
}
