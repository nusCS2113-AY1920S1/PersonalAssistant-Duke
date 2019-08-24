import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskFactory {
    public ITask createTask(String input) throws IOException {
        String[] allArgs = input.split(" ");
        List<String> listArgs = new ArrayList<>(Arrays.asList(allArgs));
        switch (allArgs[0]) {
            case "todo":
                listArgs.remove(0);
                String description = String.join(" ", listArgs);
                return new ToDos(description);
            case "deadline":
                return new Deadline(input);
            case "event":
                return new Event(input);
            default:
                throw new IOException("No such tasks implemented");
        }
    }
}
