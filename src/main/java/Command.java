import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public abstract class Command {
    public abstract void execute(List<Task> list, Ui ui, Storage storage) throws DukeException, ParseException, IOException;

    public abstract boolean isExit();
}
