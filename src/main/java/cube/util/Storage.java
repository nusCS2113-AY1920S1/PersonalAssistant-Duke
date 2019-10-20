/**
 * Storage of the task list in Duke.
 *
 * @author tygq13
 */
package cube.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

import java.util.ArrayList;

import cube.exception.DukeLoadingException;
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
	 * @throws DukeLoadingException exception occurs when unable to create new file.
	 */
	public void create(File file) throws DukeLoadingException {
		file.getParentFile().mkdirs();
		try {
			file.createNewFile();
		} catch (IOException e) {
			throw new DukeLoadingException(fileFullPath);
		}

	}

	/**
	 * Returns true if the data file is available, otherwise makes a new data file and returns false.
	 *
	 * @return true if data file available, otherwise false.
	 * @throws DukeLoadingException exception occurs when unable to create new file.
	 */
	public boolean checkFileAvailable() throws DukeLoadingException {

		File file = new File(fileFullPath);
		if (file.exists()) {
			return true;
		} else {
			create(file);
			return false;
		}
	}


	/**
	 * Appends a task to the data file.
	 *
	 * @param task the task to be added to data file.
	 * @throws DukeException exception happens in writing to the data file.
	 */
	public void append(Task task) throws DukeException {
		checkFileAvailable();
		try {
			FileOutputStream fileAppend = new FileOutputStream(fileFullPath, true);
			ObjectOutputStream out = new ObjectOutputStream(fileAppend);
			out.writeObject(task);
			out.close();
			fileAppend.close();
		} catch (IOException e) {
			throw new DukeLoadingException(fileFullPath);
		}
	}

	/**
	 * Saves the whole list of tasks into data file.
	 *
	 * @param list the list of tasks.
	 * @throws DukeException exception happens in writing to the data file.
	 */
	public void save(TaskList list) throws DukeException {
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
			throw new DukeLoadingException(fileFullPath);
		}
	}
}