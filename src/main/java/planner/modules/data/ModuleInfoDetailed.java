package planner.modules.data;

import planner.exceptions.original.ModBadGradeException;
import planner.exceptions.original.ModException;

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

    public String getPrerequisites() {return  prerequisites;}

    public Attributes getAttributes() {
        return attributes;
    }

    public ExamInfo[] getSemesterData() {
        return semesterData;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String score) {
        try {
            if (score.equalsIgnoreCase("S") || score.equalsIgnoreCase("U")) {
                if (this.attributes.isSu()) {
                    this.grade = score;
                } else {
                    throw new ModBadGradeException();
                }
            }
            this.grade = score;
        } catch (ModBadGradeException e) {
            System.out.println(e.getMessage());
        }
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
