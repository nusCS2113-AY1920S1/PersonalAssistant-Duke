package util;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Scanner;
import java.util.ArrayList;

import exception.DukeLoadingException;
import task.*;
import exception.DukeException;
import exception.DukeLoadingException;

public class Storage {
	private String filePath;
	private static final String fileName = "duke.txt";

	public Storage(String filePath) {
		this.filePath = filePath;
	}

	public void create() throws IOException{
		File directory = new File(filePath);
		directory.mkdir();
		File file = new File(filePath + File.separator + fileName);
		file.createNewFile();
	}

	public ArrayList<Task> load() throws DukeException {
		ArrayList<Task> list = new ArrayList<>();
		File data = new File(filePath + File.separator + fileName);
		try {
			Scanner read = new Scanner(data);
			while (read.hasNext()) {
			    String [] tasks = read.nextLine().split(" \\| ");
			    //System.out.println(Arrays.toString(tasks));
			    if (tasks[0].trim().equals("T")) {
			        list.add(new Todo(Boolean.parseBoolean(tasks[1]), tasks[2].trim()));
			    } else if (tasks[0].trim().equals("D")) {
			        list.add(new Deadline(Boolean.parseBoolean(tasks[1]), tasks[2].trim(), Parser.parseStringToDate(tasks[3].trim())));
			    } else if (tasks[0].trim().equals("E")) {
			        list.add(new Event(Boolean.parseBoolean(tasks[1]), tasks[2].trim(), Parser.parseStringToDate(tasks[3].trim())));
			    }
			}
		} catch (FileNotFoundException e) {
			try {
				create();
			} catch (IOException error) {
				throw new DukeLoadingException();
			}
		}
		return list;
	}

	private void writeTask(BufferedWriter fileUpdate, Task task) throws DukeException {
		try {
			fileUpdate.write(task.getType() + " | " + (task.getStatus()? "1" : "0"));
			for (String s : task.getTask()) {
			    fileUpdate.write(" | " + s);
			}
			fileUpdate.write(System.lineSeparator());
		} catch (IOException e) {
			throw new DukeException("couldn't write into file");
		}
	}

	public void append(Task task) throws DukeException {
		try {
			FileWriter fw = new FileWriter(filePath + File.separator + fileName, true);
			BufferedWriter fileAppend = new BufferedWriter(fw);
			writeTask(fileAppend, task);
			fileAppend.close();
		} catch (IOException e) {
			throw new DukeException("couldn't write into file");
		}
	}


	public void save(TaskList list) throws DukeException {
	    /* write to a specific line:
	    List<String> lines = Files.readAllLines(Paths.get(FILEPATH));
	    lines.set(taskNumber - 1, finished.getType() + " | 1 | " + finished.getTask());
	    Files.write(file.toPath(), lines);
	    */
	    
	    try {
		    FileWriter fw = new FileWriter(filePath + File.separator + fileName);
		    BufferedWriter fileSave = new BufferedWriter(fw);
		    for (Task task : list.list()) {
		        writeTask(fileSave, task);
		    }
		    fileSave.close();
	    } catch (IOException e) {
	    	throw new DukeException("couldn't write into file");
	    }
	}
}