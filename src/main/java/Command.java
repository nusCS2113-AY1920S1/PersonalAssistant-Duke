<<<<<<< HEAD
import UI.Ui;
=======
<<<<<<< HEAD
import Storage.Storage;
>>>>>>> f549e283c42c289d7c7324cee3f0e138922257cc
=======
>>>>>>> f7938ba1adc707a6bdf34ebdb286314c7a8a91d1
>>>>>>> Jason

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public abstract class Command {
    public abstract void execute(List<Task> list, Ui ui, Storage storage) throws DukeException, ParseException, IOException, NullPointerException ;

    public abstract boolean isExit();
}
