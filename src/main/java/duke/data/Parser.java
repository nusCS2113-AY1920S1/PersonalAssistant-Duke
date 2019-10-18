package duke.data;


import duke.module.Goal;
import duke.module.Lesson;
import duke.module.Reminder;
import duke.module.Schedule;
import duke.task.After;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.TaskList;
import duke.task.ToDo;
import duke.Ui;
import duke.sports.ManageStudents;
import duke.sports.MyClass;
import duke.sports.MyPlan;
import duke.sports.MyStudent;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.*;

/**
 * Parser is the controller for the string inputs received by the standard input.
 */
public class Parser {

    /**
     * This function takes the standard input defined by the user and
     * parses it into instructions for the Storage to read.
     *
     * @param io
     */
    public void parseInput(String io, TaskList tasks, Storage storage, ManageStudents students, Schedule schedule, MyPlan plan) throws FileNotFoundException, ParseException {
        int index = 1;
        String input = io;
        String[] word = io.split(" ");
        String cmd = word[0];

        switch (cmd) {

            case "list":
                tasks.showList();
                break;

            case "done":
                try {
                    index = Integer.parseInt(input.substring(5)) - 1;
                    tasks.doneTask(index);
                    storage.updateFile(tasks.getList());
                } catch (NullPointerException | IndexOutOfBoundsException e) {
                    System.out.println("\u2639 OOPS!!! The following task does not exist!");
                }
                break;

            /**
             * Creates task with a duration
             */
            case "todo":
                try {
                    String[] tempString = input.split(" ");
                    List<String> listString = new ArrayList<String>(Arrays.asList(tempString));
                    listString.remove(0);
                    String info1 = String.join(" ", listString);
                    String[] parseString = info1.split("/in");
                    ToDo todo = new ToDo(parseString[0], false, parseString[1]);
                    tasks.addTask(todo, "T");
                    storage.saveFile("T", todo, todo.getDate());
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println("\u2639 OOPS!!! The description of a todo cannot be empty.");
                }
                break;

            case "deadline":
                try {
                    index = input.indexOf("/by");
                    String info = input.substring(9, index - 1);
                    String endDate = input.substring(index + 4);
                    Deadline deadline = new Deadline(info, false, endDate);
                    tasks.addTask(deadline, "D");
                    storage.saveFile("D", deadline, deadline.getDate());
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println("\u2639 OOPS!!! The task needs a deadline");
                }
                break;

            case "event":
                try {
                    index = input.indexOf("/at");
                    String info = input.substring(6, index - 1);
                    String endDate = input.substring(index + 4);
                    Event event = new Event(info, false, endDate);
                    tasks.addTask(event, "E");
                    storage.saveFile("E", event, event.getDate());
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println("\u2639 OOPS!!! The task needs a deadline");
                }
                break;
            /**
             * Command should be in the form: reminder deadlines before 18/09/2019 1900
             * Push date before date into
             */
            case "reminder":
                try {
                    index = input.indexOf("before");
                    Date date = tasks.dateConvert(input.substring(index + 7));
                    Reminder reminder = new Reminder(date);
                    reminder.getReminders(tasks);
                } catch (StringIndexOutOfBoundsException e) {
                    System.err.println("Incorrect format");
                }
                break;

            /**
             * Command should be in the form: aftertask return book /after exam
             * It will be stored as type [A].
             */
            case "aftertask":
                try {
                    index = input.indexOf("/after");
                    String info = input.substring(10, index - 1);
                    String endDate = input.substring(index + 7);
                    After after = new After(info, false, endDate);
                    tasks.addTask(after, "A");
                    storage.saveFile("A", after, after.getDate());
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println("\u2639 OOPS!!! Please enter input in the form: aftertask XXX /after YYY");
                }
                break;

            case "delete":
                index = Integer.parseInt(input.substring(7)) - 1;
                tasks.deleteTask(index);
                storage.updateFile(tasks.getList());
                break;


            case "find":
                String searchWord = input.substring(5);
                tasks.findTask(searchWord);
                break;

            case "date":
                String searchDate = input.substring(5);
                if (searchDate.length() < 10) {
                    System.out.println("Please enter input in the form: date dd/MM/YYYY");
                } else {
                    tasks.findDate(searchDate);
                }
                break;


            /**
             * Command should be in the form: class swimming /every monday
             * It will be stored as type [C].
             */
            case "class":
                try {
                    index = input.indexOf("/every");
                    String info = input.substring(6, index - 1);
                    String day = input.substring(index + 7);
                    MyClass myclass = new MyClass(info, false, day);
                    tasks.addTask(myclass, "C");
                    storage.saveFile("C", myclass, myclass.getDay());
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println("\u2639 OOPS!!! Please enter input in the form: class XXX /every YYY");
                }
                break;

            /**
             * View: schedule view-month|schedule view-week|schedule view-day 5/10/2019
             * Add: schedule add 5/10/2019 1500 5/10/2019 1600 pool Swimming
             * Delete: schedule delete 5/10/2019 1500 Swimming|schedule delete-all 5/10/2019
             */
            case "schedule":
                Storage scheduleStorage = new Storage(".\\src\\main\\java\\duke\\data\\timeslots.txt");

                try {
                    if (word[1].equals("view-week")) {
                        System.out.println(schedule.getWeek());
                    } else if (word[1].equals("view-month")) {
                        System.out.println(schedule.getMonth());
                    } else if (word[1].equals("view-day")) {
                        try {
                            System.out.println(schedule.getDay(word[2]));
                        } catch (ArrayIndexOutOfBoundsException | ParseException e) {
                            System.err.println("Enter a date please.");
                        }
                    } else if (word[1].equals("add")) {
                        String startTime = word[2] + " " + word[3];
                        String endTime = word[4] + " " + word[5];
                        String location = word[6];
                        String className = word[7];
                        System.out.println(schedule.addClass(startTime, endTime, location, className, tasks, scheduleStorage));
                    } else if (word[1].equals("delete")) {
                        String startTime = word[2] + " " + word[3];
                        String className = word[4];
                        System.out.println(schedule.delClass(startTime, className, scheduleStorage));
                    } else if (word[1].equals("delete-all")) {
                        String date = word[2];
                        System.out.println(schedule.delAllClass(date, scheduleStorage));
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Please enter the full command.");
                }
                break;

            /**
             * View: goal view 5/10/2019
             * Add: goal add 5/10/2019 Makes sure every student masters freestyle
             * Delete: goal delete-all 5/10/2019|goal delete 5/10/2019 Makes sure every student masters freestyle
             */
            case "goal":
                Storage goalStorage = new Storage(".\\src\\main\\java\\duke\\Module\\goals.txt");
                Goal goal = new Goal(goalStorage.loadGoal());
                try {
                    switch (word[1]) {
                        case "view": {
                            String date = word[2];
                            System.out.print(goal.viewGoal(date));
                            break;
                        }
                        case "add": {
                            String date = word[2];
                            index = input.indexOf(word[3]);
                            String message = input.substring(index);
                            System.out.println(goal.addGoal(date, message, goalStorage));
                            break;
                        }
                        case "delete": {
                            String date = word[2];
                            index = input.indexOf(word[3]);
                            String message = input.substring(index);
                            System.out.println(goal.removeGoal(date, message, goalStorage));
                            break;
                        }
                        case "delete-all": {
                            String date = word[2];
                            System.out.println(goal.removeAllGoal(date, goalStorage));
                            break;
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Please enter the full command.\n" +
                        "To view a goal of the day, enter input in the format: goal view dd/MM/yyyy\n" +
                        "To add a goal to a day, enter input in the format: goal add dd/MM/yyyy {goal}\n" +
                        "To delete all goals of a day, enter input in the format: goal delete-all dd/MM/yyyy\n" +
                        "To delete a goal of the day, enter input in the format: goal delete dd/MM/yyyy {goal}");
                } catch (ParseException e) {
                    System.out.println("Please enter the details in the correct format.\n" +
                        "To view a goal of the day, enter input in the format: goal view dd/MM/yyyy\n" +
                        "To add a goal to a day, enter input in the format: goal add dd/MM/yyyy {goal}\n" +
                        "To delete all goals of a day, enter input in the format: goal delete-all dd/MM/yyyy\n" +
                        "To delete a goal of the day, enter input in the format: goal delete dd/MM/yyyy {goal}");
                }
                break;

        /**
         * View: lesson view 5/10/2019
         * Add: lesson add 5/10/2019 Makes sure every student masters freestyle
         * Delete: lesson delete-all 5/10/2019|lesson delete 5/10/2019 Makes sure every student masters freestyle
         */
        case "lesson":
            Storage lessonStorage = new Storage(".\\src\\main\\java\\duke\\Module\\lessons.txt");
            Lesson lesson = new Lesson(lessonStorage.loadLesson());
            try {
                switch (word[1]) {
                case "view": {
                    String date = word[2];
                    System.out.print(lesson.viewLesson(date));
                    break;
                }
                case "add": {
                    String date = word[2];
                    index = input.indexOf(word[3]);
                    String message = input.substring(index);
                    System.out.println(lesson.addLesson(date, message, lessonStorage));
                    break;
                }
                case "delete": {
                    String date = word[2];
                    index = input.indexOf(word[3]);
                    String message = input.substring(index);
                    System.out.println(lesson.removeLesson(date, message, lessonStorage));
                    break;
                }
                case "delete-all": {
                    String date = word[2];
                    System.out.println(lesson.removeAllLesson(date, lessonStorage));
                    break;
                }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Please enter the full command.\n" +
                    "To view a lesson learnt for the day, enter input in the format: lesson view dd/MM/yyyy\n" +
                    "To add a lesson learnt for the day, enter input in the format: lesson add dd/MM/yyyy {lesson}\n" +
                    "To delete all lessons learnt for the day, enter input in the format: lesson delete-all dd/MM/yyyy\n" +
                    "To delete a lesson learnt for the day, enter input in the format: lesson delete dd/MM/yyyy {lesson}");
            } catch (ParseException e) {
                System.out.println("Please enter the details in the correct format.\n" +
                    "To view a lesson learnt for the day, enter input in the format: lesson view dd/MM/yyyy\n" +
                    "To add a lesson learnt for the day, enter input in the format: lesson add dd/MM/yyyy {lesson}\n" +
                    "To delete all lessons learnt for the day, enter input in the format: lesson delete-all dd/MM/yyyy\n" +
                    "To delete a lesson learnt for the day, enter input in the format: lesson delete dd/MM/yyyy {lesson}");
            }
            break;

            /**
             * View: training view [plan number]
             * Add: training add-activity [name] [sets] [reps] [activity number]|training add-plan [plan number]
             * Delete: training delete-all|training delete [plan number]
             */
            case "training":
                switch(word[1]) {
                    case "view": {
                        System.out.println("TBC");
                        System.out.println(plan.viewPlan());
                        break;
                    }
                    case "add-plan": {
                        //pass
                        break;
                    }
                    case "add-activity": {
                        System.out.println(plan.addActivity(word[2],Integer.parseInt(word[3]),Integer.parseInt(word[4]),Integer.parseInt(word[5])));
                        break;
                    }
                    case "delete": {
                        //pass
                        break;
                    }
                    case "delete-all": {
                        //pass
                        break;
                    }
                }
                break;

            /**
             *  Cmd "home" will list the menu items;
             *  1. View Schedule
             *  2. Manage Students
             *  3. Training Circuits
             */
            case "home":
                Ui viewMenu = new Ui();
                viewMenu.mainMenu();
                break;

            /**
             // Choosing Option 1 wil direct to "Training Schedule"
             */
            case "1":
                System.out.flush();
                // Write go to direct to View Schedule (Scott)
                Ui trainingSchedule = new Ui();
                trainingSchedule.trainingScheduleHeading();
                break;

            /**
             * Choosing option 2 will direct to "Manage Students"
             */
            case "2":
                System.out.flush();
                Ui manageStudents = new Ui();
                manageStudents.manageStudentsHeading();
                ManageStudents viewCategory = new ManageStudents();
//                viewCategory.manageStudentsCategory();
                // Write Code to direct to manage Students (Danish)
                break;

            /**
             * Choosing 3 will direct to "Training Program"
             */
            case "3":
                System.out.flush();
                Ui trainingProgram = new Ui();
                trainingProgram.trainingProgramHeading();
                //Write Code to direct to Training Circuits (JingSen)
                break;

            /**
             * When cmd student is called
             * Format for adding student is: student add/ Name/ age/ address.
             */
            case "student":
                switch (word[1]) {
                    case "add":
                        System.out.println("Insert Name, Age, Address:\n");
                        Scanner sc = new Scanner(System.in);
                        String newStudent = sc.nextLine();
                        String[] splitByComma = newStudent.split(",");
                        String name = splitByComma[0];
                        String age = splitByComma[1];
                        String address = splitByComma[2];
                        MyStudent myNewStudent = new MyStudent(name, age, address);
                        students.addStudent(myNewStudent);
                        break;

                        // Format: student delete [index]
                    case "delete":
                        index = Integer.parseInt(word[2]); // Convert string into int
                        students.deleteStudent(index);
                        break;

                    case "details":

                        System.out.println("Details for: ");
                        Scanner scan = new Scanner(System.in);
                        if (scan.equals("add details")) {
                            System.out.println("Details for: ");

                        }
                        String studentName = scan.nextLine();
                        students.findName(studentName);
                        //add student details
                        break;

                    case "edit":
                        // editStudentDetails(detail)
                        break;

                    case "list":
                        students.listAllStudents();
                        break;

                    case "search":
                        String searchName = input.substring(15);
                        students.findName(searchName);
                        break;

                    case "select":
                        index = Integer.parseInt(word[2]);
                        students.getStudentName(index);
                        break;
                    }

                    storage.updateStudentList(students.getStudentList());
                break;

            /**
             * Command is in the form: plan new [intensity level] or plan view [intensity] plan/[plan number]
             */
            case "plan":
                if (word[1].equals("view")) {
                    plan.loadPlan(word[2].toLowerCase(), word[3]);
                    plan.viewPlan();
                } else if (word[1].equals("new")) {
                    plan.createPlan(word[2].toLowerCase());
                } else if (word[1].equals("edit")) {
                    //not yet created
                }
                break;

            default:
                System.out.println("\u2639 OOPS!!! I'm sorry, but I don't know what that means :-(");
                break;
        }
    }
}
