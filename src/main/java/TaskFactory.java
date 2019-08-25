import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskFactory {
    public ITask createTask(String input) throws IOException {
        String[] allArgs = input.split(" ");
        List<String> listArgs = new ArrayList<>(Arrays.asList(allArgs));
        String tempString;
        String[] parsedStrings;
        switch (allArgs[0]) {
            case "todo":
                listArgs.remove(0);
                String description = String.join(" ", listArgs);
                return new ToDos(description);
            case "deadline":
                listArgs.remove(0); // Remove "deadline"
                tempString = String.join(" ", listArgs);
                parsedStrings = tempString.split("/by");
                return new Deadline(parsedStrings[0], parsedStrings[1]);
            case "event":
                listArgs.remove(0); // Remove "event"
                tempString = String.join(" ", listArgs);
                parsedStrings = tempString.split("/at");
                return new Event(parsedStrings[0], parsedStrings[1]);
            default:
                throw new IOException("No such tasks implemented");
        }
    }
}
