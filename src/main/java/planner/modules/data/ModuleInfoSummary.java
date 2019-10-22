package planner.modules.data;

import java.util.Arrays;

public class ModuleInfoSummary {

    private String moduleCode = "";
    private String title = "";
    private int[] semesters = {0};
    private boolean isDone = false;
    private boolean suOption = false;

    /**
     * TODO JavaDocs.
     */
    public void moduleInfoSummary(String moduleCode, String title, int[] semesters, boolean suOption) {
        this.moduleCode = moduleCode;
        this.title = title;
        this.semesters = semesters;
        this.isDone = false;
        this.suOption = suOption;
    }


    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int[] getSemesters() {
        return semesters;
    }

    public void setSemesters(int[] semesters) {
        this.semesters = semesters;
    }

    public void setSuOption(boolean suOption) {
        this.suOption = suOption;
    }

    public boolean getSuOption() {
        return this.suOption;
    }

    public void setDone() {
        this.isDone = true;
    }

    public boolean getDone() {
        return this.isDone;
    }

    @Override
    public String toString() {
        return "[moduleCode: " + moduleCode + ", title:" + title + ", semesters:" + Arrays.toString(semesters) + "]";
    }


}
