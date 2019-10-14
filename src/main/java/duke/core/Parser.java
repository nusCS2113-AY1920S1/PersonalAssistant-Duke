package duke.core;

public class Parser {

    String userInput;

    public Parser(String userInput) {
        this.userInput = userInput;
    }

    public String[] addParser() throws DukeException {
        String[] parsedCommand = userInput.toLowerCase().split("\\s+", 2);
        try {
            if (parsedCommand[2].equals("patient")) {
                String[] patientInfo = userInput.replace("add patient ", "").trim().split("\\s+", 4);
                return patientInfo;
            } else if (parsedCommand[2].equals("task")) {
                String[] taskInfo = userInput.replace("add task ", "").trim().split("\\s+", 2);
                return taskInfo;
            }
        } catch (Exception e) {
            throw new DukeException("Please change the format for your 'add' command.");
        }
        throw new DukeException("Failed to parse 'add' command.");
    }
}
