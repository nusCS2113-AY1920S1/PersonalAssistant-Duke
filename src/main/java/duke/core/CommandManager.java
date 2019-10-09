package duke.core;

import duke.command.*;
import duke.patient.Patient;
import duke.task.*;

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
        String[] command = userInput.split("\\s+", 2);
        String commandType = command[0].toLowerCase();
        switch (commandType) { //change this depending on how string is parsed
            case "add":
                try {
                    String[] tempCommand = command[1].split("\\s+");
                    if (tempCommand[0].toLowerCase().equals("patient")){
                        try {
                            Patient patient = new Patient(tempCommand[1], tempCommand[2], tempCommand[3], tempCommand[4]);
                            return new AddPatientCommand(patient);
                        }catch(Exception e){
                            throw new Exception("Add patient fails! Please follow the format 'add patient <name> <NRIC> <Room> <remark>'.");
                        }
                    }
                    else if (tempCommand[0].toLowerCase().equals("task")){
                        try {
                            StandardTask task = new StandardTask(tempCommand[1]);
                            return new AddStandardTaskCommand(task);
                        }
                    }
                    else {
                        throw new Exception("Invalid 'add' command.");
                    }
                } catch (Exception e) {
                    throw new DukeException(e.getMessage());
                }
            case "list":
                try {
                    String[] tempCommand = command[1].split("\\s+");
                    if (tempCommand[0].toLowerCase().equals("patients")){
                        return new ListPatientsCommand();
                    }
                    else if (tempCommand[0].toLowerCase().equals("tasks")){
                        return new ListTasksCommand();
                    }
                    else {
                        throw new Exception("Invalid 'list' command.");
                    }
                } catch (Exception e) {
                    throw new DukeException(e.getMessage());
                }
            case "done":
                //do thing for 'done'
            case "delete":
                try{
                    String[] tempCommand = command[1].split("\\s+", 2);
                    if (tempCommand[0].toLowerCase().equals("patient")){
                            return new DeletePatientCommand(Integer.parseInt(tempCommand[1]));
                    }
                    else {
                        throw new Exception("Invalid 'Delete' command.");
                    }
                } catch(Exception e){
                    throw new DukeException(e.getMessage());
                }
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
                throw new DukeException("Could not understand user input.");
        }
    }

}
