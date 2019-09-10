import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Represents a writer of file.
 */
public class WriteFile {
    private BufferedWriter bufferedW;
    private StringBuffer stringBuffer = new StringBuffer();
    private Ui ui;

    /**
     * Constructor of WriteFile.
     * @param file String representing the path of the file.
     * @param append boolean which allow to know if it is a append writer of file or a rewriter of file which is needed.
     * @param ui Ui which deals with the interactions with the user.
     */
    public WriteFile(String file, boolean append, Ui ui){
        this.ui = ui;
        try{
            FileWriter fileW = new FileWriter(file,append); // write only for append text on datafile
            this.bufferedW = new BufferedWriter(fileW);
        }
        catch(IOException e){
            ui.display("\t IOException: \n\t\t error with fileWritter");
        }
    }

    /**
     * Write the string on the file.
     * @param s String to write on the file.
     * @throws IOException Exception caught when there is a error on writing.
     */
    public void write(String s) throws IOException {
        bufferedW.write(s);
        bufferedW.flush();
    }

    /**
     * Close the buffer of the writer of file.
     */
    public void freeBufferedWriter(){
        try {
            this.bufferedW.close();
        }
        catch (IOException e){
            ui.display("\t IOException: \n\t\t error when close the bufferedWriter");
        }
    }

}
