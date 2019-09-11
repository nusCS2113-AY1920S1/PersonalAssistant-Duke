import javax.imageio.IIOException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FileManager {
    public void saveFile(List<Task> taskList) {
        try {
            PrintStream stdout = System.out;
            PrintStream fileOut = new PrintStream("./savefile.txt");
            System.setOut(fileOut);
            for (Task pastTask : taskList){
                if(pastTask instanceof Todo){
                    System.out.println("T | " + (pastTask.isDone ? 1 : 0) + " | " + pastTask.description);
                }else if(pastTask instanceof  Deadline){
                    System.out.println("D | " + (pastTask.isDone ? 1 : 0) + " | " + pastTask.description + " | " + ((Deadline) pastTask).by);
                }else if (pastTask instanceof  Event){
                    System.out.println("E | " + (pastTask.isDone ? 1 : 0) + " | " + pastTask.description + " | " + ((Event) pastTask).at);
                }
            }
            System.setOut(stdout);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Task> LoadFile(){
        List<Task> taskList = new ArrayList<>();
        try {
            Path path = Paths.get("./savefile.txt");
            Scanner scanner =  new Scanner(path);
            System.out.println("Loading...");
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] inputArr = line.split("\\Q | \\E");
                if(inputArr[0].equals("T")){
                    String description = inputArr[2];
                    Todo todo = new Todo(description);
                    todo.isDone = inputArr[1].equals("1");
                    taskList.add(todo);
                }else if(inputArr[0].equals("D")){
                    String description = inputArr[2];
                    String by = inputArr[3];
                    LocalDateTime localDateTime = LocalDateTime.parse(by);
                    Deadline deadline = new Deadline(description, localDateTime);
                    deadline.isDone = inputArr[1].equals("1");
                    taskList.add(deadline);
                }else if(inputArr[0].equals("E")){
                    String description = inputArr[2];
                    String at = inputArr[3];
                    Event event = new Event(description, at);
                    event.isDone = inputArr[1].equals("1");
                    taskList.add(event);
                }
            }
            System.out.println("Loaded");
        } catch (IOException e) {
            System.out.println("No save file found, starting a new one");
        }
        return taskList;
    }

}
