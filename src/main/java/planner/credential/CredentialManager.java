//@@author LongLeCE

package planner.credential;

import planner.ui.cli.PlannerUi;

import java.util.Arrays;
import java.util.HashSet;

public class CredentialManager {
    private static HashSet<String> validActions = new HashSet<>(Arrays.asList("login", "register"));

    /**
     * Login/register prompt.
     * @param plannerUi plannerUi to use
     * @return corresponding user profile
     */
    public UserProfile prompt(PlannerUi plannerUi) {
        String action = plannerUi.loginPrompt();
        while (!CredentialManager.validActions.contains(action)) {
            action = plannerUi.invalidResponsePrompt();
        }
        return this.login(action.equals("register"), plannerUi);
    }

    /**
     * Login to ModPlanner.
     * @param isNewUser is user new (not registered)?
     * @param plannerUi plannerUi to use
     * @return corresponding user profile
     */
    private UserProfile login(boolean isNewUser, PlannerUi plannerUi) {
        UserProfile userProfile = null;
        String userName;
        String password;
        boolean firstPrompt = true;
        while (userProfile == null) {
            if (firstPrompt) {
                firstPrompt = false;
            } else {
                plannerUi.println("Invalid credential, please try again!");
            }
            userName = this.getUserName(isNewUser, plannerUi);
            password = this.getPassWord(plannerUi);
            userProfile = UserProfile.createProfile(userName, password, plannerUi);
        }
        return userProfile;
    }

    /**
     * Prompt for username.
     * @param isNewUser is user new (not registered)?
     * @param plannerUi plannerUi to use
     * @return username
     */
    private String getUserName(boolean isNewUser, PlannerUi plannerUi) {
        String userName = plannerUi.prompt("Username: ");
        while (true) {
            if (isNewUser && UserProfile.userExists(userName)) {
                userName = plannerUi.userExistPrompt();
            } else if (!isNewUser && !UserProfile.userExists(userName)) {
                userName = plannerUi.noSuchUserPrompt();
            } else {
                return userName;
            }
        }
    }

    private String getPassWord(PlannerUi plannerUi) {
        return plannerUi.prompt("Password: ", false, true);
    }
}
