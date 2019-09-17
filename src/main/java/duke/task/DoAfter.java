package duke.task;

import java.time.LocalDateTime;

public class DoAfter extends Task {

private String todo;
private String after;

private final String SYMBOL = "[T]";

public DoAfter(String todo, String after) {
    super(todo.trim(), TaskType.TODO);
    this.after = after;
}

@Override
public String toString() {
    return SYMBOL + super.toString() + " (after: " + this.after + ")\n";
}

@Override
public String getSymbol() {
    return this.SYMBOL;
}

@Override
public String writeToFile() {
    return String.format("T | %d | %s | %s", (isCompleted() ? 1 : 0), this.getDescription(), "after:" +
            this.after);
}

@Override
public String getDateTime() {
    return null;
}

@Override
public LocalDateTime getLocalDate() {
    return null;
}

@Override
public void setDate(String newDate) {
}

}
