public class Parser{
    private static String line = "   ___________________________________________________\n";

    public static String format(String output){
        return line + output + line;
    }

    public static Command parse(String command){
        String str[] = command.split(" ", 0);
        Command cmd = new Command();
        switch (str[0]) {
            case "bye":
                cmd = new CommandExit();
                break;
            case "list":
                cmd = new CommandList();
                break;
            case "done":
                try {
                    int index = Integer.parseInt(command.substring(5));
                    cmd = new CommandDone(index);
                }
                catch (NumberFormatException e){
                    System.out.println(format("    Please enter the task as its order number\n"));
                }
                break;
            case "find":
                String keyword = command.substring(5);
                cmd = new CommandFind(keyword);
                break;
            case "delete":
                try {
                    int index = Integer.parseInt(command.substring(7));
                    cmd = new CommandDelete(index);
                }
                catch (IndexOutOfBoundsException e){
                    System.out.println(format("    The task does not exist\n"));
                }
                catch (NumberFormatException e){
                    System.out.println(format("    Please enter the task as its order number\n"));
                }
                break;
            case "todo":
                try {
                    command = command.substring(5);
                    Todo new_todo = new Todo(command);
                    cmd = new CommandAdd(new_todo);
                }
                catch (StringIndexOutOfBoundsException e){
                    System.out.println(format("     Description of todo cannot be empty\n"));
                }
                break;
            case "deadline":
                try {
                    command = command.substring(9);
                    String dl[] = command.split("/", 2);
                    Deadline new_deadline = new Deadline(dl[0], dl[1]);
                    cmd = new CommandAdd(new_deadline);
                }
                catch (StringIndexOutOfBoundsException e){
                    System.out.println(format("     Description of deadline cannot be empty\n"));
                }
                catch (ArrayIndexOutOfBoundsException | NumberFormatException e){
                    System.out.println(format("     Please indicate the date or time of the deadline with the right format\n"));
                }
                break;
            case "event":
                try {
                    command = command.substring(6);
                    String ev[] = command.split("/", 2);
                    Event new_event = new Event(ev[0], ev[1]);
                    cmd = new CommandAdd(new_event);
                }
                catch (StringIndexOutOfBoundsException e){
                    System.out.println(format("     Description of event cannot be empty\n"));
                }
                catch (ArrayIndexOutOfBoundsException | NumberFormatException e){
                    System.out.println(format("     Please indicate the date or time of the event with the right format\n"));
                }
                break;
            case "time":
                try {
                    command = command.substring(5);
                    String[] ti = command.split(" ");
                    FixedDuration fixedDuration = new FixedDuration(ti[0], ti[1]);
                    cmd = new CommandAdd(fixedDuration);
                }
                catch (StringIndexOutOfBoundsException e) {
                    System.out.println(format("     Description of time task cannot be empty\n"));
                }
                break;
            default:
                cmd = new CommandUnknown();
                break;
        }
        return cmd;
    }
}
