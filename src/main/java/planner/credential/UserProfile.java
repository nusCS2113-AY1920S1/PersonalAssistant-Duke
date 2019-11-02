//@@author LongLeCE

package planner.credential;

import planner.logic.modules.cca.CcaList;
import planner.logic.modules.legacy.task.Task;
import planner.logic.modules.module.ModuleTask;
import planner.ui.cli.PlannerUi;
import planner.util.storage.Storage;

import java.util.HashMap;
import java.util.List;

public class UserProfile {
    private String userName;
    private String password;
    private int currentSemester;
    private HashMap<Integer, HashMap<String, List<? extends Task>>> modulesAndCcas;
    private static Hasher hasher = new Hasher();
    private static Storage storage = new Storage();
    @SuppressWarnings("unchecked")
    private static HashMap<String, HashMap<String, UserProfile>> users =
            storage.readGson("data/users.json", HashMap.class);

    static {
        if (users == null) {
            UserProfile.users = new HashMap<>();
            UserProfile.users.put("users", new HashMap<>());
        }
    }

    static UserProfile createProfile(String userName, String password, PlannerUi plannerUi) {
        if (UserProfile.userExists(userName)) {
            return UserProfile.loadProfile(userName, password);
        }
        UserProfile userProfile = new UserProfile();
        userProfile.userName = userName;
        userProfile.password = UserProfile.hasher.getHashString(password);
        int year = plannerUi.yearPrompt();
        int semester = plannerUi.semesterPrompt();
        userProfile.currentSemester = (year << 1) + semester - 2;
        return userProfile;
    }

    private static UserProfile loadProfile(String userName, String password) {
        UserProfile userProfile = UserProfile.users.get("users").get(userName);
        if (UserProfile.isValidPassword(password, userProfile.password)) {
            return userProfile;
        }
        return null;
    }

    public void saveProfile() {
        UserProfile.users.get("users").put(this.getUserName(), this);
        storage.writeGson(UserProfile.users, "data/users.json");
    }

    public static boolean userExists(String userName) {
        return UserProfile.users.get("users").containsKey(userName);
    }

    public static boolean isValidPassword(String password, String passwordHash) {
        return passwordHash.equals(UserProfile.hasher.getHashString(password));
    }

    public String getUserName() {
        return this.userName;
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
        HashMap<String, List<? extends Task>> modulesMap = new HashMap<>();
        modulesMap.put(type, tasks);
        this.modulesAndCcas.put(semester, modulesMap);
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
}
