import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private String fileName;

    public Storage(String fileName) {
        this.fileName = fileName;
    }

    // Unchecked type coercion is necessary here.
    // And possible cast exceptions are handled
    @SuppressWarnings("unchecked")
    public List<Task> load() {
        try {
            FileInputStream fileStream = new FileInputStream(this.fileName);
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);
            List<Task> tasks = (List<Task>) objectStream.readObject();
            objectStream.close();
            return tasks;
        } catch (FileNotFoundException e) {
            System.out.println("    File doesn't exist, will create a new file.");
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            System.err.println("An unexpected error occurred when reading the file, not loading anything. " + e);
            System.err.println("The contents of the file might be deleted if you continue with Duke.");
            return new ArrayList<>();
        }
    }

    public void save(List<Task> tasks) {
        try {
            FileOutputStream fileStream = new FileOutputStream(this.fileName);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject(tasks);
            objectStream.close();
        } catch (FileNotFoundException e) {
            System.err.println("Unable to open " + this.fileName);
        } catch (IOException e) {
            System.err.println("An unexpected error occurred when writing to the file. " + e);
        }
    }
}
