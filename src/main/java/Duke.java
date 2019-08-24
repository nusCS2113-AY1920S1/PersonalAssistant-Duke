import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

// Java program to illustrate creating an array of
// objects
class todoList
{
    public int roll_no;
    public String todo;
    todoList(int roll_no, String todo)
    {
        this.roll_no = roll_no;
        this.todo = todo;
    }
}

public class Duke {
    public static void main(String[] args) throws IOException {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        String line = "-------------------------";
        System.out.println("Hello from\n" + logo + "\n" + line + "\n" + "Hello I am " +
                "Duke.");
        System.out.println("How can I help you?");
        // declares an Array of integers.
        todoList[] arr;
        // allocating memory for 5 objects of type Student.
        arr = new todoList[101];
        int todolist_number = 1;

        while (true) {
            //Enter data using BufferReader
            Scanner in = new Scanner(System.in);

            String s = in.nextLine();

            // Printing the read line
            if (s.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else if (!s.equals("list")){
                System.out.println("added: " + s);
                arr[todolist_number - 1] = new todoList(todolist_number, s);
             //   arr[todolist_number - 1].todo = s;
                todolist_number += 1;

            } else {
                if (todolist_number == 1) {
                    System.out.println("No tasks had been added yet!");
                } else {
                    for (int i = 0; i < arr.length - 1; i++)
                        if (arr[i] != null) {
                            System.out.println(arr[i].roll_no + ". " + arr[i].todo);
                        }
                }
            }

        }
    }
}
