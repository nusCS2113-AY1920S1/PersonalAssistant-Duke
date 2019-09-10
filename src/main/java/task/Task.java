package task;

public class Task {
	protected String task;
	protected boolean isDone;
	protected char type;

	public Task() {
		this(null);
	}

	public Task(String task) {
		this.task = task;
		this.isDone = false;
	}

	public String[] getTask() {
		return new String[] {task};
	}

	public char getType() {
		return type;
	}

	public boolean getStatus() {
		return isDone;
	}

	public String getStatusIcon() {
		return (isDone ? "\u2713" : "\u2718"); //tick or cross
	}

	@Override
	public String toString() {
		return "[" + getStatusIcon() + "] " + task; 
	}

	public void markAsDone() {
		this.isDone = true;
	}
}