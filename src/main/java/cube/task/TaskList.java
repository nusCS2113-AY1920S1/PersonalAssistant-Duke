/**
 * This is a container class that contain the task list.
 *
 * @author tygq13
 */
package task;

import java.util.ArrayList;

public class TaskList {
	private ArrayList<Task> list;

	/**
	 * Default constructor.
	 * Initializes a new list of task.
	 */
	public TaskList() {
		list = new ArrayList<>();
	}

	/**
	 * Constructor with one argument.
	 * Constructs a list with Arraylist.
	 *
	 * @param list the list of tasks.
	 */
	public TaskList(ArrayList<Task> list) {
		this.list = list;
	}

	/**
	 * Appends new task into the list.
	 *
	 * @param task the task to be appended.
	 */
	public void add(Task task) {
		list.add(task);
	}

	/**
	 * Returns the task at the specified index.
	 *
	 * @param index the index of the desired task in the list.
	 * @return a task.
	 */
	public Task get(int index) {
		return list.get(index);
	}

	/**
	 * Returns the size of the list of tasks.
	 *
	 * @return the size of the task list.
	 */
	public int size() {
		return list.size();
	}

	/**
	 * Removes the task at a specified index.
	 *
	 * @param index the index of the task to be removed.
	 */
	public void remove(int index) {
		list.remove(index);
	}

}