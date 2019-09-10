import java.util.Scanner;

public class Parser {
    private static String[] substring;

    public static Command Parse(String ss) throws DukeExceptionThrow
    {
        String[] command = ss.split(" ", 2);
        try
        {
            switch (command[0]) {
                case "list":
                    return new ListCommand();
                case "done":
                    int i = Integer.parseInt(command[1]);
                    return new DoneCommand(i);
                case "delete":
                    int x = Integer.parseInt(command[1]);
                    return new DeleteCommand(x);
                case "todo":
                    ToDos t = new ToDos(command[1]);
                    return new AddCommand(t);
                case "deadline":
                    String[] temp = command[1].split(" /by ");
                    Deadline d = new Deadline(temp[0], temp[1]);
                    return new AddCommand(d);
                case "event":
                    String[] temp1 = command[1].split(" /at ");
                    Events z = new Events(temp1[0], temp1[1]);
                    return new AddCommand(z);
                case "find":
                    return new FindCommand(command[1]);
                case "bye":
                    return new ExitCommand();
                default:
                    throw new DukeExceptionThrow("Unrecognized user input!");
            }
        }
        catch (DukeExceptionThrow e)
        {
            throw new DukeExceptionThrow("Fail to recognize the command");
        }
    }

}
