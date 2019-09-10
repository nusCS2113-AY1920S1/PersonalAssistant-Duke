public class Parser {
    public static Command parse(String command) throws DukeException {
        switch (command){
            case("list"):
                return new ListCommand();

            case("done"):
                return new DoneCommand();

            case ("delete"):
                return new DeleteCommand();

            case("deadline"):
                return new DeadlineCommand();

            case ("event"):
                return new EventCommand();

            case ("todo"):
                return new TodoCommand();

            case ("find"):
                return new FindCommand();

            case ("bye"):
                return new ByeCommand();

            default:
                throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");

        }
    }
}
