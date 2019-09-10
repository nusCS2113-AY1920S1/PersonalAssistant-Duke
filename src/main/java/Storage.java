import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Storage {
    private String filePath;

    public Storage(String path) {
        this.filePath = path;
    }

    public ArrayList<Task> load() throws DukeExceptionThrow
    {
        File newDuke = new File(filePath);
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            Scanner ss = new Scanner(newDuke);
            while (ss.hasNext()) {
                String[] newTask = ss.nextLine().split(" \\| ");
                if (newTask[0].equals("T"))
                {
                    Task x = new ToDos(newTask[2]);
                    if (newTask[1].equals("1"))
                    {
                        x.markAsDone();
                    }
                    tasks.add(x);
                }
                if (newTask[0].equals("D"))
                {
                    Task t = new Deadline(newTask[2], newTask[3]);
                    if (newTask[1].equals("1"))
                    {
                        t.markAsDone();
                    }
                    tasks.add(t);
                }
                if (newTask[0].equals("E"))
                {
                    Task t = new Events(newTask[2], newTask[3]);
                    if (newTask[1].equals("1"))
                    {
                        t.markAsDone();
                    }
                    tasks.add(t);
                }

            }
            return tasks;
        }
        catch (FileNotFoundException e)
        {
            throw new DukeExceptionThrow("File is not found!");
        }
    }

    public void save(ArrayList<Task> task) {
        try {
            FileWriter ww = new FileWriter("./data/duke.txt");
            for (Task t : task)
            {
                ww.write(t.writeTxt() + System.lineSeparator());
            }
            ww.close();
        } catch (IOException e)
        {
            System.out.println("File writing process encounters an error " + e.getMessage());
        }
    }

}
