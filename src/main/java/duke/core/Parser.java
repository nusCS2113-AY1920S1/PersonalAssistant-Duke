//@@lmtaek

package duke.core;

public class Parser {

    String userInput;
    String[] parsedInput;

    /**
     * Constructor for the Parser class.
     *
     * @param userInput Takes in user's raw input and stores it to use in its methods, parsing it
     *                  into a format that is appropriate for the command it invokes.
     */
    public Parser(String userInput) throws DukeException {
        this.userInput = userInput.trim();
        try {
            parsedInput = userInput.split(":");
        } catch (Exception e) {
            throw new DukeException("Could not parse user input!");
        }
    }

    /**
     * Parses user input so that it is compatible with `add patient` command.
     * `add patient` output: patient_name, patient_NRIC, patient_room, patient_remark
     *
     * @return A formatted string that will work for the available `add patient` command.
     * @throws DukeException If the method is unable to parse the userInput correctly, it will throw
     *                       a DukeException.
     */
    public String[] parseAddPatient() throws DukeException {
        String[] formattedOutput = new String[4];
        try {
            for (int i = 1; i <= formattedOutput.length; i++) {
                formattedOutput[i - 1] = parsedInput[i].trim();
            }
            return formattedOutput;
        } catch (Exception e) {
            throw new DukeException("Please follow the  "
                    + "`add patient :<patient name> :<NRIC> :<patient room> :<patient_remark>` "
                    + "format.");
        }
    }

    /**
     * Parses user input so that it is compatible with `add task` command.
     * `add task` output: task_description
     *
     * @return A formatted string that will work for the available `add task` command.
     * @throws DukeException If the method is unable to parse the userInput correctly, it will throw
     *                       a DukeException.
     */
    public String parseAddTask() throws DukeException {
        String formattedOutput;
        try {
            formattedOutput = parsedInput[1].trim();
        } catch (Exception e) {
            throw new DukeException("Please follow the `add task :<task description>` format.");
        }
        return formattedOutput;
    }

    /**
     * Takes the user input and formats it so it is compatible with `assign deadline task` commands.
     * `assign standard task` output: patient_name or #patient_id, task_name or #task_id, deadline
     *
     * @return A string of formatted output to be used by `assign deadline task` command.
     * @throws DukeException Thrown when the user input cannot be parsed in the desired manner.
     */
    public String[] parseAssignDeadlineTask() throws DukeException {
        String[] formattedInput = new String[3];
        try {
            for (int i = 1; i <= formattedInput.length; i++) {
                formattedInput[i - 1] = parsedInput[i].trim();
            }
            return formattedInput;
        } catch (Exception e) {
            throw new DukeException("Please follow the "
                    + "`assign standard task :<patient name> or #<patient id> :#<task id> or <task name> "
                    + ":<dd/MM/YYYY HHmm>` format.");
        }
    }

    /**
     * Takes the user input and formats it so it is compatible with `assign event task` command.
     * `assign event task` output: patient_name or #patient_id, task_name or #task_id, start_time, end_time
     *
     * @return A string of formatted output to be used by `assign event task` command.
     * @throws DukeException Thrown when the user input cannot be parsed in the desired manner.
     */
    public String[] parseAssignEventTask() throws DukeException {
        String[] formattedInput = new String[4];
        try {
            String[] parsedTimes = parsedInput[3].split(" to ");
            for (int i = 1; i < (formattedInput.length - 1); i++) {
                formattedInput[i - 1] = parsedInput[i].trim();
            }

            formattedInput[2] = parsedTimes[0];
            formattedInput[3] = parsedTimes[1];
            return formattedInput;
        } catch (Exception e) {
            throw new DukeException("Please follow the "
                    + "`assign event task :<patient name> or #<patient id> :#<task ID> or <task name> "
                    + ":<dd/MM/YYYY HHmm> to <dd/MM/YYYY HHmm>` format.");
        }
    }

    /**
     * Takes the user input and formats it so it is compatible with 'delete patient' command.
     * `delete patient` output: patient_name or #patient_id
     *
     * @return A string of formatted output to be used by 'delete patient' commands.
     * @throws DukeException Thrown when the user input cannot be parsed in the desired manner.
     */
    public String parseDeletePatient() throws DukeException {
        try {
            String formattedInput;
            formattedInput = parsedInput[1].trim();
            return formattedInput;
        } catch (Exception e) {
            throw new DukeException("Please follow the `delete patient :<patient name> or #<patient id>` format");
        }
    }

    /**
     * Takes the user input and formats it so it is compatible with 'delete task' command.
     * `delete task` output: task_id or task_name
     *
     * @return A string of formatted output to be used by 'delete task' commands.
     * @throws DukeException Thrown when the user input cannot be parsed in the desired manner.
     */
    public String parseDeleteTask() throws DukeException {
        try {
            String formattedInput;
            formattedInput = parsedInput[1].trim();
            return formattedInput;
        } catch (Exception e) {
            throw new DukeException("Please follow the `delete task :<task name> or #<task id>` format");
        }
    }

    /**
     * Takes user input and formats it so it is compatible with 'delete patient task' command.
     * `delete patient task` output: patient_name or #patient_id, task_name or #task_id
     * `delete patient task` output: assigned_task_unique_id
     *
     * @return Array of strings to be used by 'delete patient task' command.
     * @throws DukeException when user input cannot be parsed properly.
     */
    public String[] parseDeleteAssignedTask() throws DukeException {
        try {
            String[] formattedInput = new String[2];

            if (parsedInput[1].trim().charAt(0) == '%') {
                formattedInput[0] = parsedInput[1];
                return formattedInput;
            } else {
                for (int i = 1; i <= formattedInput.length; i++) {
                    formattedInput[i - 1] = parsedInput[i].trim();
                }
                return formattedInput;
            }
        } catch (Exception e) {
            throw new DukeException("Please follow the `delete patient task :<patient name> or #<patient id>"
                    + " :<task name> or #<task id>` or "
                    + " `delete patient task :%<unique assigned task id>` format.");
        }
    }

    /**
     * Takes the user input and formats it so it is compatible with 'update patient' command.
     * `update patient` output: patient_name or #patient_id, edited_field, updated_info
     *
     * @return A string of formatted output to be used by 'update patient' commands.
     * @throws DukeException Thrown when the user input cannot be parsed in the desired manner.
     */
    public String[] parseUpdatePatient() throws DukeException {
        try {
            String[] formattedInput = new String[3];

            for (int i = 1; i <= formattedInput.length; i++) {
                formattedInput[i - 1] = parsedInput[i].trim();
            }
            return formattedInput;
        } catch (Exception e) {
            throw new DukeException("Please use the `update patient :<patient name> or #<patient id>"
                    + "/<edited info type> :<updated info>` format.");
        }
    }

    /**
     * Takes the user input and formats it so it is compatible with 'update task' command.
     * `update task` output: task_name or #task_id, updated_description
     *
     * @return A string of formatted output to be used by 'update task' commands.
     * @throws DukeException Thrown when the user input cannot be parsed in the desired manner.
     */
    public String[] parseUpdateTask() throws DukeException {
        try {
            String[] formattedInput = new String[2];
            for (int i = 1; i <= formattedInput.length; i++) {
                formattedInput[i - 1] = parsedInput[i].trim();
            }
            return formattedInput;
        } catch (Exception e) {
            throw new DukeException("Please use the `update patient :<task name> or #<task id>"
                    + " :<updated description>` format.");
        }
    }

    /**
     * Takes the user input and formats it so it is compatible with 'find patient' command.
     * `find patient` output: patient_name or #patient_id
     *
     * @return A string of formatted output to be used by 'find patient' command.
     * @throws DukeException Thrown when the user input cannot be parsed in the desired manner.
     */
    public String parseFindPatient() throws DukeException {
        try {
            String formattedInput = parsedInput[1].trim();
            return formattedInput;
        } catch (Exception e) {
            throw new DukeException("Please use the `find patient :<patient name> or #<patient id>` format.");
        }
    }

    /**
     * Takes the user input and formats it so it is compatible with 'find patient task' command.
     * `find patient tasks` output: patient_name or #patient_id
     *
     * @return A string of formatted output to be used by 'find patient task' command.
     * @throws DukeException Thrown when the user input cannot be parsed in the desired manner.
     */
    public String parseFindPatientTasks() throws DukeException {
        try {
            String formattedInput = parsedInput[1].trim();
            return formattedInput;
        } catch (Exception e) {
            throw new DukeException("Please use the `find patient tasks :<patient name> or #<patient id>` format.");
        }
    }
}