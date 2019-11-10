//@@author namiwa

package planner.logic.modules.module;

import java.util.ArrayList;
import java.util.Arrays;

import planner.logic.exceptions.planner.ModBadGradeException;
import planner.logic.exceptions.planner.ModBadSuException;

public class ModuleInfoDetailed {

    private String moduleCode = "";
    private String title = "";
    private String description = "";
    private String moduleCredit = "";
    private String department = "";
    private String faculty = "";
    private String preclusion = "";
    private String prerequisite = "";
    private Attributes attributes = new Attributes();
    private String grade = "";
    private ExamInfo[] semesterData = new ExamInfo[0];
    private String[] semester = {""};
    private ArrayList<String> validGrades = new ArrayList<>(Arrays.asList("A+", "A", "A-", "B+", "B",
        "B-", "C+", "C", "D+", "D", "F", "S", "U", "CS", "CU"));


    public String getModuleCode() {
        return moduleCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Double getModuleCredit() throws NumberFormatException {
        return Double.parseDouble(moduleCredit);
    }

    public String getDepartment() {
        return department;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getPreclusion() {
        return preclusion;
    }

    public String getPrerequisites() {
        return prerequisite;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public ExamInfo[] getSemesterData() {
        return semesterData;
    }

    public String[] getSemester() {
        return semester;
    }

    //@@author andrewleow97
    public void setModuleCredit(String moduleCredit) {
        this.moduleCredit = moduleCredit;
    }

    //@@author e0313687
    public String getGrade() {
        return grade;
    }

    public String getModuleLevel() {
        return moduleCode.replaceAll("[^0-9]", "");
    }

    //@@author andrewleow97
    /**
     * Checks if module is S/U-able, and assigns grade based on String score.
     */
    public void setGrade(String score) throws ModBadGradeException, ModBadSuException {
        if (score.equalsIgnoreCase("S")
            ||
            score.equalsIgnoreCase("U")
            ||
            score.equalsIgnoreCase("CS")
            ||
            score.equalsIgnoreCase("CU")) {
            if (this.attributes.isSu()) {
                this.grade = score;
            } else {
                throw new ModBadSuException();
            }
        } else if (!this.validGrades.contains(score)) {
            throw new ModBadGradeException();
        } else {
            this.grade = score;
        }
    }

    @Override
    public String toString() {
        return "ModuleCode:"
                + getModuleCode()
                + ", MC:"
                + getModuleCredit()
                + ", SU:"
                + getAttributes().convertSu()
                + ", grade:"
                + getGrade();
    }
}
