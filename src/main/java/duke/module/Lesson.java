package duke.module;

import duke.data.Storage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class Lesson {
    /**
     * A hash map which holds the optional lessons learnt for any day.
     */
    private Map<Date, ArrayList<String>> lessons;

    /**
     * Constructor for Lesson objects.
     * @param dateArrayListMap The hash map of lessons learnt for the day from loading the lessons.txt text file.
     */
    public Lesson(Map<Date, ArrayList<String>> dateArrayListMap) {
        this.lessons = dateArrayListMap;
    }

    /**
     * Shows all lessons learnt for a specific day.
     * @param day The day to view all lessons learnt.
     * @return A message containing all the lessons learnt for the day to be printed.
     * @throws ParseException if the user input is in wrong format.
     */
    public String viewLesson(String day) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = simpleDateFormat.parse(day);
        String message = "";
        boolean hasLesson = false;
        for (Date d : lessons.keySet()) {
            if (d.equals(today)) {
                if (!lessons.get(d).isEmpty()) {
                    hasLesson = true;
                    for (String str : lessons.get(d)) {
                        message += str + "\n";
                    }
                }
            }
        }
        if (!hasLesson) {
            return "There is no lesson learnt for the day";
        } else {
            return message;
        }
    }

    /**
     * Adds a lesson learnt to the lessons hash map.
     * @param date The date to add the lesson learnt to.
     * @param message The lesson learnt to add to the lessons hash map.
     * @return A message showing task completed successfully.
     * @throws ParseException if the user input is in wrong format.
     */
    public String addLesson(String date, String message, Storage lessonStorage) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = simpleDateFormat.parse(date);
        boolean alreadyHaveDate = false;
        for (Date d : lessons.keySet()) {
            if (d.equals(today)) {
                alreadyHaveDate = true;
                lessons.get(d).add(message);
            }
        }
        if (!alreadyHaveDate) {
            ArrayList<String> temp = new ArrayList<>();
            temp.add(message);
            lessons.put(today, temp);
        }
        lessonStorage.updateLesson(lessons);
        return "New lesson learnt for the day has been added";
    }

    /**
     * Removes a lesson learnt from the lessons hash map.
     * @param day The date to remove the lesson learnt from.
     * @param message The specific lesson learnt to remove from the hash map.
     * @return A message showing task completed successfully.
     * @throws ParseException if the user input is in wrong format.
     */
    public String removeLesson(String day, String message, Storage lessonStorage) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = simpleDateFormat.parse(day);
        if (lessons.containsKey(today)) {
            lessons.get(today).remove(message);
            if (lessons.get(today).isEmpty()) {
                lessons.remove(today);
            }
        }
        lessonStorage.updateLesson(lessons);
        return "Lesson learnt for the day, " + day + ", has been removed";
    }

    /**
     * Removes all the lessons learnt from the lessons hash map for a day.
     * @param day The date to remove all the lessons learnt from.
     * @return A message showing task completed successfully.
     * @throws ParseException if the user input is in wrong format.
     */
    public String removeAllLesson(String day, Storage lessonStorage) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = simpleDateFormat.parse(day);
        if (lessons.containsKey(today)) {
            lessons.remove(today);
            lessonStorage.updateLesson(lessons);
            return "All the lessons learnt for the day " + day + " have been cleared";
        } else {
            return "There are no lessons learnt for the day, " + day + ", to remove.";
        }
    }
}

