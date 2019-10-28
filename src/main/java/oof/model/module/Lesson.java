package oof.model.module;

import java.time.DayOfWeek;

/**
 * Represents a Lesson class for module lessons.
 */
public class Lesson {

    private static final String DELIMITER = "||";
    private String lessonName;
    private String location;
    private DayOfWeek day;
    private String startTime;
    private String endTime;

    /**
     * Constructor for Lesson object.
     *
     * @param lessonName String containing Lesson name.
     * @param startTime  String containing start time of Lesson.
     * @param endTime    String containing end time of Lesson.
     */
    public Lesson(String lessonName, String startTime, String endTime) {
        this.lessonName = lessonName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getLessonTimeString() {
        return startTime + " to " + endTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
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

    /**
     * Converts a Lesson object to string format for storage.
     * @return Lesson object in string format for storage.
     */
    public String toStorageString() {
        return "L" + DELIMITER + lessonName + DELIMITER + startTime + DELIMITER + endTime;
    }

}
