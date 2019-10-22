package duke.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import duke.task.*;
import duke.tasklist.TaskList;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Class that deals with the saving and loading of all duke.task.Task data the user has saved
 * Uses JSON file as the file type which the duke.tasklist.TaskList is saved
 * Loads the data stored in the JSON file into an ArrayList of Tasks that
 * can be used to construct a new instance of duke.tasklist.TaskList which has all the saved duke.task.Task data
 */
public class Storage {
	String filePath;

	/**
	 * Constructor of the storage class
	 * Takes in the file path of where the JSON file with the saved data is stored on the computer
	 * @param filePath the location in the directory where the JSON file is
	 */
	public Storage(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * Reads all the data saved in the JSON file specified in the filePath attribute
	 * Converts the data into an ArrayList of Tasks with all saved duke.task.Task data
	 * Returns this ArrayList for user to update progress or new tasks
	 * @return ArrayList of duke.task.Task the ArrayList with all the saved duke.task.Task data
	 * @throws FileNotFoundException in case of the file not beign found, a new instance will be created and all
	 * previous data will be lost
	 */
	public ArrayList<Task> load() throws FileNotFoundException {
		Gson gson = new GsonBuilder()
				.registerTypeAdapterFactory(RuntimeTypeAdapterFactory
						.of(Task.class)
						.registerSubtype(Task.class)
						.registerSubtype(Event.class))
				.create();
		JsonReader reader = new JsonReader(new FileReader("data/duke.json"));
		Type type = new TypeToken<ArrayList<Task>>() {}.getType();
		ArrayList<Task> tasks = gson.fromJson(reader, type);
		return tasks;
	}

	/**
	 * Transfers all current duke.task.Task data saved in the duke.tasklist.TaskList into an ArrayList of Tasks
	 * Writes all data in this ArrayList onto the JSON file specified in the filePath attribute
	 * @param tasks the duke.tasklist.TaskList with all the saved duke.task.Task data
	 * @throws IOException in case of IOException then the error in failure of saving should be shown to user
	 */
	public void save(TaskList tasks) throws IOException {
		FileWriter writer = new FileWriter("data/duke.json");
		ArrayList<Task> list= tasks.getList();
		Gson gson = new GsonBuilder()
				.registerTypeAdapterFactory(RuntimeTypeAdapterFactory
						.of(Task.class)
						.registerSubtype(Task.class)
						.registerSubtype(Event.class))
				.create();
		gson.toJson(list, writer);
		writer.close();
	}
}
