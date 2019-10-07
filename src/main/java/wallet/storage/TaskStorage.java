package wallet.storage;

import wallet.model.task.Deadline;
import wallet.model.task.DoWithinPeriod;
import wallet.model.task.Event;
import wallet.model.task.Task;
import wallet.model.task.Tentative;
import wallet.model.task.Todo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaskStorage extends Storage<Task> {
    public static final String DEFAULT_STORAGE_FILEPATH = "./data/wallet.txt";

    /**
     * Attempts to load the save file on the local computer and populate the task list with its data.
     *
     * @return The list of task loaded from save file.
     */
    @Override
    public ArrayList<Task> loadFile() {
        ArrayList<Task> taskList = new ArrayList<Task>();

        try {
            RandomAccessFile raf = new RandomAccessFile(DEFAULT_STORAGE_FILEPATH, "r");
            String str;
            while (raf.getFilePointer() != raf.length()) {
                str = raf.readLine();
                String[] strArr = str.split(",");
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy h:mma");
                if (strArr[0].trim().equals("T")) {
                    Todo todo = new Todo(strArr[2].trim());
                    if (strArr[1].trim().equals("1")) {
                        todo.markAsDone();
                    }
                    taskList.add(todo);
                } else if (strArr[0].trim().equals("D")) {
                    Date date = sdf.parse(strArr[3].trim());
                    Deadline deadline = new Deadline(strArr[2].trim(), date);
                    if (strArr[1].trim().equals("1")) {
                        deadline.markAsDone();
                    }
                    taskList.add(deadline);
                } else if (strArr[0].trim().equals("E")) {
                    Date date = sdf.parse(strArr[3].trim());
                    Event event = new Event(strArr[2].trim(), date);
                    if (strArr[1].trim().equals("1")) {
                        event.markAsDone();
                    }
                    taskList.add(event);
                } else if (strArr[0].trim().equals("*E")) {
                    //B-TentativeScheduling: Retrieve Tentative Records
                    String dateList = strArr[3];
                    String[] dateArr = dateList.split("\\|");
                    ArrayList<Date> possibleDates = new ArrayList<Date>();
                    SimpleDateFormat formatDate = new SimpleDateFormat("dd MMM yyyy h:mma");
                    for (String d : dateArr) {
                        Date entry = sdf.parse(d);
                        possibleDates.add(entry);
                    }

                    Tentative tentative = new Tentative(strArr[2].trim(), possibleDates);
                    if (strArr[1].trim().equals("1")) {
                        tentative.markAsDone();
                    }
                    taskList.add(tentative);

                } else if (strArr[0].trim().equals("DW")) {
                    Date dateStart = sdf.parse(strArr[3].trim());
                    Date dateEnd = sdf.parse(strArr[4].trim());
                    DoWithinPeriod dowithin = new DoWithinPeriod(strArr[2].trim(), dateStart, dateEnd);
                    if (strArr[1].trim().equals("1")) {
                        dowithin.markAsDone();
                    }
                    taskList.add(dowithin);
                }
            }
            raf.close();
        } catch (FileNotFoundException e) {
            System.out.println("No saved tasks found.");
        } catch (IOException e) {
            System.out.println("End of file.");
        } catch (ParseException e) {
            System.out.println("Wrong date/time format.");
        }

        return taskList;
    }

    @Override
    public void writeListToFile(ArrayList<Task> objectList) {
        //TODO: ...
    }

    /**
     * Attempts to write newly added task into a save file on the local computer.
     *
     * @param task The new task that is added.
     */
    public void writeToFile(Task task) {
        try {
            RandomAccessFile raf = new RandomAccessFile(DEFAULT_STORAGE_FILEPATH, "rws");
            raf.seek(raf.length());
            if (raf.getFilePointer() != 0) {
                raf.writeBytes("\r\n");
            }
            raf.writeBytes(task.writeToFile());
            raf.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    /**
     * Updates the save file on the local computer when a task is marked as done.
     *
     * @param task  The task to be marked as done.
     * @param index The index of the task in the list.
     */
    public void updateToFile(Task task, int index) {
        try {
            RandomAccessFile raf = new RandomAccessFile(DEFAULT_STORAGE_FILEPATH, "rws");
            while (index != 0) {
                raf.readLine();
                index--;
            }

            //Added Codes to cater to Tentative
            String outputString = task.toString();
            String type = outputString.substring(1, 3);
            if (type.equals("*E")) {
                raf.seek(raf.getFilePointer() + 3);
            } else {
                raf.seek(raf.getFilePointer() + 2);
            }

            raf.writeBytes("1");
            raf.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    /**
     * Updates the save file on the local computer when a task is deleted.
     *
     * @param taskList The list of task to update.
     * @param index    The index of the task in the list to be removed.
     */
    public void removeFromFile(ArrayList<Task> taskList, int index) {
        try {
            RandomAccessFile raf = new RandomAccessFile(DEFAULT_STORAGE_FILEPATH, "rws");
            int counter = index;
            while (counter != 0) {
                raf.readLine();
                counter--;
            }
            long start = raf.getFilePointer();
            while (index != taskList.size()) {
                raf.writeBytes(taskList.get(index).writeToFile());
                index++;
                if (index != taskList.size()) {
                    raf.writeBytes("\r\n");
                }
            }
            raf.setLength(raf.getFilePointer());
            raf.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
