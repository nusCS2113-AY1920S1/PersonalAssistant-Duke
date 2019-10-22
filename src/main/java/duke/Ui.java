package duke;

import duke.Parser.ParserCommand;
import duke.Parser.Parser;
import duke.module.Lesson;
import duke.module.Schedule;
import duke.sports.ManageStudents;
import duke.sports.MyPlan;
import duke.task.TaskList;
import duke.data.Storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Scanner;

public class Ui {
    /**
     * Declaring new Parser type.
     */
    private ParserCommand parser = new ParserCommand();

    /**
     * A method to run the program.
     */
    public void execute() {
        Scanner sc = new Scanner(System.in);
        welcome();
        while (true) {
            if (sc.hasNextLine()) {
                String command = sc.nextLine();
                if (command.equals("bye")) {
                    goodbye();
                    System.exit(0);
                } else if (command.equals("home")) {
                    mainMenu();
                } else {
                    try {

                        parser.parseCommand(command);
                    } catch (FileNotFoundException | ParseException io) {
                        System.err.println("file not found");
                    }
                }
            }
        }
    }

    /**
     * This function prints out the welcome message of Duke.
     */
    public void welcome() {
        System.out.println("Hello! I'm Duke, your personal sports manager!\n"
            + "What can I do for you?");
    }

    /**
     * Displays main menu on command line.
     */
    public void mainMenu() {
        System.out.println("SPORTS MANAGER\n"
            + "1. View Training Schedule\n"
            + "2. Manage Students\n"
            + "3. Training Circuits");
    }

    /**
     * This function prints out the goodbye message of Duke.
     */
    public void goodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * This function prints out the heading when option 1 is chosen.
     */
    public void trainingScheduleHeading() {
        System.out.flush();
        System.out.println("TRAINING SCHEDULE:\n");
    }

    /**
     * This function prints out the heading when option 2 is chosen.
     */
    public void manageStudentsHeading() {
        System.out.flush();
        System.out.println("MANAGE STUDENTS:\n"
            + "1. Student List - View all students available "
            + "and edit student particulars (Cmd: student list)\n"
            + "2. Add student - Adding a new student to the list "
            + "with main details (Cmd: student add [name],[age],[address]) (\n"
            + "3. Remove Student - Remove a student in a list "
            + "(Cmd: student delete [index of student in the list])\n"
            + "4. Search Student - Finding a particular student in the list "
            + "(Cmd: student search [name])");
    }


    public void lessonHeading(Lesson lessonDate) {
        System.out.flush();
        System.out.println("What would you like to do on \n" + lessonDate + "?\n"
            + "1. View lessons of the day\n"
            + "2. Add a lesson of the day\n"
            + "3. Delete a lesson of the day\n"
            + "4. Clear all lessons of the day\n"
            + "5. Quit lesson of the day");
    }

    /**
     * This function prints out the heading when option 3 is chosen.
     */
    public void trainingProgramHeading() {
        System.out.flush();
        System.out.println("TRAINING PROGRAM:\n"
            + "1. Plan list - View all the plans available "
            + "and check a plan or edit it (cmd: TBC)\n"
            + "2. Create plan - Create a new plan of a "
            + "specified intensity level (Cmd: plan new [intensity level])"
            + "3. Edit plan - Edit a specified plan by adding new "
            + "activities or switching activity positions "
            + "(Cmd: plan edit [intensity level] [plan number])");
    }

    /**
     * This function takes the standard input defined by the user.
     * passes it into the Parser class.
     *
     * @param input    user input
     * @param tasks    task class
     * @param storage  saving/loading class
     * @param students students class
     * @param schedule schedule class
     * @param plan     training plan class
     * @throws FileNotFoundException if save files cannot be loaded
     * @throws ParseException if loadSchedule has an error
     */
    public void readCommand(final String input,
                            final TaskList tasks,
                            final Storage storage,
                            final ManageStudents students,
                            final Schedule schedule,
                            final MyPlan plan)
        throws FileNotFoundException, ParseException {
        Parser parser = new Parser();
        parser.parseInput(input, tasks, storage, students, schedule, plan);
    }

    /**
     * Displays student from student list that is matching to search.
     *
     * @param foundStudent Name of student that has been found.
     * @return Student that has been found
     */
    public String printFoundStudent(final String foundStudent) {
        return "Here are the matching names in your list: " + foundStudent;
    }

}
