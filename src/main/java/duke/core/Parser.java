package duke.core;

public class Parser {

    String userInput;

    public Parser(String userInput) {
        this.userInput = userInput;
    }

    public String[] parseAdd() throws DukeException {
        String[] parsedCommand = userInput.toLowerCase().split("\\s+", 3);
        try {
            if (parsedCommand[1].equals("patient")) {
                String[] patientInfo = userInput.replace("add patient ", "").trim().split("\\s+", 4);
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

    public String[] parseAssign() throws DukeException {
        String[] formattedInput = new String[5];
        try {
            String[] parsedCommand = userInput.toLowerCase().split("\\s+", 4);
            if (parsedCommand[1].equals("by") && parsedCommand[2].equals("id:")) {

                String[] tempInput = userInput.replace("assign by id: ", "").split("\\s+", 4);
                if (tempInput[0].equals("E")) {
                    String[] parsedTimes = tempInput[3].split(" to ", 2);

                    for (int i=0; i<3; i++) {
                        formattedInput[i] = tempInput[i];
                    }
                    formattedInput[3] = parsedTimes[0];
                    formattedInput[4] = parsedTimes[1];
                } else {
                    for (int i = 0; i < tempInput.length; i++) {
                        formattedInput[i] = tempInput[i];
                    }
                }


            } else {
                throw new DukeException("Please use proper 'assign by ID' command format. ");
            }
            System.out.println(formattedInput[3]);
            return formattedInput;
        } catch (Exception e) {
            throw new DukeException("Please use the correct format for the 'assign by id' command. ");
        }
    }

    public String parseDeletePatient() throws DukeException {
        String formattedInput;
        String inputToParse = userInput.replaceAll("(?i)delete patient ", "").trim();
        formattedInput = inputToParse;
        return formattedInput;
    }

    public String parseDeleteTask() throws DukeException {
        String formattedInput;
        String inputToParse = userInput.replaceAll("(?i)delete task ", "").trim();
        formattedInput = inputToParse;
        return formattedInput;
    }

    public String parseUpdatePatient() throws DukeException {
        String formattedInput;
        String inputToParse = userInput.replaceAll("(?i)update patient ", "").trim();
        formattedInput = inputToParse;
        return formattedInput;
    }

    public String parseUpdateTask() throws DukeException {
        String formattedInput;
        String inputToParse = userInput.replaceAll("(?i)update task ", "").trim();
        formattedInput = inputToParse;
        return formattedInput;
    }
}
