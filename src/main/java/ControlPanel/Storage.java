package ControlPanel;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Tasks.*;

public class Storage {

    private String fileName;
    private SimpleDateFormat simpleDateFormat;
    public  Storage (String filePath){
        fileName = filePath;
        simpleDateFormat  = new SimpleDateFormat("d/M/yyyy HHmm");
    }

    public ArrayList<Task> load() {
        ArrayList<Task> checkList = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.replace('|', '@');
                String[] info = line.split(" @ ");
                if (!(info[0].equals("T") || info[0].equals("D") || info[0].equals("E"))) {
                    throw new DukeException("This is not a valid input from the file!!!");
                }
                Task t = new Task("default");
                switch (info[0]) {
                    case "T":
                        t = new ToDos(info[2]);
                        break;
                    case "D":
                        Date deadlineDate = simpleDateFormat.parse(info[3]);
                        t = new Deadline(info[2], deadlineDate);
                        break;
                    case "E":
                        Date eventDate = simpleDateFormat.parse(info[3]);
                        t = new Events(info[2], eventDate);
                        break;
                }
                if (t.getDescription().equals("default")) {
                    throw new DukeException("This task is not refreshed.");
                }
                if (info[1].equals("1")) {
                    t.markAsDone();
                }
                checkList.add(t);
            }
            bufferedReader.close();
        } catch (IOException | DukeException | ParseException e) {
            e.printStackTrace();
        }
        return checkList;
    }

    public void writeTheFile(ArrayList<Task> taskList) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("");
            for (Task t : taskList) {
                if (t instanceof ToDos) {
                    if (t.getStatus())
                        bufferedWriter.write("T | 1 | " + t.getDescription() + "\n");
                    else
                        bufferedWriter.write("T | 0 | " + t.getDescription() + "\n");
                } else if (t instanceof Events) {
                    if (t.getStatus())
                        bufferedWriter.write("E | 1 | " + t.getDescription() + " | "
                                + ((Events) t).getAt() + "\n");
                    else
                        bufferedWriter.write("E | 0 | " + t.getDescription() + " | "
                                + ((Events) t).getAt() + "\n");
                } else if (t instanceof Deadline) {
                    if (t.getStatus())
                        bufferedWriter.write("D | 1 | " + t.getDescription() + " | "
                                + ((Deadline) t).getBy() + "\n");
                    else
                        bufferedWriter.write("D | 0 | " + t.getDescription() + " | "
                                + ((Deadline) t).getBy() + "\n");

                }
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
