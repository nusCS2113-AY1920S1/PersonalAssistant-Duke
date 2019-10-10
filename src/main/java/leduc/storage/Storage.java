package leduc.storage;

import leduc.Date;
import leduc.Parser;
import leduc.Ui;
import leduc.exception.FileException;
import leduc.exception.NonExistentDateException;
import leduc.task.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Represents a leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
 */
public class Storage {
    private File file;

    /**
     * Constructor of leduc.storage.Storage
     * @param file String representing the path of the file
     */
    public Storage(String file){
        this.file = new File(file);
        try {
            this.file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * read the file and write all the task to an array of task.
     * if the file is empty, the array is empty too
     * @return an array of task
     * @throws FileException thrown when there is a reading error of the file
     */
    public List<Task> load() throws FileException { // load the initial data file
        Scanner sc = null;
        try {
            sc = new Scanner(this.file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new FileException();
        }
        ArrayList<Task> tasks = new ArrayList<>();
        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] tokens = line.split("//");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", Locale.ENGLISH);
            switch (tokens[0]){
                case "T" :
                    tasks.add(new TodoTask(tokens[2], tokens[1].trim()));
                    break;
                case "D" :
                    tasks.add(new DeadlinesTask(tokens[2],tokens[1].trim(), new Date(LocalDateTime.parse(tokens[3], formatter))));
                    break;
                case "E":
                    tasks.add(new EventsTask(tokens[2], tokens[1].trim(), new Date(LocalDateTime.parse(tokens[3], formatter)), new Date(LocalDateTime.parse(tokens[4], formatter))));
                    break;
            }
        }
        return tasks;
    }

    /**
     * write all task to the files
     * @param tasks all the tasks that have to be written to the file
     * @throws FileException thrown when there is writing problem to the files
     */
    public void save(ArrayList<Task> tasks) throws FileException {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(this.file);
            try {

                for (Task task : tasks){
                    if (task.isTodo()) {
                        fileWriter.write("T//"+ task.getMark() +"//" + task.getTask() + "\n");
                    } else if (task.isDeadline()) {
                        fileWriter.write("D//"+ task.getMark() +"//" + task.getTask() + "//" + ((DeadlinesTask) task).getDeadlines().toString()+ "\n");
                    } else if (task.isEvent()) {
                        fileWriter.write("E//"+ task.getMark() +"//" + task.getTask() + "//" + ((EventsTask) task).getDateFirst().toString() + "//" + ((EventsTask) task).getDateSecond().toString() + "\n");
                    }
                }
            } finally {
                fileWriter.close();
            }
        }
        catch(IOException e){
            e.printStackTrace();
            throw new FileException();
        }
    }
}
