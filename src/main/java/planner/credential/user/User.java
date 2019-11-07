//@@author LongLeCE

package planner.credential.user;

import planner.logic.command.Arguments;
import planner.logic.command.ClearCommand;
import planner.logic.exceptions.planner.ModTamperedUserDataException;
import planner.logic.modules.cca.CcaList;
import planner.logic.modules.legacy.task.Task;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTask;
import planner.logic.modules.module.ModuleTasksList;
import planner.ui.cli.PlannerUi;
import planner.util.crawler.JsonWrapper;
import planner.util.storage.Storage;

import java.util.HashMap;
import java.util.List;

public class User {
    private String password;
    private int currentSemester;
    private HashMap<Integer, HashMap<String, List<? extends Task>>> modulesAndCcas;
    private static Storage storage = new Storage();
    private static CredentialManager credentialManager = new CredentialManager();
    private static Profile profile;
    private static int LOGIN_LIMITS = 5;
    private static final String defaultPath = "data/userProfile.json";

    static {
        profile = readUserData();
        if (profile == null) {
            User.profile = new Profile();
            User.profile.put("profile", null);
        }
    }

    private static Profile readUserData() {
        try {
            return User.storage.readGsonSecure(User.defaultPath, Profile.class);
        } catch (ModTamperedUserDataException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Load user profile.
     * @param detailedMap detailed module map
     * @param tasks module list
     * @param ccas cca list
     * @param plannerUi planner Ui
     * @param store storage
     * @param jsonWrapper json wrapper
     * @param profile user profile
     * @return user profile
     */
    public static User loadProfile(HashMap<String, ModuleInfoDetailed> detailedMap,
                                   ModuleTasksList tasks,
                                   CcaList ccas,
                                   PlannerUi plannerUi,
                                   Storage store,
                                   JsonWrapper jsonWrapper, User profile) {
        User user = User.profile.get("profile");
        if (user == null) {
            return new User();
        }
        while (!User.confirmOldPassword(user, detailedMap, tasks, ccas, plannerUi, store, jsonWrapper, profile)) {
            plannerUi.println("Sorry but I cannot continue without a valid password!");
        }
        return user;
    }

    /**
     * Prompt to confirm old password on log in.
     * @param user user profile
     * @param detailedMap detailed module map
     * @param tasks module list
     * @param ccas cca list
     * @param plannerUi planner Ui
     * @param store storage
     * @param jsonWrapper json wrapper
     * @param profile user profile
     * @return true if user input valid old password within LOGIN_LIMITS tries, false otherwise
     */
    public static boolean confirmOldPassword(User user,
                                             HashMap<String, ModuleInfoDetailed> detailedMap,
                                             ModuleTasksList tasks,
                                             CcaList ccas,
                                             PlannerUi plannerUi,
                                             Storage store,
                                             JsonWrapper jsonWrapper,
                                             User profile) {
        if (user.isPasswordProtected()) {
            int counter = 1;
            for (String password = CredentialManager.requirePassword(plannerUi);
                 !User.isValidPassword(password, user.password);
                 password = CredentialManager.requirePassword(plannerUi)) {
                plannerUi.println("That did not work, please try again");
                ++counter;
                if (counter > LOGIN_LIMITS) {
                    boolean reset = plannerUi.confirm("You are entering wrong passwords too many times!" +
                            "\nDo you want me to reset the password? (User data will be wiped!)");
                    if (reset) {
                        User.reset(detailedMap, tasks, ccas, plannerUi, store, jsonWrapper, profile);
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Save user profile to disk.
     */
    public void saveProfile() {
        User.profile.put("profile", this);
        User.storage.writeGsonSecure(User.profile, User.defaultPath);
    }

    public int getSemester() {
        return this.currentSemester;
    }

    @SuppressWarnings("unchecked")
    public List<ModuleTask> getModules(int semester) {
        return (List<ModuleTask>) this.modulesAndCcas.get(semester).get("modules");
    }

    public List<ModuleTask> getModules() {
        return this.getModules(this.getSemester());
    }

    public CcaList getCcas(int semester) {
        return (CcaList) this.modulesAndCcas.get(semester).get("ccas");
    }

    public CcaList getCcas() {
        return this.getCcas(this.getSemester());
    }

    /**
     * Set modules/ccas for a semester.
     * @param semester selected semester
     * @param tasks module list
     */
    private void setTask(int semester, String type, List<? extends Task> tasks) {
        if (tasks != null && !tasks.isEmpty()) {
            HashMap<String, List<? extends Task>> modulesMap = new HashMap<>();
            modulesMap.put(type, tasks);
            if (this.modulesAndCcas == null) {
                this.modulesAndCcas = new HashMap<>();
            }
            this.modulesAndCcas.put(semester, modulesMap);
        }
    }

    public void setModules(int semester, List<ModuleTask> modules) {
        this.setTask(semester, "modules", modules);
    }

    public void setModules(List<ModuleTask> modules) {
        this.setModules(this.getSemester(), modules);
    }

    public void setCcas(int semester, CcaList ccas) {
        this.setTask(semester, "ccas", ccas);
    }

    public void setCcas(CcaList ccas) {
        this.setCcas(this.getSemester(), ccas);
    }

    /**
     * Set modules and ccas for a semester.
     * @param semester selected semester
     * @param modules module list
     * @param ccas cca list
     */
    public void setModulesAndCcas(int semester, List<ModuleTask> modules, CcaList ccas) {
        this.setModules(semester, modules);
        this.setCcas(semester, ccas);
    }

    public void setModulesAndCcas(List<ModuleTask> modules, CcaList ccas) {
        this.setModulesAndCcas(this.getSemester(), modules, ccas);
    }

    public void setSemester(int semester) {
        this.currentSemester = semester;
    }

    /**
     * Set new password.
     * @param newPassword new password
     * @param oldPassword old password
     * @return true if old password is valid, false otherwise
     */
    public boolean setPassword(String newPassword, String oldPassword) {
        if (newPassword != null && !newPassword.isBlank()
                && (!this.isPasswordProtected() || this.isValidPassword(oldPassword))) {
            this.password = credentialManager.hash(newPassword);
            return true;
        }
        return false;
    }

    public String getPasswordHash() {
        return this.password;
    }

    private boolean isValidPassword(String password) {
        return User.isValidPassword(password, this.password);
    }

    public static boolean isValidPassword(String password, String passwordHash) {
        return credentialManager.compareHash(password, passwordHash);
    }

    public boolean isPasswordProtected() {
        return this.password != null && !this.password.isBlank();
    }

    /**
     * Reset data (depends on ClearCommand's implementation).
     * @param detailedMap detailed module map
     * @param tasks module list
     * @param ccas cca list
     * @param plannerUi planner Ui
     * @param store storage
     * @param jsonWrapper json wrapper
     * @param profile user profile
     */
    private static void reset(HashMap<String, ModuleInfoDetailed> detailedMap,
                              ModuleTasksList tasks,
                              CcaList ccas,
                              PlannerUi plannerUi,
                              Storage store,
                              JsonWrapper jsonWrapper, User profile) {
        HashMap<String, Object> argsMap = new HashMap<>();
        argsMap.put("command", "clear");
        argsMap.put("toClear", "data");
        Arguments args = new Arguments(argsMap);
        new ClearCommand(args).execute(detailedMap, tasks, ccas, plannerUi, store, jsonWrapper, profile);
    }
}
