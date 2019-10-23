package planner.modules.data;

import planner.exceptions.original.ModBadGradeException;
import planner.exceptions.original.ModBadSuException;

import java.util.ArrayList;
import java.util.Arrays;

public class ModuleInfoDetailed {

    private String moduleCode = "";
    private String title = "";
    private String description = "";
    private String moduleCredit = "";
    private String department = "";
    private String faculty = "";
    private String preclusion = "";
    private String prerequisites = "";
    private Attributes attributes = new Attributes();
    private String grade = "";
    private ExamInfo[] semesterData;
    private ArrayList<String> validGrades = new ArrayList<String>(Arrays.asList("A+", "A", "A-", "B+", "B",
        "B-", "C+", "C", "D+", "D", "F", "S", "U"));


    public String getModuleCode() {
        return moduleCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getModuleCredit() {
        return Integer.parseInt(moduleCredit);
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
        return  prerequisites;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public ExamInfo[] getSemesterData() {
        return semesterData;
    }

    public String getGrade() {
        return grade;
    }

    /**
     * Checks if module is S/U-able, and assigns grade based on String score.
     */
    public void setGrade(String score) throws ModBadGradeException, ModBadSuException {
        if (!validGrades.contains(score)) {
            throw new ModBadGradeException();
        }
        if (score.equalsIgnoreCase("S") || score.equalsIgnoreCase("U")) {
            if (this.attributes.isSu()) {
                this.grade = score;
            } else {
                throw new ModBadSuException();
            }
        }
        this.grade = score;
    }

    @Override
    public String toString() {
        return "ModuleCode:"
                + getModuleCode()
                + ", MC:"
                + getModuleCredit()
                + ", SU:"
                + getAttributes().isSu()
                + ", grade:"
                + getGrade();
    }
}
