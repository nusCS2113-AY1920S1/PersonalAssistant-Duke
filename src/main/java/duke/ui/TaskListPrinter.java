package duke.ui;

import duke.task.Task;
import duke.tasklist.TaskList;

public class TaskListPrinter {
	public static void print(Ui ui, TaskList list) {
		int taskCount = list.size();
		ui.showLine("ID | Priority | Recurrence | Duration | Done? | Description");
		for (int i = 0; i < taskCount; i++) {
			ui.showLine("-- | -------- | ---------- | -------- | ----- | -----------");
			String curr;
			if (i < 9) {
				curr = "0" + (i + 1);
			} else {
				curr = Integer.toString(i + 1);
			}
			Task t = list.get(i);
			curr += " | " + padPriority(t.getPriority());
			curr += " | " + padRecurrence(t.getRecurrenceCode());
			curr += " | " + padDuration(t.getDuration());
			curr += " |   " + t.getStatusIcon();
			curr += "   | " + t.getDescription();
			ui.showLine(curr);
		}
	}

	private static String padPriority(String priority) {
		switch (priority.length()) {
		case 6:
			return " " + priority + " ";
		case 4:
			return "  " + priority + "  ";
		default:
			return "  " + priority + "   ";
		}
	}

	private static String padRecurrence(String recurrence) {
		switch (recurrence.length()) {
		case 6:
			return "  " + recurrence + "  ";
		case 5:
			return "   " + recurrence + "  ";
		default:
			return "    " + recurrence + "    ";
		}
	}

	private static String padDuration(String duration) {
		int toPad = 8 - duration.length();
		int front, back;
		if (toPad % 2 == 0) {
			front = toPad / 2;
			back = toPad / 2;
		} else {
			front = toPad / 2 + 1;
			back = toPad / 2;
		}
		String result = "";
		for (int i = 0; i < front; i++) {
			result += " ";
		}
		result += duration;
		for (int i = 0; i < back; i++) {
			result += " ";
		}
		return result;
	}
}
