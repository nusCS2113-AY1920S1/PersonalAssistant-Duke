package duke.Data;

import duke.Task.*;

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Storage handles all the loading and saving of data
 * from and into the respective text files.
 */
public class Storage {
    private String filePath;
    private Scanner fileInput;

    public Storage(String filePath) throws FileNotFoundException {
        this.filePath = filePath;
        File f = new File(filePath);
        fileInput = new Scanner(f);
    }

    /**
     * This function saves the newly created task into duke.txt.
     * @param type The type of task created
     * @param e    The task created to be saved
     * @param date The date of the task created
     * @throws IOException io
     */
    public void saveFile(String type, Item e, String date) {
        try {
            if (type.equals("T")) {
                FileWriter fileWriter = new FileWriter(filePath, true);
                fileWriter.write(type + "-" + e.checkStatus() + "-" + e.getInfo() + "-" + e.getDuration() + "\n");
                fileWriter.close();
            } else if (type.equals("C")) {
                FileWriter fileWriter = new FileWriter(filePath, true);
                fileWriter.write(type + "-" + e.checkStatus() + "-" + e.getInfo() + "-" + date + "\n");
                fileWriter.close();
            } else {
                FileWriter fileWriter = new FileWriter(filePath, true);
                fileWriter.write(type + "-" + e.checkStatus() + "-"
                    + e.getInfo() + "-" + e.getRawDate() + "\n");
                fileWriter.close();
            }
        } catch (IOException io) {
            System.out.println("File not found:" + io.getMessage());
        }
    }

    /**
     * This function updates the list of tasks.
     * Erases the entire list that exists presently and rewrites the file.
     * @param up The updated ArrayList that must be used to recreate the updated duke.txt
     * @throws IOException io
     */
    public void updateFile(ArrayList<Item> up) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException io) {
            System.out.println("File not found:" + io.getMessage());
        }

        for (Item i : up) {
            try {
                FileWriter fileWriter = new FileWriter(filePath, true);
                fileWriter.write(i.getType() + "-" + i.checkStatus() + "-" + i.getInfo() + "-" + i.getRawDate() + "\n");
                fileWriter.close();
            } catch (IOException io) {
                System.out.println("File not found:" + io.getMessage());
            }
        }
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
}
