package duke.core;

public class Parser {

    String userInput;

    /**
     * .
     *
     * @param userInput .
     */
    public Parser(String userInput) {
        this.userInput = userInput;
    }

    /**
     * .
     *
     * @return .
     * @throws DukeException .
     */
    public String[] parseAdd() throws DukeException {
        String[] parsedCommand = userInput.toLowerCase().split("\\s+", 3);
        try {
            if (parsedCommand[1].equals("patient")) {
                String[] patientInfo = userInput.replace(
                        "add patient ", "").trim().split("\\s+", 4);
                return patientInfo;
            } else if (parsedCommand[1].equals("task")) {
                String[] taskInfo = new String[1];
                taskInfo[0] = userInput.replace("add task ", "").trim();
                return taskInfo;
            }
        } catch (Exception e) {
            throw new DukeException("Please change the format for your 'add' command.");
        }
        throw new DukeException("Failed to parse 'add' command.");
    }

    /**
     * .
     *
     * @return .
     * @throws DukeException .
     */
    public String[] parseAssign() throws DukeException {
        String[] formattedInput = new String[5];
        try {
            String[] parsedCommand = userInput.toLowerCase().split("\\s+", 6);
            formattedInput[1] = parsedCommand[2].replace("id", "").trim();
            formattedInput[2] = parsedCommand[3];
            formattedInput[3] = parsedCommand[4];
            formattedInput[4] = parsedCommand[5];
            if (parsedCommand[1].equals("eventtask")) {
                formattedInput[0] = "E";
            } else if (parsedCommand[1].equals("standardtask")){
                formattedInput[0] = "S";
            } else {
                throw new DukeException("Please use proper 'assign standardtask/eventtask' command format. ");
            }
            return formattedInput;
        } catch (Exception e) {
            throw new DukeException("Please use the correct format for the 'assign by id' command. ");
        }
    }

    /**
     *  .
     * @return .
     * @throws DukeException .
     */
    public String parseDeletePatient() throws DukeException {
        String formattedInput;
        String inputToParse = userInput.replaceAll("(?i)delete patient ", "").trim();
        formattedInput = inputToParse;
        return formattedInput;
    }

    /**
     *  .
     * @return .
     * @throws DukeException .
     */
    public String parseDeleteTask() throws DukeException {
        String formattedInput;
        String inputToParse = userInput.replaceAll("(?i)delete task ", "").trim();
        formattedInput = inputToParse;
        return formattedInput;
    }

    /**
     *  .
     * @return .
     * @throws DukeException .
     */
    public String parseUpdatePatient() throws DukeException {
        String formattedInput;
        String inputToParse = userInput.replaceAll("(?i)update patient ", "").trim();
        formattedInput = inputToParse;
        return formattedInput;
    }

    /**
     *  .
     * @return .
     * @throws DukeException .
     */
    public String parseUpdateTask() throws DukeException {
        String formattedInput;
        String inputToParse = userInput.replaceAll("(?i)update task ", "").trim();
        formattedInput = inputToParse;
        return formattedInput;
    }
}
