package duke.commands;

import duke.Duke;
import duke.DukeException;
import duke.Storage;
import duke.Ui;
import duke.components.*;

import java.util.ArrayList;

/**
 * A class representing the command to copy bars or verses and paste them within the same track.
 */
public class CopyCommand extends Command<SongList> {

    private String message;
    private Song song; //current working song
    /**
     * Constructor for the command to copy and paste bars or verse.
     * @param message the input message that resulted in the creation of the duke.Commands.Command
     */
    public CopyCommand(String message) {
        this.message = message.trim();
    }


    /**
     * Copy bars between a certain range and paste it between a certain range
     * in song creator.
     * @param songList the duke.TaskList or duke.components.SongList object that contains the task list in use
     * @param ui the Ui object responsible for the reading of user input and the display of
     *           the responses
     * @param storage the Storage object used to read and manipulate the .txt file
     * @return the string to be displayed in duke.Duke
     * @throws DukeException if an exception occurs in the parsing of the message or in IO
     */
    @Override
    public String execute(SongList songList, Ui ui, Storage storage) throws DukeException {
        //copy 2 4 6
        //copy 2 4
        //copy <versename> 6
        //copy <versename>

        if (message.length() < 5 || !message.substring(0,4).equals("copy")) {
            //exception if not fully spelt
            throw new DukeException(message);
        }
        try {
            message = message.substring(5).trim();
            String[] sections = message.split(" ");
            if (sections.length < 1 || sections.length > 3) {
                throw new DukeException(message, "copy");
            }
            //trimmer
            for (int i = 0; i < sections.length; i++) {
                sections[i] = sections[i].trim();
            }
            if (sections.length == 1) {
                //copy the verse to the end of the song list
                String verseName = sections[0];
                copyVerseToEnd(verseName);
                return ui.formatCopy(verseName, null, null, null, 0);
            } else if (sections.length == 2) {
                try {
                    //copy the bars between the starting num and ending num to the end of the song
                    int startNum = Integer.parseInt(sections[0]);
                    int endNum = Integer.parseInt(sections[1]);
                    copyBarsToEnd(startNum, endNum);
                    return ui.formatCopy(null, startNum, endNum, null, 1);
                } catch (NumberFormatException e) {
                    //copy the verse and insert it between the numbers stated
                    String verseName = sections[0].trim();
                    int startNum = Integer.parseInt(sections[1]);
                    insertVerse(verseName, startNum);
                    return ui.formatCopy(verseName, null, null, startNum, 2);
                }
            } else if (sections.length == 3) {
                //copy the bars between the a range into a new range
                int copyStartNum = Integer.parseInt(sections[0]);
                int copyEndNum = Integer.parseInt(sections[1]);
                int pasteStartNum = Integer.parseInt(sections[2]);
                insertCopiedBars(copyStartNum, copyEndNum, pasteStartNum);
                return ui.formatCopy(null, copyStartNum, copyEndNum, pasteStartNum, 3);
            } else {
                throw new DukeException(message, "copy");
            }
        } catch (Exception e) {
            throw new DukeException(message, "copy");
        }
    }

    /**
     * Returns a boolean value representing whether the program will terminate or not, used in
     * duke.Duke to reassign a boolean variable checked at each iteration of a while loop.
     *
     * @return a boolean value that represents whether the program will terminate after the command
     */
    @Override
    public boolean isExit() {
        return false;
    }

    public void copyVerseToEnd(String name) throws DukeException{
        //initialize VerseList to obtain its services
        VerseList verseList = new VerseList();
        Group verseBars = verseList.find(name);
        if(verseBars == null){
            throw new DukeException("", "copy");
        }
        ArrayList<Bar> songBars = song.getBars();
        int verseSize = verseBars.size();
        for(int i=0; i< verseSize; i++){
            songBars.add(verseBars.get(i));
        }
        song.updateBars(songBars);
    }

    public void insertVerse(String name, int i) throws DukeException{
        if(i < 1 || i > song.getNumBars()){
            throw new DukeException("", "copy");
        }
        VerseList verseList = new VerseList();
        Group verseBars = verseList.find(name);
        if(verseBars == null){
            throw new DukeException("", "copy");
        }
        ArrayList<Bar> songBars = song.getBars();
        ArrayList<Bar> tempBars = song.getBars();
        int verseSize = verseBars.size();
        for(int j = i-1; j < songBars.size(); j++){
            tempBars.add(songBars.get(j));
        }

        for(int j=0; j<verseSize; j++){
            songBars.set(i, tempBars.get(j));
            i++;
        }

        int tempBarsSize = tempBars.size();
        for(int j = 0; j < tempBarsSize; j++){
            songBars.add(tempBars.get(j));
        }
        song.updateBars(songBars);
    }

    public void copyBarsToEnd(int copyStart, int copyEnd) throws DukeException{
        int songNumBars = song.getNumBars();
        if(copyStart < 1 || copyEnd < 1
            || copyStart > songNumBars || copyStart > songNumBars
            || copyEnd < copyStart){
            throw new DukeException("", "copy");
        }
        ArrayList<Bar> copyBars = new ArrayList<>();
        ArrayList<Bar> songBars = song.getBars();
        for(int i=copyStart-1; i < copyEnd; i++){
            copyBars.add(songBars.get(i));
        }
        for(int i=0; i<copyBars.size(); i++){
            songBars.add(copyBars.get(i));
        }
        song.updateBars(songBars);
    }

    public void insertCopiedBars(int copyStart, int copyEnd, int pasteStart) throws DukeException{
        int songNumBars = song.getNumBars();
        if(copyStart < 1 || copyEnd < 1 || pasteStart < 1
                || copyStart > songNumBars || copyStart > songNumBars || pasteStart > songNumBars
                || copyEnd < copyStart){
            throw new DukeException("", "copy");
        }
        ArrayList<Bar> copyBars = new ArrayList<>();
        ArrayList<Bar> songBars = song.getBars();
        ArrayList<Bar> tempBars = new ArrayList<>();
        for(int i=copyStart-1; i < copyEnd; i++){
            copyBars.add(songBars.get(i));
        }
        for(int i=copyEnd; i<songBars.size(); i++){
            tempBars.add(songBars.get(i));
        }
        for(int i=0; i<copyBars.size(); i++){
            songBars.set(pasteStart-1, tempBars.get(i));
            pasteStart++;
        }
        for(int i=0; i<tempBars.size(); i++){
            songBars.set(pasteStart, tempBars.get(i));
            pasteStart++;
        }
        song.updateBars(songBars);
    }
}
