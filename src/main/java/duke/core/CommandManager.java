package duke.core;

import duke.command.*;
import duke.patient.Patient;
import duke.task.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents a Parser that parses user input into a specific
 * type of Command.
 */
public class CommandManager {

    /**
     * Parses a Task from a string array.
     *
     * @param userInput The string array to be parsed.
     * @return The Command received from user.
     */
    public static Command manageCommand(String userInput) throws DukeException {
        userInput = userInput.trim();
        String[] command = userInput.split(" ", 2);
        String commandType = command[0];
        switch (commandType) { //change this depending on how string is parsed
            case "addPatient":
                try {
                    String[] tempCommand = command[1].split(" ", 5);
                    boolean isHospitalised = false;
                    if (tempCommand[4].equals("T")){
                        isHospitalised = true;
                    }
                    Patient patient = new Patient(Integer.parseInt(tempCommand[0]),tempCommand[1],tempCommand[2],tempCommand[3], isHospitalised);
                    return new AddPatientCommand(patient);
                } catch (Exception e) {
                    throw new DukeException("Fail to parse addPatient command");
                }
            case "addStandardTask":
                try {
                    String[] tempCommand = command[1].split(" ", 1);
                    StandardTask task = new StandardTask(tempCommand[0]);
                    return new AddStandardTaskCommand(task);
                } catch (Exception e) {
                    throw new DukeException("Fail to parse addTask command");
                }
            case "list":
                //do thing for 'list'
            case "done":
                //do thing for 'done'
            case "delete":
                //do thing for 'delete
            case "find":
                //do thing for 'find'
            case "reschedule":
                //do thing for 'reschedule'
            case "view":
                //do thing for 'view'
            case "help":
                //do thing for 'help'
            case "bye":
                return new ExitCommand();
            default:
                throw new DukeException("Unrecognized user input!");
        }
    }

}
