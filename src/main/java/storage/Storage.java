package storage;

import exception.DukeException;
import task.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * This Storage class is utilised to do both the reading and writing to
 * persistent storage using the two primary methods saveFile and loadFile.
 *
 * @author Sai Ganesh Suresh
 * @version v2.0
 */
public class Storage {

    @SuppressWarnings("unchecked")
    private static <T> T castToAnything(Object obj) {
        return (T) obj;
    }

    private File file;
    private FileOutputStream fileOutputStream;
    private ObjectOutputStream objectOutputStream;
    private FileInputStream fileInputStream;
    private ObjectInputStream objectInputStream;


    /**
     * This Storage constructor is used to function is used to assign the different
     * parameters required by the Storage methods.
     *
     * @param file This parameter holds the file to write to.
     */
    public Storage(File file) {
        this.file = file;
        this.file.getParentFile().mkdirs();
    }

    /**
     * This saveFile method is used repeatedly throughout the other classes to save
     * updates made to the TaskList to the persistent storage to ensure the user
     * does not loose data due to sudden termination of the program.
     *
     * @param listOfTasks This parameter holds the updated TaskList of the user and
     *                    used to save the updated TaskList.
     * @throws DukeException This exception is thrown if there is not file at the
     *                       given location to save to.
     */
    public void saveFile(ArrayList<Task> listOfTasks) throws DukeException {
        try {
            setOutputStreams();
            objectOutputStream.writeObject(listOfTasks);
            objectOutputStream.close(); // always close
            fileOutputStream.close(); // always close
        } catch (IOException e) {
            throw new DukeException(DukeException.unableToWriteFile());
        }
    }

    /**
     * This saveFile method is used repeatedly throughout the other classes to save
     * updates made to the TaskList to the persistent storage to ensure the user
     * does not loose data due to sudden termination of the program.
     *
     * @param file This parameter is passed as to be able to write to the file.
     * @throws DukeException This exception is thrown for any unexpected issues such
     *                       as no file in location, unable to read the file or a
     *                       class in not found.
     */
    public ArrayList<Task> loadFile(File file) throws DukeException {
        ArrayList<Task> listOfTasks;
        try {
            setInputStreams(file);
            listOfTasks = (ArrayList<Task>)(objectInputStream.readObject());
            fileInputStream.close();
            objectInputStream.close();
            return listOfTasks;

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            throw new DukeException(DukeException.fileDoesNotExist());
        } catch (IOException e) {
            throw new DukeException(DukeException.unableToReadFile());
        } catch (Exception e) {
            throw new DukeException(DukeException.classDoesNotExist());
        }
    }

    private void setOutputStreams() throws IOException {
        this.fileOutputStream = new FileOutputStream(file);
        this.objectOutputStream = new ObjectOutputStream(fileOutputStream);
    }

    private void setInputStreams(File file) throws IOException {
        this.fileInputStream = new FileInputStream(file);
        this.objectInputStream = new ObjectInputStream(fileInputStream);
    }
}
