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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Date;

/**
 * Parser is the controller for the string inputs received
 * by the standard input.
 */
public class Parser {

    /**
     * This function takes the standard input defined by the user and
     * parses it into instructions for the Storage to read.
     *
     * @param io
     */
    public void parseInput(final String io,
                           final TaskList tasks,
                           final Storage storage,
                           final ManageStudents students,
                           final Schedule schedule,
                           final MyPlan plan)
        throws FileNotFoundException, ParseException {
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
                System.out.println("\u2639 OOPS!!!"
                    + "The following task does not exist!");
            }
            break;

        /**
         * Creates task with a duration
         */
        case "todo":
            try {
                String[] tempString = input.split(" ");
                List<String> listString = new ArrayList<String>(
                    Arrays.asList(tempString));
                listString.remove(0);
                String info1 = String.join(" ", listString);
                String[] parseString = info1.split("/in");
                ToDo todo = new ToDo(parseString[0], false, parseString[1]);
                tasks.addTask(todo, "T");
                storage.saveFile("T", todo, todo.getDate());
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("\u2639 OOPS!!!"
                    + "The description of a todo cannot be empty.");
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
         * Command should be in the form:
         * reminder deadlines before 18/09/2019 1900
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
                System.out.println(
                    "\u2639 OOPS!!! Please enter input in the"
                        + "form: aftertask XXX /after YYY");
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
                System.out.println("Please enter input in the form: "
                    + "date dd/MM/YYYY");
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
                System.out.println(
                    "\u2639 OOPS!!! "
                        + "Please enter input in the form:"
                        + "class XXX /every YYY");
            }
            break;

        /**
         * View: schedule view-month|schedule view-week|
         * schedule view-day 5/10/2019
         * Add: schedule add 5/10/2019 1500 5/10/2019 1600 pool Swimming
         * Delete: schedule delete 5/10/2019 1500 Swimming|
         * schedule delete-all 5/10/2019
         */
        case "schedule":
            Storage scheduleStorage = new Storage(
                ".\\src\\main\\java\\duke\\data\\timeslots.txt");

            try {
                if (word[1].equals("view-week")) {
                    System.out.println(schedule.getWeek());
                } else if (word[1].equals("view-month")) {
                    System.out.println(schedule.getMonth());
                } else if (word[1].equals("view-day")) {
                    try {
                        System.out.println(schedule.getDay(word[2]));
                    } catch (ArrayIndexOutOfBoundsException
                        | ParseException e) {
                        System.err.println("Enter a date please.");
                    }
                } else if (word[1].equals("add")) {
                    String startTime = word[2] + " " + word[3];
                    String endTime = word[4] + " " + word[5];
                    String location = word[6];
                    String className = word[7];
                    System.out.println(schedule.addClass(
                        startTime,
                        endTime,
                        location,
                        className,
                        tasks,
                        scheduleStorage));
                } else if (word[1].equals("delete")) {
                    String startTime = word[2] + " " + word[3];
                    String className = word[4];
                    System.out.println(
                        schedule.delClass(
                            startTime, className, scheduleStorage));
                } else if (word[1].equals("delete-all")) {
                    String date = word[2];
                    System.out.println(
                        schedule.delAllClass(date, scheduleStorage));
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Please enter the full command.");
            }
            break;

        /**
         * Simply type "goal" to start it off.
         */
        case "goal":
            Storage goalStorage = new Storage(
                ".\\src\\main\\java\\duke\\data\\goals.txt");
            Goal goal = new Goal(goalStorage.loadGoal());
            Scanner myGoalScan = new Scanner(System.in);

            System.out.println("Please enter the date of the day "
                + "in this format: dd/MM/yyyy");
            String goalDate = myGoalScan.next();

            boolean isQuittingGoal = false;
            while (!isQuittingGoal) {
                try {
                    System.out.println(
                        "\nWhat would you like to do on " + goalDate + "?\n"
                            + "1. View goals of the day\n"
                            + "2. Add a goal of the day\n"
                            + "3. Delete a goal of the day\n"
                            + "4. Clear all goals of the day\n"
                            + "5. Quit goal of the day");
                    int execute_type = myGoalScan.nextInt();
                    myGoalScan.nextLine();  // This line you have
                    // to add (It consumes the \n character)
                    switch (execute_type) {
                    case 1: {
                        System.out.print(goal.viewGoal(goalDate));
                        break;
                    }
                    case 2: {
                        System.out.println("To add a goal to "
                            + goalDate + ", enter the goal.");
                        String myGoal = myGoalScan.nextLine();
                        System.out.println(
                            goal.addGoal(goalDate, myGoal, goalStorage));
                        break;
                    }
                    case 3: {
                        System.out.println("To delete a goal from "
                            + goalDate + ", enter the goal.");
                        String message = myGoalScan.nextLine();
                        System.out.println(
                            goal.removeGoal(
                                goalDate, message, goalStorage));
                        break;
                    }
                    case 4: {
                        System.out.println(
                            goal.removeAllGoal(goalDate, goalStorage));
                        break;
                    }
                    case 5: {
                        isQuittingGoal = true;
                        System.out.println(
                            "You have quit the lesson of the day.");
                    }
                    default:
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Please enter the full command.");
                } catch (ParseException e) {
                    System.out.println(
                        "Please enter the details in the correct format.");
                }
            }
            break;

        /**
         * Simply type "lesson" to start it off.
         */
        case "lesson":
            Storage lessonStorage = new Storage(
                ".\\src\\main\\java\\duke\\data\\lessons.txt");
            Lesson lesson = new Lesson(lessonStorage.loadLesson());
            Scanner myLessonScan = new Scanner(System.in);

            System.out.println("Please enter the date of the day " +
                "in this format: dd/MM/yyyy");
            String lessonDate = myLessonScan.next();

            boolean isQuittingLesson = false;
            while (!isQuittingLesson) {
                try {
                    System.out.println(
                        "\nWhat would you like to do on " + lessonDate + "?\n"
                            + "1. View lessons of the day\n"
                            + "2. Add a lesson of the day\n"
                            + "3. Delete a lesson of the day\n"
                            + "4. Clear all lessons of the day\n"
                            + "5. Quit lesson of the day");
                    int execute_type = myLessonScan.nextInt();
                    myLessonScan.nextLine();  // This line you have
                    // to add (It consumes the \n character)
                    switch (execute_type) {
                    case 1: {
                        System.out.print(lesson.viewLesson(lessonDate));
                        break;
                    }
                    case 2: {
                        System.out.println("To add a lesson to "
                            + lessonDate + ", enter the lesson.");
                        String myLesson = myLessonScan.nextLine();
                        System.out.println(
                            lesson.addLesson(lessonDate, myLesson, lessonStorage));
                        break;
                    }
                    case 3: {
                        System.out.println("To delete a lesson from "
                            + lessonDate + ", enter the lesson.");
                        String message = myLessonScan.nextLine();
                        System.out.println(
                            lesson.removeLesson(
                                lessonDate, message, lessonStorage));
                        break;
                    }
                    case 4: {
                        System.out.println(lesson.removeAllLesson(
                            lessonDate, lessonStorage));
                        break;
                    }
                    case 5: {
                        isQuittingLesson = true;
                        System.out.println(
                            "You have quit the lesson of the day.");
                    }
                    default:
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Please enter the full command.");
                } catch (ParseException e) {
                    System.out.println(
                        "Please enter the details in the correct format.");
                }
            }
            break;

        /**
         * View: training view [plan number]
         * Add: training add-activity [name] [sets] [reps] [activity number]|
         * training add-plan [plan number]
         * Delete: training delete-all|training delete [plan number]
         */
        case "training":
            switch (word[1]) {
            case "view":
                System.out.println("TBC");
                System.out.println(plan.viewPlan());
                break;
            case "add-plan":
                //pass
                break;
            case "add-activity":
                int num = 2;
                System.out.println(plan.addActivity(word[num],
                    Integer.parseInt(word[++num]),
                    Integer.parseInt(word[++num])));
                break;
            case "delete":
                System.out.println("To be added.");
                break;
            case "delete-all":
                System.out.println("To be added");
                break;
            default:
                break;
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
                MyStudent myNewStudent = new MyStudent(
                    name, age, address);
                students.addStudent(myNewStudent);
                break;

            // Format: student delete [index]
            case "delete":
                index = Integer.parseInt(word[2]);
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
                index = Integer.parseInt(word[2]);
                System.out.print("What do you want to edit for ");
                students.getStudentName(index);
                System.out.println("?");

                // editStudentDetails(detail)
                break;

            case "list":
                students.listAllStudents();
                break;

            case "search":
                final int limit = 15;
                String searchName = input.substring(limit);
                students.findName(searchName);
                break;

            case "select":
                index = Integer.parseInt(word[2]);
                System.out.print("You have selected: ");
                students.getStudentName(index);
                break;

            default:
                System.out.println("(Add statement here?)");

            }

            storage.updateStudentList(students.getStudentList());
            break;

        case "plan":
            if (word[1].equals("view")) {
                //int num = 2;
                //plan.loadPlan(word[num].toLowerCase(), word[++num]);
                System.out.println(plan.viewPlan());
            } else if (word[1].equals("new")) {
                plan.createPlan(word[2].toLowerCase());
            } else if (word[1].equals("edit")) {
                System.out.println("To be created...");
            }
            break;

        default:
            System.out.println("\u2639 OOPS!!! I'm sorry,"
                + "but I don't know what that means :-(");
            break;
        }
    }
}
