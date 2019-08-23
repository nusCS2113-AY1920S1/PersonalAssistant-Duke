import java.util.Scanner;

public class Duke {
    /**
     * Main class.
     * Level 1 Completed
     *
     * @param args Refers to CLI arguments
     */
    public static void main(String[] args) {
//        String logo = " ____        _        \n"
//                + "|  _ \\ _   _| | _____ \n"
//                + "| | | | | | | |/ / _ \\\n"
//                + "| |_| | |_| |   <  __/\n"
//                + "|____/ \\__,_|_|\\_\\___|\n";
//        System.out.println("Hello from\n" + logo);
        TaskList taskList = new TaskList();
        Scanner sc = new Scanner(System.in);

        System.out.println("Hello! I'm Duke");
        System.out.println("What can I do for you?");

        while (true) {
            String command = sc.nextLine();
            if (command.equals("list")) {
                taskList.showAllTasks();
            } else if (command.equals("bye")) {
                break;
            } else if (command.contains("done")) {
                String[] allInputs = command.split(" ");
                System.out.println("Nice! I've marked this task as done:");

                for (String i : allInputs) {
                    if (!i.equals("done")) {
                        int index = Integer.parseInt(i) - 1;
                        ToDos chosenToDos = taskList.getTask(index);
                        chosenToDos.markAsDone();
                        System.out.println("[" + chosenToDos.getStatusIcon() + "] " + chosenToDos.getDescription());
                    }
                }
            } else {
                ToDos newToDos = new ToDos(command);
                taskList.addToToDos(newToDos);
                System.out.println("added: " + command);
            }
        }
        System.out.println("Bye. Hope to see you again soon!");
    }
}
