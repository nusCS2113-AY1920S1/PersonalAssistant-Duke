import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Parser {

    public static Command handleInput(String inputLine, TaskList tasks) {
        String[] inputArray = inputLine.split(" ");
        String command = inputArray[0];

        switch (command) {
            case "list":
                return new ShowListCommand();
            case "done":
                try {
                    return new CompleteCommand(inputArray[1]);
                } catch (IndexOutOfBoundsException e) {
                    ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                            "Please use the format 'done <number>'!"
                    ));
                    Ui.printMsg(msg);
                    break;
                }
            case "remove":
                try {
                    return new RemoveCommand(inputArray[1]);
                } catch (IndexOutOfBoundsException e) {
                    ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                            "Please use the format 'remove <number>'!"
                    ));
                    Ui.printMsg(msg);
                    break;
                }
            case "find":
                return new FindStringCommand(inputLine);
            default:
                return addToList(command, inputLine);
        }
        return new ErrorCommand();
    }

    public static Command addToList(String command, String inputLine) {

        String taskDescription;
        Command commandToRun = new ErrorCommand();

        try {
            taskDescription = inputLine.substring(command.length()+1);
            switch (command) {
                case "todo":
                    commandToRun = new AddTodoCommand(taskDescription);
                    break;
                case "event":
                    commandToRun = new AddEventCommand(taskDescription);
                    break;
                case "deadline":
                    commandToRun = new AddDeadlineCommand(taskDescription);
                    break;
            }
        } catch (IndexOutOfBoundsException e) {
            ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                    "Invalid command given!"
            ));
            Ui.printMsg(msg);
        }

        return commandToRun;
    }

    public static void exit() {
        ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                "Bye. Hope to see you again soon!"
        ));
        Ui.printMsg(msg);
        //Storage.save(tasks); // Don't need to save since any previous commands are already saved
    }

}
