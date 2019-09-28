package duke.Data;

import duke.Sports.MyClass;
import duke.Task.*;

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * Storage handles all the loading and saving of data
 * from and into the duke.txt file respectively.
 */
public class Storage {
    private String filePath;
    private Scanner fileInput;
    private ArrayList<Item> oldList = new ArrayList<>();

    public Storage(String filePath) throws FileNotFoundException {
        this.filePath = filePath;
        File f = new File(filePath);
        fileInput = new Scanner(f);
    }

    public String dateRevert(String date) {
        try {
            Date newDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(date);
            String oldDateFormat = new SimpleDateFormat("dd/MM/yyyy HHmm").format(newDateFormat);
            return oldDateFormat;
        } catch (ParseException pe) {
            System.err.println("Error: Date in wrong format");
            return date;
        }
    }

    /**
     * This function parses the info of the duke.txt into an ArrayList.
     * @return ArrayList containing all the parsed data from the duke.txt file
     * @throws FileNotFoundException          e
     * @throws ArrayIndexOutOfBoundsException e
     */
    public ArrayList<Item> loadFile() {
        try {
            while (fileInput.hasNextLine()) { //do something
                String type, info;
                Boolean stat;
                String s1 = fileInput.nextLine();
                String[] data = s1.split("-");
                type = data[0];
                stat = (data[1].equals("1"));

                switch (type) {
                case "D":
                    Item deadline = new Deadline(data[2], stat, dateRevert(data[3]));
                    oldList.add(deadline);
                    break;

                case "E":
                    Item event = new Event(data[2], stat, dateRevert(data[3]));
                    oldList.add(event);
                    break;

                case "T":
                    Item todo = new ToDo(data[2], stat, dateRevert(data[3]));
                    oldList.add(todo);
                    break;

                case "A":
                    Item after = new After(data[2], stat, dateRevert(data[3]));
                    oldList.add(after);
                    break;

                case "C":
                    Item myclass = new MyClass(data[2], stat, data[3]);
                    oldList.add(myclass);
                    break;

                default:
                    System.out.println("No data");
                }
            }
            fileInput.close();
            return oldList;
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
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
                fileWriter.write(type + "-" + e.checkStatus() + "-" + e.getInfo() + "\n");
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
}
