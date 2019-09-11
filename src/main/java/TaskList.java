import java.util.ArrayList;

/**
 * Represents the data structure containing all tasks added to the task manager
 * Uses java.util.ArrayList as the main container
 */
public class TaskList {
	private ArrayList<Task> taskList;

	/**
	 * Constructor for TaskList
	 * this is to initialise an EMPTY TaskList so it takes no inputs
	 */
	public TaskList() {
		taskList = new ArrayList<Task>();
	}

	/**
	 * Constructor for TaskList
	 * this is to initialise a TaskList with an ArrayList of Tasks
	 * this facilitates the loading of saved Task data
	 * @param tasks an ArrayList of Tasks
	 */
	public TaskList(ArrayList<Task> tasks) {
		taskList = tasks;
	}

	/**
	 * returns the ArrayList of Tasks which TaskList maintains
	 * @return ArrayList the ArrayList of Tasks maintained by the TaskList class
	 */
	public ArrayList<Task> getList() {
		return taskList;
	}

	/**
	 * returns the number of tasks
	 * @return size the number of tasks in the TaskList now
	 */
	public int size() {
		return taskList.size();
	}

	/**
	 * returns the Task at index i
	 * @param i the index of the Task
	 * @return Task the Task object at index i
	 */
	public Task get(int i) {
		return taskList.get(i);
	}

	/**
	 * sets a Task at index i
	 * if there is already a Task there then it will be overwritten
	 * @param i the index of the Task
	 * @param task the new Task to be set at index i
	 */
	public void set(int i, Task task) {
		taskList.set(i, task);
	}

	/**
	 * adds a Task to the back of the TaskList
	 * @param task the Task to be added
	 */
	public void add(Task task) {
		taskList.add(task);
		System.out.println("Got it. I've added this task:" );
		System.out.println("  " + task);
		int taskCount = taskList.size();
		if (taskCount == 1) {
			System.out.println("Now you have " + taskCount + " task in the list.");
		} else {
			System.out.println("Now you have " + taskCount + " tasks in the list.");
		}
	}

	/**
	 * Searches for any Task objects that contain the keyword entered
	 * if there are at least one Task objects fulfilling the criteria
	 * will print out a list of these Task objects for the user to see
	 * otherwise will notify the user that no Task objects have the keyword inside
	 * @param keyword the keyword which the user is searching for Tasks with
	 * @param ui the user interface class which deals with user interactions
	 */
	public void find(String keyword, Ui ui) {
		ArrayList<Task> temp = new ArrayList<Task>();
		for (Task t : taskList) {
			if (t.getDescription().contains(keyword)) {
				temp.add(t);
			}
		}
		if (temp.size() == 0) {
			ui.showLine("There are no matching tasks in your list :-(");
		} else {
			ui.showLine("Here are the matching tasks in your list:");
			for (int i = 0; i < temp.size(); i++) {
				ui.showLine((i + 1) + "." + temp.get(i));
			}
		}
	}

	/**
	 * removes the Task at index i
	 * @param i the index at which the Task should be removed
	 */
	public void remove(int i) {
		taskList.remove(i);
	}
}