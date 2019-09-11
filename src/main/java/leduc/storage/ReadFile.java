package leduc.storage;

import leduc.Ui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Represents a reader of file.
 */
public class ReadFile {
    private BufferedReader bufferedR;
    private Ui ui;

    /**
     * Constructor of leduc.storage.ReadFile.
     * @param file String representing of the path of the file.
     * @param ui leduc.Ui which deals with the interactions with the user.
     */
    public ReadFile(String file, Ui ui){
        this.ui=ui;
        try {
            FileReader fileReader = new FileReader(file);
            this.bufferedR = new BufferedReader(fileReader);
        }
        catch (FileNotFoundException e){
            ui.display("\t FileNotFoundException: \n\t\t the " + file +" isn't found ");
        }
    }

    /**
     * Returns the buffer of the reader.
     * @return the buffer of the reader.
     */
    public BufferedReader getBufferedReader() {
        return bufferedR;
    }

    /**
     * Allow to close the buffer of the reader.
     */
    public void freeBufferedReader(){
        try {
            this.bufferedR.close();
        }
        catch (IOException e){
            ui.display("\t IOException: \n\t\t error when close the bufferedReader");
        }
    }
}
