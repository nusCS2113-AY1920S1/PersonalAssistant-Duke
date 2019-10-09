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
                            throw new Exception("Please follow the format 'add patient <name> <NRIC> <Room> <remark>'. ");
                        }
                    }
                    else if (tempCommand[0].toLowerCase().equals("task")){
                        try {
                            StandardTask task = new StandardTask(tempCommand[1]);
                            return new AddStandardTaskCommand(task);
                        }catch(Exception e){
                            throw new Exception("Please follow the format 'add task <task description>.' ");
                        }
                    }
                    else {
                        throw new Exception("Invalid format. ");
                    }
                } catch (Exception e) {
                    throw new DukeException("Add command fails. " + e.getMessage());
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
                        throw new Exception("Invalid 'list' command. ");
                    }
                } catch (Exception e) {
                    throw new DukeException("List command fails. " + e.getMessage());
                }
            case "done":
                //do thing for 'done'
            case "delete":
                try{
                    String[] tempCommand = command[1].split("\\s+", 2);
                    if (tempCommand[0].toLowerCase().equals("patient")){
                        try {
                            int id = Integer.parseInt(tempCommand[1]);
                            return new DeletePatientCommand(id);
                        }catch(Exception e){
                            throw new Exception("Please follow the format 'delete patient <id>'.");
                        }
                    }
                    else {
                        throw new Exception("Invalid format. ");
                    }
                } catch(Exception e){
                    throw new DukeException("Delete command fails. " + e.getMessage());
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
