package duke.Module;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class Goal {

    private String filePath;
    private Scanner fileInput;

    /**
     * A list which holds the optional goal of the day for any day.
     */
    private HashMap<Date,String> goals;

    public Goal(String filePath) throws FileNotFoundException, ParseException {
        this.filePath = filePath;
        File f = new File(filePath);
        fileInput = new Scanner(f);
        this.goals = loadGoal();
    }

    /**
     * Reads filePath, takes in Strings and turns them into a hash map of goals.
     */
    public HashMap<Date,String> loadGoal() throws ParseException {
        try {
            HashMap<Date,String> temp = new HashMap<>();
            while (fileInput.hasNextLine()) {
                String s1 = fileInput.nextLine();
                String[] data = s1.split("-");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = simpleDateFormat.parse(data[0]);
                temp.put(date,data[1]);
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
     * @throws IOException io
     */
    public void updateGoal(HashMap<Date,String> goals) {
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
            Iterator it = goals.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                fileWriter.write(df.format(pair.getKey()) + "-" + pair.getValue() + "\n");
                it.remove();
            }
            fileWriter.close();
        } catch (IOException io) {
                System.out.println("File not found:" + io.getMessage());
        }
    }

    public String addGoal(String date, String message) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = simpleDateFormat.parse(date);
        goals.put(today, message);
        updateGoal(goals);
        return "New goal of the day has been added";
    }

    public String removeGoal(String day) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = simpleDateFormat.parse(day);
        for (Date d : goals.keySet()) {
            if (d.equals(today)) {
                goals.remove(d);
            }
        }
        updateGoal(goals);
        return "Goal of the day on " + day + " has been removed";
    }

    public String viewGoal(String day) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = simpleDateFormat.parse(day);
        String message = "";
        boolean hasGoal = false;
        for (Date d : goals.keySet()) {
            if (d.equals(today)) {
                if (!goals.get(d).isEmpty()) {
                    hasGoal = true;
                    message += goals.get(d) + "\n";
                }
            }
        }
        if (!hasGoal) {
            return "There is no goal of the day";
        } else {
            return message;
        }
    }
}
