package oof.model.module;

import java.time.DayOfWeek;

/**
 * Represents a Lesson class for module lessons.
 */
public class Lesson {

    private String lessonName;
    private String location;
    private DayOfWeek day;
    private String startTime;
    private String endTime;

    /**
     * Constructor for Lesson object.
     *
     * @param lessonName String containing Lesson name.
     * @param location   String containing Lesson location.
     * @param day        String containing day of Lesson.
     * @param startTime  String containing start time of Lesson.
     * @param endTime    String containing end time of Lesson.
     */
    public Lesson(String lessonName, String location, DayOfWeek day, String startTime, String endTime) {
        this.lessonName = lessonName;
        this.location = location;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getLessonTimeString() {
        return startTime + "-" + endTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getLocation() {
        return location;
    }

    public String getLessonName() {
        return lessonName;
    }
}
