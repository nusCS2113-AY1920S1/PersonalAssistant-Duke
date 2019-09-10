package task;

public class Todo extends Task{

	public Todo() {
		this(null);
	}

	public Todo(String task) {
		this(false, task);
	}

	public Todo(boolean done, String task) {
		super(task);
		this.isDone = done;
		this.type = 'T';
	}

	@Override
	public String toString() {
		return "[T]" + super.toString();
	}

}