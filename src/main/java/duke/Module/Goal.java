package duke.Module;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Goal {

    private String filePath;
    private Scanner fileInput;

    /**
     * A hash map which holds the optional goal of the day for any day.
     */
    private Map<Date, ArrayList<String>> goals;

    /**
     * Constructor for Goal objects.
     * @param filePath The path of the file goals.txt
     * @throws FileNotFoundException if the file specified by the filepath cannot be found.
     * @throws ParseException if the user input is in wrong format.
     */
    public Goal(String filePath) throws FileNotFoundException, ParseException {
        this.filePath = filePath;
        File f = new File(filePath);
        fileInput = new Scanner(f);
        this.goals = loadGoal();
    }

    /**
     * Reads filePath, takes in Strings and turns them into a hash map of goals.
     * @return A hash map of goals.
     * @throws ParseException if the user input is in wrong format.
     */
    public Map<Date,ArrayList<String>> loadGoal() throws ParseException {
        try {
            Map<Date,ArrayList<String>> temp = new HashMap<>();
            while (fileInput.hasNextLine()) {
                String s1 = fileInput.nextLine();
                String[] data = s1.split("-");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = simpleDateFormat.parse(data[0]);
                ArrayList<String> temp2 = new ArrayList<>();
                for (String str : data) {
                    if (!str.equals(data[0])) {
                        temp2.add(str);
                    }
                }
                temp.put(date,temp2);
            }
            fileInput.close();
            return temp;
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * This function updates the hash map of goals.
     * Erases the entire hash map that exists presently and rewrites the file.
     * @param goals The updated hash map that must be used to recreate the updated goals.txt
     * @throws IOException io if the file cannot be found.
     */
    public void updateGoal(Map<Date,ArrayList<String>> goals) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException io) {
            System.out.println("File not found:" + io.getMessage());
        }

        try {
            FileWriter fileWriter = new FileWriter(filePath, true);
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            for (Map.Entry<Date,ArrayList<String>> entry : goals.entrySet()) {
                String extra = "";
                ArrayList<String> temp = entry.getValue();
                for (String str : temp) {
                    extra += "-" + str;
                }
                fileWriter.write(df.format(entry.getKey()) + extra + "\n");
            }
            fileWriter.close();
        } catch (IOException io) {
            System.out.println("File not found:" + io.getMessage());
        }
    }

    /**
     * Shows all goals on a specific day.
     * @param day The day to view all goals.
     * @return A message containing all the goals of the day to be printed.
     * @throws ParseException if the user input is in wrong format.
     */
    public String viewGoal(String day) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = simpleDateFormat.parse(day);
        String message = "";
        boolean hasGoal = false;
        for (Date d : goals.keySet()) {
            if (d.equals(today)) {
                if (!goals.get(d).isEmpty()) {
                    hasGoal = true;
                    for (String str : goals.get(d)) {
                        message += str + "\n";
                    }
                }
            }
        }
        if (!hasGoal) {
            return "There is no goal of the day";
        } else {
            return message;
        }
    }

    /**
     * Adds a goal to the goals hash map.
     * @param date The date to add the goal to.
     * @param message The goal to add to the goals hash map.
     * @return A message showing task completed successfully.
     * @throws ParseException if the user input is in wrong format.
     */
    public String addGoal(String date, String message) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = simpleDateFormat.parse(date);
        boolean alreadyHaveDate = false;
        for (Date d : goals.keySet()) {
            if (d.equals(today)) {
                alreadyHaveDate = true;
                goals.get(d).add(message);
            }
        }
        if (!alreadyHaveDate) {
            ArrayList<String> temp = new ArrayList<>();
            temp.add(message);
            goals.put(today, temp);
        }
        updateGoal(goals);
        return "New goal of the day has been added";
    }

    /**
     * Removes a goal from the goals hash map.
     * @param day The date to remove the goal from.
     * @param message The specific goal to remove from the hash map.
     * @return A message showing task completed successfully.
     * @throws ParseException if the user input is in wrong format.
     */
    public String removeGoal(String day, String message) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = simpleDateFormat.parse(day);
        if (goals.containsKey(today)) {
            goals.get(today).remove(message);
            if (goals.get(today).isEmpty()) {
                goals.remove(today);
            }
        }
        updateGoal(goals);
        return "Goal of the day on " + day + " has been removed";
    }

    /**
     * Removes all the goals from the goals hash map for a day.
     * @param day The date to remove all the goals from.
     * @return A message showing task completed successfully.
     * @throws ParseException if the user input is in wrong format.
     */
    public String removeAllGoal(String day) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = simpleDateFormat.parse(day);
        if (goals.containsKey(today)) {
            goals.remove(today);
            updateGoal(goals);
            return "All the goals for the day " + day + " have been cleared";
        } else {
            return "There are no goals of the day to remove for " + day;
        }
    }
}
