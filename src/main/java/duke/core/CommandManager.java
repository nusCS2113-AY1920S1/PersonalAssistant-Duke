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
        String commandType = command[0].toLowerCase();
        switch (commandType) { //change this depending on how string is parsed
            case "add":
                try {
                    String[] tempCommand = command[1].split(" ");
                    if (tempCommand[0].toLowerCase().equals("patient")){
                        Patient patient = new Patient(tempCommand[1], tempCommand[2], tempCommand[3], tempCommand[4]);
                        return new AddPatientCommand(patient);
                    }
                    else if (tempCommand[0].toLowerCase().equals("task")){
                        StandardTask task = new StandardTask(tempCommand[1]);
                        return new AddStandardTaskCommand(task);
                    }
                } catch (Exception e) {
                    throw new DukeException("Failed to parse 'add' command. " + e.getMessage());
                }
            case "list":
                try {
                    String[] tempCommand = command[1].split(" ");
                    if (tempCommand[0].toLowerCase().equals("patient")){
                        return new ListPatientCommand();
                    }
                    else if (tempCommand[0].toLowerCase().equals("tasks")){
                        return new ListTasksCommand();
                    }
                } catch (Exception e) {
                    throw new DukeException("Failed to parse 'list' command. " + e.getMessage());
                }
            case "done":
                //do thing for 'done'
            case "delete":
                try{
                    String[] tempCommand = command[1].split(" ", 2);
                    if (tempCommand[0].toLowerCase().equals("patient")){
                            return new DeletePatientCommand(Integer.parseInt(tempCommand[1]));
                    }
                    else {
                        throw new Exception("Delete Command with invalid format.");
                    }
                } catch(Exception e){
                    throw new DukeException(e.getMessage());
                }
            case "find":
                try{
                    String[] tempCommand = command[1].split(" ", 2);
                    if (tempCommand[0].toLowerCase().equals("patient")){
                        String commandContent = tempCommand[1];
                        int patientID = Integer.parseInt(commandContent);
                        return new DeletePatientCommand(patientID);
                    }
                } catch(Exception e){
                    throw new DukeException("Find Command fails." + e.getMessage());
                }
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
                throw new DukeException("Could not understand user input.");
        }
    }

}
