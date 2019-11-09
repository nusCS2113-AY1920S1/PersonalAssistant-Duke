//@@author LongLeCE

package planner.credential.user;

import planner.logic.command.Arguments;
import planner.logic.command.ClearCommand;
import planner.logic.exceptions.planner.ModTamperedUserDataException;
import planner.logic.modules.TaskList;
import planner.logic.modules.cca.Cca;
import planner.logic.modules.legacy.task.TaskWithMultipleWeeklyPeriod;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTask;
import planner.ui.cli.PlannerUi;
import planner.util.crawler.JsonWrapper;
import planner.util.storage.Storage;

import java.lang.reflect.Field;
import java.util.HashMap;

public class User {
    private String password;
    private int currentSemester;
    private HashMap<Integer, TaskLists> modulesAndCcas;
    private static Storage storage = new Storage();
    private static CredentialManager credentialManager = new CredentialManager();
    private static int LOGIN_LIMITS = 5;
    private static final String defaultPath = "data/userProfile.json";

    private User(int semester) {
        currentSemester = semester;
        this.init();
    }

    private User() {
        this(0);
    }

    private void init(int semester) {
        modulesAndCcas = new HashMap<>();
        modulesAndCcas.put(semester, new TaskLists());
        modulesAndCcas.get(semester).setTask(new TaskList<>());
        modulesAndCcas.get(semester).setCcas(new TaskList<>());
    }

    private void init() {
        this.init(currentSemester);
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
     * @param plannerUi planner Ui
     * @param store storage
     * @param jsonWrapper json wrapper
     * @return user profile
     */
    public static User loadProfile(HashMap<String, ModuleInfoDetailed> detailedMap,
                                   PlannerUi plannerUi,
                                   Storage store,
                                   JsonWrapper jsonWrapper) {
        Profile p = User.readUserData();
        if (p == null) {
            return new User();
        }
        User user = p.get("profile");
        if (user == null) {
            return new User();
        }
        while (!User.confirmOldPassword(detailedMap, plannerUi, store, jsonWrapper, user)) {
            plannerUi.println("Sorry but I cannot continue without a valid password!");
        }
        return user;
    }

    /**
     * Prompt to confirm old password on log in.
     * @param detailedMap detailed module map
     * @param plannerUi planner Ui
     * @param store storage
     * @param jsonWrapper json wrapper
     * @param profile user profile
     * @return true if user input valid old password within LOGIN_LIMITS tries, false otherwise
     */
    public static boolean confirmOldPassword(HashMap<String, ModuleInfoDetailed> detailedMap,
                                             PlannerUi plannerUi,
                                             Storage store,
                                             JsonWrapper jsonWrapper,
                                             User profile) {
        if (profile.isPasswordProtected()) {
            int counter = 1;
            for (String password = CredentialManager.requirePassword(plannerUi);
                 !User.isValidPassword(password, profile.password);
                 password = CredentialManager.requirePassword(plannerUi)) {
                plannerUi.println("That did not work, please try again");
                ++counter;
                if (counter > LOGIN_LIMITS) {
                    boolean reset = plannerUi.confirm("You are entering wrong passwords too many times!"
                            + "\nDo you want me to reset the password? (ALL user data will be wiped!)");
                    if (reset) {
                        User.reset(detailedMap, plannerUi, store, jsonWrapper, profile);
                        return true;
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
        Profile profile = new Profile();
        profile.put("profile", this);
        User.storage.writeGsonSecure(profile, User.defaultPath);
    }

    public int getSemester() {
        return this.currentSemester;
    }

    public TaskList<ModuleTask> getModules(int semester) {
        return this.modulesAndCcas.get(semester).getModules();
    }

    public TaskList<ModuleTask> getModules() {
        return this.getModules(this.getSemester());
    }

    public TaskList<Cca> getCcas(int semester) {
        return this.modulesAndCcas.get(semester).getCcas();
    }

    public TaskList<Cca> getCcas() {
        return this.getCcas(this.getSemester());
    }

    public TaskList<TaskWithMultipleWeeklyPeriod> getAllTasks(int semester) {
        return this.modulesAndCcas.get(semester).getAllTasks();
    }

    public TaskList<TaskWithMultipleWeeklyPeriod> getAllTasks() {
        return this.getAllTasks(this.getSemester());
    }

    /**
     * Set modules/ccas for a semester.
     * @param semester selected semester
     * @param tasks module list
     */
    private void setTask(int semester, TaskList<? extends TaskWithMultipleWeeklyPeriod> tasks) {
        if (tasks != null && !tasks.isEmpty()) {
            if (this.modulesAndCcas == null) {
                this.modulesAndCcas = new HashMap<>();
            }
            TaskLists taskLists = this.modulesAndCcas.get(semester);
            for (Field field: taskLists.getClass().getDeclaredFields()) {
                if (field.getClass().isAssignableFrom(tasks.getClass())) {
                    try {
                        field.set(taskLists, tasks);
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    public void setModules(int semester, TaskList<ModuleTask> modules) {
        this.setTask(semester, modules);
    }

    public void setModules(TaskList<ModuleTask> modules) {
        this.setModules(this.getSemester(), modules);
    }

    public void setCcas(int semester, TaskList<Cca> ccas) {
        this.setTask(semester, ccas);
    }

    public void setCcas(TaskList<Cca> ccas) {
        this.setCcas(this.getSemester(), ccas);
    }

    /**
     * Set modules and ccas for a semester.
     * @param semester selected semester
     * @param modules module list
     * @param ccas cca list
     */
    public void setModulesAndCcas(int semester, TaskList<ModuleTask> modules, TaskList<Cca> ccas) {
        this.setModules(semester, modules);
        this.setCcas(semester, ccas);
    }

    public void setModulesAndCcas(TaskList<ModuleTask> modules, TaskList<Cca> ccas) {
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
     * @param plannerUi planner Ui
     * @param store storage
     * @param jsonWrapper json wrapper
     * @param profile user profile
     */
    private static void reset(HashMap<String, ModuleInfoDetailed> detailedMap,
                              PlannerUi plannerUi,
                              Storage store,
                              JsonWrapper jsonWrapper,
                              User profile) {
        HashMap<String, Object> argsMap = new HashMap<>();
        argsMap.put("command", "clear");
        argsMap.put("toClear", "data");
        Arguments args = new Arguments(argsMap);
        new ClearCommand(args).execute(detailedMap, plannerUi, store, jsonWrapper, profile);
    }

    /**
     * Reset all.
     */
    public void clear() {
        this.setSemester(0);
        this.init();
        this.password = null;
        this.saveProfile();
    }
}
