package process;

import process.DukeException;
import task.Task;
import task.TaskList;

import java.io.*;
/**
 * Represents a storage file inside data
 */
public class Storage {
    private String file_content;
    private String file_path;
    /**
     * Create a storage file found at filepath
     * @param filePath to be used for storage
     */
    public Storage (String filePath){
        file_path = filePath;
        file_content = "";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line_X;
            while ((line_X = br.readLine()) != null) {
                file_content += line_X + "\n";
            }
        } catch (IOException e) {
            System.out.println(filePath + " file not found, creating file...");
        }
    }
    /**
     * Laod the file from the filepath
     * @return the file in the form of a string
     * @throws DukeException if the file cannot be found or if there is no task in the list
     */
    public String load () throws DukeException {
        if (file_content.isBlank()) {
            throw new DukeException("file_not_found");
        }
        return file_content;
    }
    /**
     * Save the tasklist given to the filepath
     * @param tasks the tasklist to be saved
     */
    public void save(TaskList tasks) {
        String sep = "}-}";
        String output = "";
        for (Task i : tasks) {
            output += i.save_as(sep);
        }
        try (PrintWriter out = new PrintWriter(file_path)) {
            out.println(output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
