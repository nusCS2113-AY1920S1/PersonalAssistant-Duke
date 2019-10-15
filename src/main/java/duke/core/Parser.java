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
        String[] formattedInput;
        try {
            String[] parsedCommand = userInput.toLowerCase().split("\\s+", 4);
            if (parsedCommand[1].equals("by") && parsedCommand[2].equals("id:")) {
                formattedInput = userInput.replace("assign by id: ", "").split("\\s+", 4);
                //System.out.println(formattedInput[3]);
                if (formattedInput[0].equals("E")) {
                    String[] parsedTimes = formattedInput[3].split(" to ", 2);
                    //System.out.println(parsedTimes[3] + " ?? " + parsedTimes[4]);
                    formattedInput[3] = parsedTimes[0].trim();
                    formattedInput[4] = parsedTimes[1].trim();
                }

            } else {
                throw new DukeException("Please use proper 'assign by ID' command format. ");
            }
            return formattedInput;
        } catch (Exception e) {
            throw new DukeException("Please use the correct format for the 'assign by id' command. ");
        }
    }

    
}
