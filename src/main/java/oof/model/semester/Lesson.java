package oof.model.semester;

import java.time.DayOfWeek;

/**
 * Represents a Lesson class for module lessons.
 */
public class Lesson {

    private static final String DELIMITER = "||";
    private String moduleCode;
    private String lessonName;
    private DayOfWeek day;
    private String startTime;
    private String endTime;

    /**
     * Constructor for Lesson object.
     *
     * @param moduleCode String containing Module code.
     * @param lessonName String containing Lesson name.
     * @param day        Day of the week.
     * @param startTime  String containing start time of Lesson.
     * @param endTime    String containing end time of Lesson.
     */
    public Lesson(String moduleCode, String lessonName, DayOfWeek day, String startTime, String endTime) {
        this.moduleCode = moduleCode;
        this.lessonName = lessonName;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getLessonTimeString() {
        return startTime + " to " + endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getLessonName() {
        return lessonName;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public String getDayString() {
        return day.toString();
    }

    public String getDescription() {
        return moduleCode + " " + lessonName;
    }

    /**
     * Converts a Lesson object to string format for storage.
     *
     * @return Lesson object in string format for storage.
     */
    public String toStorageString() {
        return "LESSON" + DELIMITER + moduleCode + DELIMITER + lessonName + DELIMITER + day.name() + DELIMITER
                + startTime + DELIMITER + endTime;
    }

}
