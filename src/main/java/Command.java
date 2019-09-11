<<<<<<< HEAD
import UI.Ui;
=======
>>>>>>> f7938ba1adc707a6bdf34ebdb286314c7a8a91d1

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public abstract class Command {
    public abstract void execute(List<Task> list, Ui ui, Storage storage) throws DukeException, ParseException, IOException, NullPointerException ;

    public abstract boolean isExit();
}
