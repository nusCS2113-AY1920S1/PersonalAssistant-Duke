package duke.modules;

import java.util.Arrays;

public class ModuleInfoSummary {

    private String moduleCode;
    private String title;
    private int[] semesters;
    private boolean isDone;
    private boolean SUOption;


    public void ModuleInfoSummary(String moduleCode, String title, int[] semesters, boolean SUOption) {
        this.moduleCode = moduleCode;
        this.title = title;
        this.semesters = semesters;
        this.isDone = false;
        this.SUOption = SUOption;
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

    public void setSUOption(boolean SUOption) {
        this.SUOption = SUOption;
    }

    public boolean getSUOption() {
        return this.SUOption;
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
