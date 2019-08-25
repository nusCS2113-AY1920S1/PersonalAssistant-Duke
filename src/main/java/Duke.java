import Model_Classes.Deadline;
import Model_Classes.Event;
import Model_Classes.Task;
import Model_Classes.ToDo;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
   private static final String LINE_BREAK = "    -----------------------------------------------------------";
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("How may I help you?");
        System.out.println(LINE_BREAK);
        boolean isExit = false;
        Scanner userInput = new Scanner(System.in);
        ArrayList<Task> toDoList = new ArrayList<>();
        readFromFile(toDoList);

        // begin repeated inputs of commands
        while (!isExit) {
            String temp = userInput.next().toLowerCase();
            switch (temp) {
                case "bye" :
                    // user indicated bye to exit duke
                    isExit = true;
                    System.out.println(LINE_BREAK);
                    System.out.println("    Goodbye! See you next time!");
                    System.out.println(LINE_BREAK);
                    writeToFile(toDoList);
                    userInput.close();
                    break;

                case "list" :
                    // user indicates to list items, iterate through toDoList and print
                    int listCount = 1;
                    System.out.println(LINE_BREAK);
                    for (Task output : toDoList) {
                        System.out.println("    " + listCount + ". " + output.toString());
                        listCount += 1;
                    }
                    System.out.println(LINE_BREAK);
                    break;

                case "done" :
                    temp = userInput.nextLine();
                    System.out.println(LINE_BREAK);
                    try {
                        int index = Integer.parseInt(temp.substring(1)) - 1;
                        try {
                            toDoList.get(index).setDone();
                            Task tempTask = toDoList.get(index);
                            System.out.println("    " + "Nice! I've marked this task as done");
                            System.out.println("    " + tempTask.getStatusIcon() + " " + tempTask.getDescription());
                            System.out.println("    You now have " + toDoList.size() + " tasks in the list");
                        } catch (IndexOutOfBoundsException A) {
                            System.out.println("    The index you've entered does not exist in your list of tasks!");
                        }
                    } catch (StringIndexOutOfBoundsException S) {
                        System.out.println("    Sorry, please enter the number of the task completed");
                        System.out.println("    Input the description in this manner :\n" + "       done 1 ");
                    }
                    System.out.println(LINE_BREAK);
                    break;

                case "delete" :
                    temp = userInput.nextLine();
                    System.out.println(LINE_BREAK);
                    try {
                        int index = Integer.parseInt(temp.substring(1)) - 1;
                        try {
                            Task tempTask = toDoList.get(index);
                            System.out.println("    " + "Noted. I've deleted this task");
                            System.out.println("    " + tempTask.getStatusIcon() + " " + tempTask.getDescription());
                            toDoList.remove(index);
                            System.out.println("    You now have " + toDoList.size() + " tasks in the list");
                        } catch (IndexOutOfBoundsException A){
                            System.out.println("    The index you've entered does not exist in your list of tasks!");
                        }
                    } catch (StringIndexOutOfBoundsException S) {
                        System.out.println("    Sorry, please enter the number of the task to be deleted");
                        System.out.println("    Input the description in this manner :\n" + "       delete 1 ");
                    }
                    System.out.println(LINE_BREAK);
                    break;

                case "todo" :
                    temp = userInput.nextLine();
                    System.out.println(LINE_BREAK);
                    if (!temp.equals("")) {
                        ToDo newToDo = new ToDo(temp);
                        toDoList.add(newToDo);
                        System.out.println("    " + "Got it. I've added this task to the list");
                        System.out.println("      " + newToDo.toString());
                        System.out.println("    You now have " + toDoList.size() + " tasks in the list");
                    } else {
                        System.out.println("    Sorry, please enter the description of the task");
                        System.out.println("    Input the description in this manner :\n" + "       todo Example Task ");
                    }
                    System.out.println(LINE_BREAK);
                    break;

                case "deadline" :
                    temp = userInput.nextLine();
                    System.out.println(LINE_BREAK);
                    String[] splitDeadline = temp.split("/");
                    splitDeadline[1] = splitDeadline[1].replaceAll("\\s+", "");
                    try {
                        Deadline newDeadline = new Deadline(splitDeadline[0], splitDeadline[1]);
                        toDoList.add(newDeadline);
                        System.out.println("    " + "Got it. I've added this task to the list");
                        System.out.println("      " + newDeadline.toString());
                        System.out.println("    You now have " + toDoList.size() + " tasks in the list");
                    } catch (ArrayIndexOutOfBoundsException A) {
                        System.out.println("    Sorry, the description of the deadline is wrongly set");
                        System.out.println("    Input the description in this manner :\n" + "       deadline Example Task / Date to be finished");
                    }
                    System.out.println(LINE_BREAK);
                    break;

                case "event" :
                    temp = userInput.nextLine();
                    System.out.println(LINE_BREAK);
                    String[] splitEvent = temp.split("/");
                    splitEvent[1] = splitEvent[1].replaceAll("\\s+", "");
                    try {
                        Event newEvent = new Event(splitEvent[0], splitEvent[1]);
                        toDoList.add(newEvent);
                        System.out.println("    " + "Got it. I've added this task to the list");
                        System.out.println("      " + newEvent.toString());
                        System.out.println("    You now have " + toDoList.size() + " tasks in the list");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("    Sorry, the description of the event is wrongly set");
                        System.out.println("    Input the description in this manner :\n" + "       event Example Task / Date to be finished");
                    }
                    System.out.println(LINE_BREAK);
                    break;

                default:
                    // invalid command, prompt for a correct command
                    System.out.println(LINE_BREAK);
                    System.out.println("    I'm sorry, I don't understand this command....");
                    System.out.println("    Try typing todo, deadline or event followed by the task description!");
                    System.out.println(LINE_BREAK);
                    break;
            }
        }
    }

    public static void writeToFile (ArrayList<Task> list) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"));
            for (Task s : list) {
                String done = s.getStatusIcon().equals("[\u2713] ") ? "y" : "n";
                String type = String.valueOf(s.toString().charAt(1));
                String description = s.getDescription();
                String[] tempString = s.toString().split(":");
                String time = "";
                if (!type.equals("T")) {
                    time = tempString[1].substring(1, tempString[1].length()-1);
                }
                String out = type + "#" + done + "#" + description + "#" + time;
                writer.write(out);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public static void readFromFile (ArrayList<Task> taskArrayList) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("data.txt"));
            String line = "";
            ArrayList<String> tempList = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                tempList.add(line);
            }
            for (String list : tempList) {
                String[] temp = list.split("#", 4);
                switch (temp[0]) {
                    case "T" :
                        ToDo tempToDo = new ToDo(temp[2]);
                        if (temp[1].equals("y")) {
                            tempToDo.setDone();
                        }
                        taskArrayList.add(tempToDo);
                        break;
                    case "E" :
                        Event tempEvent = new Event(temp[2], temp[3]);
                        if (temp[1].equals("y")) {
                            tempEvent.setDone();
                        }
                        taskArrayList.add(tempEvent);
                        break;
                    case "D" :
                        Deadline tempDeadline = new Deadline(temp[2], temp[3]);
                        if (temp[1].equals("y")) {
                            tempDeadline.setDone();
                        }
                        taskArrayList.add(tempDeadline);
                        break;
                }
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}
