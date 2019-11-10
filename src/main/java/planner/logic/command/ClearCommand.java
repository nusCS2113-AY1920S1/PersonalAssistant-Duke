//@@author LongLeCE

package planner.logic.command;

import java.util.HashMap;

import planner.credential.user.CredentialManager;
import planner.credential.user.User;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.ui.cli.PlannerUi;
import planner.util.crawler.JsonWrapper;
import planner.util.storage.Storage;

public class ClearCommand extends ModuleCommand {

    public ClearCommand(Arguments args) {
        super(args);
    }

    @Override
    public void execute(HashMap<String, ModuleInfoDetailed> detailedMap,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper,
                        User profile) {
        String toClear = arg("toClear");
        plannerUi.clearMsg(toClear);
        boolean confirm = plannerUi.confirm();
        if (confirm) {
            switch (toClear) {
                case ("modules"): {
                    profile.getModules().clear();
                    break;
                }

                case ("ccas"): {
                    profile.getCcas().clear();
                    break;
                }

                case ("password"): {
                    if (profile.isPasswordProtected()) {
                        String oldPassword = CredentialManager.requirePassword(plannerUi);
                        if (profile.isValidPassword(oldPassword)) {
                            profile.clearPassword();
                        } else {
                            plannerUi.println("Failed to clear password!");
                            return;
                        }
                    } else {
                        plannerUi.println("No active password found!");
                        return;
                    }
                    break;
                }

                case ("data"): {
                    profile.getModules().clear();
                    profile.getCcas().clear();
                    profile.clear();
                    break;
                }

                default: {
                    break;
                }
            }
            plannerUi.clearedMsg(toClear);
        } else {
            plannerUi.abortMsg();
        }
    }
}
