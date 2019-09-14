package wallet.storage;

import wallet.task.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Storage {
    /**
     * The path where the save file is located in the local computer.
     */
    private String path;

    /**
     * Constructs a new Storage object.
     * @param path The path of the save file in the local computer.
     */
    public Storage(String path){
        this.path = path;
    }

    /**
     * Attempts to load the save file on the local computer and populate the task list with its data.
     *
     * @return The list of task loaded from save file
     */
    public List<Task> loadFile(){
        List<Task> taskList = new ArrayList<Task>();

        try {
            RandomAccessFile raf = new RandomAccessFile(path, "r");
            String str;
            while (raf.getFilePointer() != raf.length()){
                str = raf.readLine();
                String[] strArr = str.split(",");
                String[] info;
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy h:mma");
                if (strArr[0].trim().equals("T")){
                    Todo todo = new Todo(strArr[2].trim());
                    if (strArr[1].trim().equals("1")){
                        todo.markAsDone();
                    }
                    taskList.add(todo);
                } else if (strArr[0].trim().equals("D")){
                    Date date = sdf.parse(strArr[3].trim());
                    Deadline deadline = new Deadline(strArr[2].trim(), date);
                    if (strArr[1].trim().equals("1")){
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
                }
                else if (strArr[0].trim().equals("DW")) {
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
        } catch (FileNotFoundException e){
            System.out.println("No saved tasks found.");
        } catch (IOException e){
            System.out.println("End of file.");
        } catch (ParseException e){
            System.out.println("Wrong date/time format.");
        }

        return taskList;
    }

    /**
     * Attempts to write newly added task into a save file on the local computer.
     *
     * @param task The new task that is added
     * @param type The type of task added
     */
    public void writeFile(Task task, String type){
        try {
            RandomAccessFile raf = new RandomAccessFile(path, "rws");
            raf.seek(raf.length());
            if (type.equals("todo")) { type = "T"; }
            else if (type.equals("event")) { type = "E"; }
            else if (type.equals("deadline")) { type = "D";}
            if (raf.getFilePointer() != 0) {
                raf.writeBytes("\r\n");
            }
            raf.writeBytes(task.writeToFile());
            raf.close();
        } catch (IOException e){
            System.out.println("IOException: " + e.getMessage());
        }
    }

    /**
     * Updates the save file on the local computer when a task is marked as done.
     *
     * @param task The task to be marked as done
     * @param index The index of the task in the list
     */
    public void updateFile(Task task, int index){
        try {
            RandomAccessFile raf = new RandomAccessFile(path, "rws");
            while (index != 0){
                raf.readLine();
                index--;
            }
            raf.seek(raf.getFilePointer() + 2);
            raf.writeBytes("1");
            raf.close();
        } catch (IOException e){
            System.out.println("IOException: " + e.getMessage());
        }
    }

    /**
     * Updates the save file on the local computer when a task is deleted.
     *
     * @param taskList The list of task to update
     * @param index The index of the task in the list to be removed
     */
    public void removeTask(List<Task> taskList, int index){
        try {
            RandomAccessFile raf = new RandomAccessFile(path, "rws");
            int counter = index;
            while (counter != 0){
                raf.readLine();
                counter--;
            }
            long start = raf.getFilePointer();
            while (index != taskList.size()){
                raf.writeBytes(taskList.get(index).writeToFile());
                index++;
                if (index != taskList.size())
                    raf.writeBytes("\r\n");
            }
            raf.setLength(raf.getFilePointer());
            raf.close();
        } catch (IOException e){
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
