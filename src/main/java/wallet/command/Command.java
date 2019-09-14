package wallet.command;

import wallet.storage.Storage;
import wallet.task.Task;
import wallet.task.TaskList;

public class Command {
    /**
     * This method parses the input command of the user and execute the different functions based on the given command.
     *
     * @param fullCommand The full command input by the user
     * @param taskList The current list of task
     * @param fileIO The class object that handles file IO
     * @return true if the command given is bye
     */
    public static boolean parse(String fullCommand, TaskList taskList, Storage fileIO){
        boolean isExit = false;

        String[] command = fullCommand.split(" ",2);
        if (command[0].equals("list")){
            int count = 1;
            System.out.println("Here are the tasks in your list:");
            for (Task t : taskList.getTaskList()){
                System.out.println(count + "." + t.toString());
                count++;
            }
        } else if (command[0].equals("find")) {
            int count = 1;
            try {
                String keyword = command[1];
                System.out.println("Here are the matching tasks in your list:");
                for (Task t : taskList.getTaskList()){
                    if (t.getDescription().contains(keyword)){
                        System.out.println(count + "." + t.toString());
                        count++;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("☹ OOPS!!! Please specify a keyword to search for.");
            }
        } else if (command[0].equals("done")) {
            try {
                int num = Integer.parseInt(command[1]) - 1;
                Task task = taskList.getTask(num);
                if (task.getStatus()){
                    System.out.println("This task is already done.");
                    System.out.println(task.toString());
                } else {
                    task.markAsDone();
                    taskList.modifyTask(num, task);
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(task.toString());
                    fileIO.updateFile(task, num);
                }
            } catch (IndexOutOfBoundsException e){
                System.out.println("☹ OOPS!!! I'm sorry, but this task does not exist");
            }
        } else if (command[0].equals("todo") || command[0].equals("deadline") || command[0].equals("event")) {
            try {
                Task task = taskList.createTask(command[0], command[1]);
                if (task != null){
                    taskList.addTask(task);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(task.toString());
                    System.out.println("Now you have " + taskList.getTaskListSize() + " tasks in the list.");
                    fileIO.writeFile(task, command[0]);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("☹ OOPS!!! The description of " + command[0] + " cannot be empty");
            }
        } else if (command[0].equals("delete")) {
            try {
                int index = Integer.parseInt(command[1]) - 1;
                Task task = taskList.getTask(index);
                taskList.deleteTask(index);
                fileIO.removeTask(taskList.getTaskList(), index);
                System.out.println("Noted. I've removed this task:");
                System.out.println(task.toString());
                System.out.println("Now you have " + taskList.getTaskListSize() + " tasks in the list.");
            } catch (IndexOutOfBoundsException e){
                System.out.println("☹ OOPS!!! I'm sorry, but this task does not exist");
            } catch (NumberFormatException e){
                System.out.println("☹ OOPS!!! Please use input the index of the task to delete");
            }
        } else if (command[0].equals("bye")){
            isExit = true;
        } else {
            System.out.println("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
        }

        return isExit;
    }
}
