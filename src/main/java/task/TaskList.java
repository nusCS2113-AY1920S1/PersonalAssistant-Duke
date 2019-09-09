package task;

import task.Task;
import java.util.ArrayList;

public class TaskList {
	private ArrayList<Task> list;

	public TaskList() {
		list = new ArrayList<>();
	}

	public TaskList(ArrayList<Task> list) {
		this.list = list;
	}

	public void add(Task task) {
		list.add(task);
	}

	public Task get(int index) {
		return list.get(index);
	}

	public int size() {
		return list.size();
	}

	public void remove(int index) {
		list.remove(index);
	}

	public ArrayList<Task> list() {
		return list;
	}
}