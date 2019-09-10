package Data;

import Task.*;

public class Parser {


    public void parseInput(String io) {
        int index = 1;
        String input = io;
        String[] word = io.split(" ");
        String cmd = word[0];

        switch (cmd) {

            case "list":
                TaskList.getList();
                break;

            case "done":
                try {
                    index = Integer.parseInt(input.substring(5)) - 1;
                    TaskList.doneTask(index);
                }
                catch (NullPointerException | IndexOutOfBoundsException e) {
                    System.out.println("\u2639 OOPS!!! The following task does not exist!");
                }
                break;

            case "todo":
                try {
                    String info = input.substring(5);
                    ToDo todo = new ToDo(info, false);
                    TaskList.addTask(todo, "T");
                }
                catch(StringIndexOutOfBoundsException e) {
                    System.out.println("\u2639 OOPS!!! The description of a todo cannot be empty.");
                }
                break;

            case "deadline":
                try {
                    index = input.indexOf("/by");
                    String info = input.substring(9, index);
                    String endDate = TaskList.dateConvert(input.substring(index + 4));
                    Deadline deadline = new Deadline(info, false, endDate);
                    TaskList.addTask(deadline, "D");
                }
                catch (StringIndexOutOfBoundsException e) {
                    System.out.println("\u2639 OOPS!!! The task needs a deadline");
                }
                break;

            case "event":
                try {
                    index = input.indexOf("/at");
                    String info = input.substring(6, index);
                    String endDate = TaskList.dateConvert(input.substring(index + 4));
                    Event event = new Event(info, false, endDate);
                    TaskList.addTask(event, "E");
                }
                catch (StringIndexOutOfBoundsException e) {
                    System.out.println("\u2639 OOPS!!! The task needs a deadline");
                }
                break;

            case "delete":
                index = Integer.parseInt(input.substring(7)) - 1;
                TaskList.deleteTask(index);
                break;

            case "find":
                String searchWord = input.substring(5);
                TaskList.findTask(searchWord);
                break;


            default:
                System.out.println("\u2639 OOPS!!! I'm sorry, but I don't know what that means :-(");
                break;
        }

    }
}
