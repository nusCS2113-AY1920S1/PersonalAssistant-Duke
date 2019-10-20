package planner.modules.data;

public class ExamInfo {

    private int semester = 0;
    private String examDate = "";
    private int examDuration = 0;

    public int getSemester() {
        return semester;
    }

    public String getExamDate() {
        return examDate;
    }

    public int getExamDuration() {
        return examDuration;
    }

    @Override
    public String toString() {
        return "{Semester:" + getSemester() + ", " + getExamDate() + " ," + getExamDuration();
    }
}
