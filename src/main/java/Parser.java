import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Parser {

    private String command;
    public String description;
    public String additional;
    private List<String> inputList;

    public void parse() {

    }

    /**
     * Constructor: reads a line from input and stores it as a list of Strongs
     */
    public Parser() {
        Scanner scanner = new Scanner(System.in);
        String inputLine = scanner.nextLine();
        inputList = Arrays.asList(inputLine.split(" "));
    }

    /**
     * This would usually contain the description of the task
     * @return the 2nd word of the input if there is one, else returns a empty string
     */
    public String getArgument(){
        if(inputList.size() > 1)
            return inputList.get(1);
        else
            return "";

    }

    /**
     * Retrieve the first word of the input
     * @return the first word of the input
     */
    public String getCommand() {
        command = inputList.get(0);
        return command;
    }

    /**
     * This method is used to get the the integer for tasks such as delete and markdone
     * @return an int value of the tasklist for the command which we want to operate on
     */
    public int getIndex() {
        int i = -1;
        try{
            if (inputList.size() <= 1) {
                throw new DukeException("", DukeException.ExceptionType.INVALID_DONE);
            } else {
                i = Integer.parseInt(inputList.get(1)) - 1;
            }
        }catch (DukeException e){
            e.PrintExceptionMessage();
        }
        return i;
    }

    /**
     *
     * @return the description of the Todo task
     */
    public String buildTodo(){
        description = String.join(" ", inputList.subList(1, inputList.size()));
        return description;
    }

    /**
     * Splits the deadline into 2 strings, the description and the deadline
     * @throws DukeException when either no description or deadline
     */
    public void buildDeadline() throws DukeException {
        String line = inputList.subList(1, inputList.size())
                .stream()
                .collect(Collectors.joining(" "));
        String[] descriptionBy = line.split("/by ");
        if(descriptionBy.length != 2){
            throw new DukeException("", DukeException.ExceptionType.INVALID_DEADLINE);
        }
        description = descriptionBy[0];
        additional = descriptionBy[1];

    }

    /**
     * Splits the event input into 2 strings, the description and the event
     */
    public void buildEvent() {
        String line = inputList.subList(1, inputList.size())
                .stream()
                .collect(Collectors.joining(" "));
        String[] descriptionBy = line.split("/at ");
        description = descriptionBy[0];
        additional = descriptionBy[1];
    }



}
