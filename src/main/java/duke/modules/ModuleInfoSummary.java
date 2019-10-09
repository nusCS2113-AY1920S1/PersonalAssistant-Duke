package duke.modules;

import java.util.Arrays;

public class ModuleInfoSummary {

    private String moduleCode;
    private String title;
    private int[] semesters;

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

    @Override
    public String toString() {
        return "[moduleCode: " + moduleCode + ", title:" + title + ", semesters:" + Arrays.toString(semesters) + "]";
    }


}
