/**
 * Storage of the task list in Duke.
 *
 * @author tygq13
 */
package cube.util;

import java.io.*;
import java.util.ArrayList;

import cube.exception.CubeException;
import cube.exception.CubeLoadingException;
import cube.task.*;
import cube.exception.DukeException;

public class Storage {
	private String filePath;
	private String fileFullPath;
	private static final String fileName = "duke.txt";

	/**
	 * Constructor with one argument.
	 *
	 * @param filePath the directory path where data will be stored.
	 */
	public Storage(String filePath) {
		this.filePath = filePath;
		this.fileFullPath = filePath + File.separator + fileName;
	}

	/**
	 * Creates the parent directory and file.
	 *
	 * @param file the file at which should be created.
	 * @throws CubeLoadingException exception occurs when unable to create new file.
	 */
	public void create(File file) throws CubeLoadingException {
		file.getParentFile().mkdirs();
		try {
			file.createNewFile();
		} catch (IOException e) {
			throw new CubeLoadingException(fileFullPath);
		}

	}

	/**
	 * Returns true if the data file is available, otherwise makes a new data file and returns false.
	 *
	 * @return true if data file available, otherwise false.
	 * @throws CubeLoadingException exception occurs when unable to create new file.
	 */
	public boolean checkFileAvailable() throws CubeLoadingException {

		File file = new File(fileFullPath);
		if (file.exists()) {
			return true;
		} else {
			create(file);
			return false;
		}
	}

	/**
	 * Using serializable, load task objects from data file with persistent storage to an ArrayList.
	 *
	 * @return The list of the task objects.
	 * @throws DukeException Exception caught in input process, when attempting to read from data file.
	 */
	public ArrayList<Task> load() throws CubeException {
		ArrayList<Task> list = new ArrayList<>();
		if (checkFileAvailable()) {
			System.out.println("Loading file from: " + fileFullPath);
			// read from file
			try {
				FileInputStream file = new FileInputStream(fileFullPath);
				ObjectInputStream in = new ObjectInputStream(file);
				while(file.available() > 0) {
					list.add((Task) in.readObject());
				}
				in.close();
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new CubeException("IOException at:" + fileFullPath);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new CubeException("IOException at:" + fileFullPath);
			}
		}
		return list;
	}

	/**
	 * Appends a task to the data file.
	 *
	 * @param task the task to be added to data file.
	 * @throws DukeException exception happens in writing to the data file.
	 */
	public void append(Task task) throws CubeLoadingException {
		checkFileAvailable();
		try {
			FileOutputStream fileAppend = new FileOutputStream(fileFullPath, true);
			ObjectOutputStream out = new ObjectOutputStream(fileAppend);
			out.writeObject(task);
			out.close();
			fileAppend.close();
		} catch (IOException e) {
			throw new CubeLoadingException(fileFullPath);
		}
	}

	/**
	 * Saves the whole list of tasks into data file.
	 *
	 * @param list the list of tasks.
	 * @throws DukeException exception happens in writing to the data file.
	 */
	public void save(TaskList list) throws CubeLoadingException {
		checkFileAvailable();
		try {
			FileOutputStream fileSave = new FileOutputStream(fileFullPath, false);
			ObjectOutputStream out = new ObjectOutputStream(fileSave);
			for (int i = 0; i < list.size(); i++) {
				out.writeObject(list.get(i));
			}
			out.close();
			fileSave.close();
		} catch (IOException e) {
			throw new CubeLoadingException(fileFullPath);
		}
	}
}