import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;


public class Duke {

    protected static List<Tasks> userToDoList = new ArrayList<>();


    public static void main(String[] args) throws IOException {

        String logo =
                " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        String line = "   ____________________________________________________________";
        String space = "    ";
        System.out.println(line);
        System.out.println(space + "Hello from\n" + logo + "\n" + space + "Hello I am " +
                "Duke.");
        System.out.println(space + "What can I do for you?");
        System.out.println(line);

        int todolist_number = 1;

        while (true) {
            Scanner in = new Scanner(System.in);
            String s = in.nextLine();

            // when user type bye
            if (s.equals("bye")) {
                System.out.println(line + "\n" + space + "Bye. Hope to see you again soon!");
                System.out.println(line);
                break;

            } else if (s.equals("list")) {
                //when user type list
                System.out.println(line + "\n" + space + "Here are the tasks in your list:");
                for (int i = 0; i < userToDoList.size(); i++) {
                    String message = userToDoList.get(i).getDescription();
                    int j = i + 1;
                    System.out.println(space + j + ".[" + userToDoList.get(i).getType()
                            + "][" + userToDoList.get(i).getStatusIcon()
                            + "] " + message);
                }
                System.out.println(line);

            } else if (s.startsWith("done")) { //when user type done
                String[] tokens = s.split(" ");
                int num = Integer.parseInt(tokens[1]);
                userToDoList.get(num - 1).setDone(true);
                System.out.println(line + "\n" + space + "Nice! I've marked this task as done:");
                System.out.println(space + " [" + userToDoList.get(num - 1).getType()
                        + "][" + userToDoList.get(num - 1).getStatusIcon()
                        + "] " + userToDoList.get(num - 1).getDescription());
                System.out.println(line);
            } else if (s.startsWith("todo")) {
                String joinTokens = s.substring(s.indexOf(" ")+ 1);
                // System.out.println(joinTokens);
                Tasks newToDo = new Todo(joinTokens, "T");
                userToDoList.add(newToDo);
                System.out.println(line + "\n" + space + "Got it. I've added this task:" + "\n" + space + " [" + userToDoList.get(todolist_number - 1).getType()
                        + "][" + userToDoList.get(todolist_number - 1).getStatusIcon()
                        + "] " + joinTokens);
                if (todolist_number > 1) {
                    System.out.println(space + "Now you have " + todolist_number + " tasks in the list.");
                } else {
                    System.out.println(space + "Now you have " + todolist_number + " task in the list.");
                }
                System.out.println(line);
                todolist_number += 1;
            } else if (s.startsWith("deadline")) {

                String todoTask = s.substring(9, s.indexOf("/by"));
                String time = s.substring(s.indexOf("/by") + 4);
                //System.out.println(time);

                Deadline task = new Deadline(todoTask, "D", time);
                String newtodoTask = task.toMessage();
                Tasks newToDo = new Deadline(newtodoTask, "D", time) ;
                userToDoList.add(newToDo);

                System.out.println(line + "\n" + space + "Got it. I've added this task:" + "\n" + space + " [" + userToDoList.get(todolist_number - 1).getType()
                        + "][" + userToDoList.get(todolist_number - 1).getStatusIcon()
                        + "] " + todoTask + "(by: " + time + ")");


                if (todolist_number > 1) {
                    System.out.println(space + "Now you have " + todolist_number + " tasks in the list.");
                } else {
                    System.out.println(space + "Now you have " + todolist_number + " task in the list.");
                }
                System.out.println(line);
                todolist_number += 1;

            } else if (s.startsWith("event")) {

                //String todoTask = s.substring(6, s.indexOf("/at"));
                String[] split = s.split("/at");
                String todoTask = split[0].substring(6);
                String time = s.substring(s.indexOf("/at") + 4);
                //System.out.println(time);

                Event task = new Event(todoTask, "E", time);
                String newtodoTask = task.toMessage();

                Tasks newToDo = new Event(newtodoTask, "E", time);
                userToDoList.add(newToDo);

                System.out.println(line + "\n" + space + "Got it. I've added this task:" + "\n" + space + " [" + userToDoList.get(todolist_number - 1).getType()
                        + "][" + userToDoList.get(todolist_number - 1).getStatusIcon()
                        + "] " + todoTask + "(at: " + time + ")");


                if (todolist_number > 1) {
                    System.out.println(space + "Now you have " + todolist_number + " tasks in the list.");
                } else {
                    System.out.println(space + "Now you have " + todolist_number + " task in the list.");
                }
                System.out.println(line);
                todolist_number += 1;

            }
        }
    }
}

