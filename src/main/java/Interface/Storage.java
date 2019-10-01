package Interface;
import Tasks.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Deals with loading or saving tasks to and from a file.
 */
public class Storage {
    private File filePath;
    private String filePathEvent;
    private String filePathTodo;
    private String filePathDeadline;
    private static String filePathTentativeDates;
    /**
     * Creates Storage object.
     */
    public Storage(){
        filePath = new File(System.getProperty("user.dir") + "\\data");
        filePath.mkdir();
        filePathEvent = System.getProperty("user.dir") + "\\data\\event.txt";
        filePathTodo = System.getProperty("user.dir") + "\\data\\todo.txt";
        filePathDeadline = System.getProperty("user.dir") + "\\data\\deadline.txt";
        filePathTentativeDates = System.getProperty("user.dir") + "\\data\\TentativeDates.txt";
    }

    /**
     * This method updates the file in the filepath with the current
     * list of tasks that the user has set.
     * @param list TaskList object with existing tasks to be saved in the file
     * @throws FileNotFoundException On file not found error using filepath
     */
    public void updateFile(TaskList list) throws FileNotFoundException {
        PrintWriter outputStream = new PrintWriter(System.getProperty("user.dir") + "\\data\\duke.txt");
        String textUiFilePath = System.getProperty("user.dir") + "\\text-ui-test\\data\\duke.txt";
        PrintWriter outputStreamTextUi = new PrintWriter(textUiFilePath);
        ArrayList<Task> temp = list.getList();
        for(Task task : temp){
            outputStream.println(task.toString());
            outputStreamTextUi.println(task.toString());
        }
        outputStream.close();
        outputStreamTextUi.close();
    }

    /**
     * This method reads the file and updates the TaskList object with tasks in the file.
     * @param list To update the TaskList object with tasks from the file
     * @throws IOException On input error reading lines in the file
     * @throws ParseException On conversion error from string to Task object
     */
    public void readFile(TaskList list) throws IOException, ParseException {
        ArrayList<String> temp = new ArrayList<>(Files.readAllLines(Paths.get(System.getProperty("user.dir") + "\\data\\duke.txt")));
        for(String string : temp){
            list.addTask(stringToTask(string));
        }
    }

    public void updateTodoList(TaskList list) throws FileNotFoundException {
        PrintWriter outputStream = new PrintWriter(filePathTodo);
        ArrayList<Task> temp = list.getList();
        for(Task task : temp){
            outputStream.println(task.toString());
        }
        outputStream.close();
    }

    public void readTodoList(TaskList list) throws IOException, ParseException {
        ArrayList<String> temp = new ArrayList<>(Files.readAllLines(Paths.get(filePathTodo)));
        for(String string : temp){
            list.addTask(stringToTask(string));
        }
    }

    public void updateEventList(TaskList list) throws FileNotFoundException {
        PrintWriter outputStream = new PrintWriter(filePathEvent);
        ArrayList<Task> temp = list.getList();
        for(Task task : temp){
            outputStream.println(task.toString());
        }
        outputStream.close();
    }

    public void readEventList(TaskList list) throws IOException, ParseException {
        ArrayList<String> temp = new ArrayList<>(Files.readAllLines(Paths.get(filePathEvent)));
        for(String string : temp){
            list.addTask(stringToTask(string));
        }
    }

    public void updateDeadlineList(TaskList list) throws FileNotFoundException {
        PrintWriter outputStream = new PrintWriter(filePathDeadline);
        ArrayList<Task> temp = list.getList();
        for(Task task : temp){
            outputStream.println(task.toString());
        }
        outputStream.close();
    }

    public void readDeadlineList(TaskList list) throws IOException, ParseException {
        ArrayList<String> temp = new ArrayList<>(Files.readAllLines(Paths.get(filePathDeadline)));
        for(String string : temp){
            list.addTask(stringToTask(string));
        }
    }
    public static void updateTentativeDates(TaskList list) throws FileNotFoundException {
        PrintWriter outputStream = new PrintWriter(filePathTentativeDates);
        ArrayList<Task> temp = list.getList();
        for(Task task : temp){
            outputStream.println(task.toString());
        }
        outputStream.close();
    }
    public void readTentativeDates(TaskList list) throws IOException, ParseException {
        ArrayList<String> temp = new ArrayList<>(Files.readAllLines(Paths.get(filePathTentativeDates)));
        for(String string : temp){
            list.addTask(stringToTask(string));
        }
    }



    /**
     * This methods converts the string from the file to Task object.
     * @param string String from the file
     * @return This returns Task object after converting the string from file to Task object
     * @throws ParseException On conversion error from string date to Date object
     */
    private  static Task stringToTask(String string) throws ParseException {
        if(string.trim().isEmpty()) {
            System.out.println("Error");
            System.exit(5);
        }
        Task line;
        if(string.contains("[T]")){
            line = new Todo(string.substring(7));
        } else if(string.contains("[D]")) {
            DateFormat format = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
            Date date = format.parse(string.substring(string.indexOf("by:") + 4, string.indexOf(')')).trim());
            String dateString = format.format(date);
            line = new Deadline(string.substring(7, string.indexOf("by:") - 2), dateString);
        } else {
            DateFormat format = new SimpleDateFormat("E dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
            Date date = format.parse(string.substring(string.indexOf("at:") + 4, string.indexOf("time:")));
            String dateString = format.format(date);
            Date startTime = timeFormat.parse(string.substring(string.indexOf("time:") + 6, string.indexOf("to")));
            String startTimeString = timeFormat.format(startTime);
            Date endTime = timeFormat.parse(string.substring(string.indexOf("to") + 3, string.indexOf(')')).trim());
            String endTimeString = timeFormat.format(endTime);
            line = new Event(string.substring(7, string.indexOf("at:")-2), dateString, startTimeString, endTimeString);
        }
        if(string.contains("\u2713")){
            line.setDone(true);
        }
        return line;
    }
}
