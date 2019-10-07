package controlpanel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import tasks.Deadline;
import tasks.Events;
import tasks.ToDos;
import tasks.Periods;
import tasks.FixedDuration;
import tasks.Task;
import tasks.MultipleEvent;
import javafx.util.Pair;

public class Storage {

    private String fileName;
    private SimpleDateFormat simpleDateFormat;

    public Storage(String filePath) {
        fileName = filePath;
        simpleDateFormat  = new SimpleDateFormat("d/M/yyyy HHmm");
    }

    /**
     * This method is used to read a txt file and store it in an Array List that contains all the tasks.
     * @return checkList the Array List that stores the tasks.
     */
    public ArrayList<Task> load() {
        ArrayList<Task> checkList = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.replace('|', '@');
                String[] info = line.split(" @ ");
                if (!(info[0].equals("T") || info[0].equals("D") || info[0].equals("E")
                        || info[0].equals("P") || info[0].equals("F") || info[0].equals("M"))) {
                    throw new DukeException("This is not a valid input from the file!!!");
                }
                Task t = new Task("default");
                switch (info[0]) {
                case "F":
                    t = new FixedDuration(info[2], info[3]);
                    break;
                case "T":
                    t = new ToDos(info[2]);
                    break;
                case "D":
                    Date deadlineDate = simpleDateFormat.parse(info[3]);
                    t = new Deadline(info[2], deadlineDate);
                    break;
                case "E":
                    Date eventStartDate = simpleDateFormat.parse(info[3]);
                    Date eventEndDate = simpleDateFormat.parse(info[4]);
                    t = new Events(info[2], eventStartDate, eventEndDate);
                    break;
                case "P":
                    Date periodStartDate = simpleDateFormat.parse(info[3]);
                    Date periodEndDate = simpleDateFormat.parse(info[4]);
                    t = new Periods(info[2], info[3], info[4]);
                    break;
                case "M":
                    String[] dateStr = info[3].split(" /or ");
                    ArrayList<Pair<Date, Date>> dates = new ArrayList<>();
                    for (String choices : dateStr) {
                        //System.out.println(choices);
                        String[] startendDate = choices.split("to ");
                        Date startDate = simpleDateFormat.parse(startendDate[0]);
                        Date endDate = simpleDateFormat.parse(startendDate[1]);
                        Pair<Date, Date> tempDate = new Pair<>(startDate, endDate);
                        dates.add(tempDate);
                    }
                    t = new MultipleEvent(info[2], dates, info[4]);
                    break;
                default:
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

    /**
     * This method is to write the Array List that contains all the tasks into a txt file.
     * @param taskList The list to be push into the txt file.
     */
    public void writeTheFile(ArrayList<Task> taskList) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("");
            for (Task t : taskList) {
                if (t instanceof  FixedDuration) {
                    if (t.getStatus()) {
                        bufferedWriter.write("F | 1 | " + t.getDescription() + "\n");
                    } else {
                        bufferedWriter.write("F | 0 | " + t.getDescription() + "\n");
                    }
                } else if (t instanceof ToDos) {
                    if (t.getStatus()) {
                        bufferedWriter.write("T | 1 | " + t.getDescription() + "\n");
                    } else {
                        bufferedWriter.write("T | 0 | " + t.getDescription() + "\n");
                    }
                } else if (t instanceof Events) {
                    if (t.getStatus()) {
                        bufferedWriter.write("E | 1 | " + t.getDescription() + " | "
                                + ((Events) t).getStartAt() + " | "
                                + ((Events) t).getEndAt() + "\n");
                    } else {
                        bufferedWriter.write("E | 0 | " + t.getDescription() + " | "
                                + ((Events) t).getStartAt() + " | "
                                + ((Events) t).getEndAt() + "\n");
                    }
                } else if (t instanceof Deadline) {
                    if (t.getStatus()) {
                        bufferedWriter.write("D | 1 | " + t.getDescription() + " | "
                                + ((Deadline) t).getBy() + "\n");
                    } else {
                        bufferedWriter.write("D | 0 | " + t.getDescription() + " | "
                                + ((Deadline) t).getBy() + "\n");
                    }
                } else if (t instanceof Periods) {
                    if (t.getStatus()) {
                        bufferedWriter.write("P | 1 | " + t.getDescription() + " | "
                                + ((Periods) t).getFrom() + " | " + ((Periods) t).getTo() + "\n");
                    } else {
                        bufferedWriter.write("P | 0 | " + t.getDescription() + " | "
                                + ((Periods) t).getFrom() + " | " + ((Periods) t).getTo() + "\n");
                    }
                } else if (t instanceof MultipleEvent) {
                    String isChosen = "0";
                    if (((MultipleEvent) t).getChosenStatus()) {
                        isChosen = "1";
                    }
                    if (t.getStatus()) {
                        String possibleDates = "";
                        for (Pair<Date, Date> date : ((MultipleEvent) t).getDates()) {
                            possibleDates += simpleDateFormat.format(date.getKey()) + " to "
                                    + simpleDateFormat.format(date.getValue()) + " /or ";
                        }
                        bufferedWriter.write("M | 1 | " + t.getDescription() + " | "
                                + possibleDates + "| " + isChosen + "\n");
                    } else {
                        String possibleDates = "";
                        for (Pair<Date, Date> date : ((MultipleEvent) t).getDates()) {
                            possibleDates += simpleDateFormat.format(date.getKey())
                                    + " to " + simpleDateFormat.format(date.getValue()) + " /or ";
                        }
                        bufferedWriter.write("M | 0 | " + t.getDescription() + " | "
                                + possibleDates + "| " + isChosen + "\n");
                    }
                }
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
