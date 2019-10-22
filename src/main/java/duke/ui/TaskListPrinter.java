package duke.ui;

import duke.task.Task;
import duke.tasklist.TaskList;

public class TaskListPrinter {
	public static void print(Ui ui, TaskList list) {
		int taskCount = list.size();
		ui.showLine("ID | P | R | D | Done? | Description");
		for (int i = 0; i < taskCount; i++) {
			String curr;
			if (i < 9) {
				curr = "0" + Integer.toString(i + 1);
			} else {
				curr = Integer.toString(i + 1);
			}
			Task t = list.get(i);
			curr += " | " + t.getPriority();
			curr += " | " + t.getRecurring();
			curr += " | " + "N"; // TODO optimise duration of tasks
			curr += " |   " + t.getStatusIcon();
			curr += "   | " + t.getDescription();
			ui.showLine(curr);
		}
	}
}
