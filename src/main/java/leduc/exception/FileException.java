package leduc.exception;

import leduc.Ui;

public class FileException extends DukeException {
    /**
     * Constructor of leduc.exception.DukeException
     *
     * @param ui leduc.Ui which deals with the interactions with the user.
     */
    public FileException(Ui ui) {
        super(ui);
    }
    public FileException(){
        super();
    }

    @Override
    public void print() {

    }
}
