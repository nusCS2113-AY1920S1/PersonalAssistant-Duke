package duke.modules.data;

public class ExamInfo {

    private int semester;
    private String examDate;
    private int examDuration;

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
