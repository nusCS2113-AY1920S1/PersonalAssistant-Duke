import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;




public class Duke {
    public int roll_no;
    public String todo;
    public boolean done;

    public Duke(int roll_no, String todo, boolean b)
    {
        this.roll_no = roll_no;
        this.todo = todo;
        this.done = false;
    }

    public int getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(int roll_no) {
        this.roll_no = roll_no;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getStatusIcon() {
        return (done ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    public static void main(String[] args) throws IOException {
        Duke[] arr;
        arr = new Duke[101];
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        String line = "-------------------------";
        System.out.println("Hello from\n" + logo + "\n" + line + "\n" + "Hello I am " +
                "Duke.");
        System.out.println("How can I help you?");

        int todolist_number = 1;

        while (true) {
            Scanner in = new Scanner(System.in);
            String s = in.nextLine();
            // when user type bye
            if (s.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;

            } else if (s.equals("list")) {//when user type list
                if (todolist_number == 1) {
                    System.out.println("No tasks had been added yet!");
                } else {
                    for (int i = 0; i < arr.length - 1; i++)
                        if (arr[i] != null) {
                            System.out.println(arr[i].roll_no + ".[" + arr[i].getStatusIcon() + "] " + arr[i].todo);
                        }
                }
            } else if (s.startsWith("done")) { //when user type done
                String[] tokens = s.split(" ");
                int num = Integer.parseInt(tokens[1]);
                arr[num - 1].setDone(true);
                System.out.println("Nice! I've marked this task as done:");
                System.out.println(" [" + arr[num - 1].getStatusIcon() + "] " + arr[num -1].getTodo());


            } else {
                System.out.println("added: " + s);
                //arr[todolist_number - 1] = new (todolist_number, s, false);
                arr[todolist_number - 1] = new Duke(todolist_number, s, false);
                todolist_number += 1;

            }
        }
    }
}
