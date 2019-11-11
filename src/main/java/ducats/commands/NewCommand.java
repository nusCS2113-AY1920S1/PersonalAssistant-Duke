package ducats.commands;

import ducats.DucatsException;
import ducats.Storage;
import ducats.Ui;
import ducats.components.Song;
import ducats.components.SongList;
import ducats.Parser;

//@@author jwyf
/**
 * A class representing the command to add a new song to the song list.
 */
public class NewCommand extends Command {

    /**
     * Constructor for the command to add a task to the task list.
     *
     * @param message the input message that resulted in the creation of the ducats.Commands.Command
     */
    public NewCommand(String message) {
        this.message = message;
    }

    /**
     * Create a new song and add it as the last entry of the song list
     * and returns the messages intended to be displayed.
     *
     * @param songList the ducats.components.SongList object that contains the song list
     * @param ui the Ui object responsible for the reading of user input and the display of
     *           the responses
     * @param storage the Storage object used to read and manipulate the .txt file
     * @return the string to be displayed in ducats.Duke
     * @throws DucatsException if an exception occurs in the parsing of the message or in IO
     */
    public String execute(SongList songList, Ui ui, Storage storage) throws DucatsException {
        String songName;
        String key;
        String timeSignature;
        int tempo;
        Song song;
        try {
            String[] sections = message.substring(4).split(" ");
            Parser parse =  new Parser();

            songName = sections[0];
            if (songName.replaceAll("\\s+","").equals("")) {
                throw new DucatsException(message,"whitespace_name");
            }
            if (parse.checkForSpecialCharacter(songName)) {
                throw new DucatsException(message,"special_characters");
            }
            if (!songList.findSong(songName).isEmpty()) {
                throw new DucatsException(message, "repeat_song_name");
            }

            key = sections[1];
            if (!key.equals("c")) {
                throw new DucatsException(message, "key");
            }

            timeSignature = sections[2];
            if (!timeSignature.equals("4/4")) {
                throw new DucatsException(message, "time_sig");
            }

            tempo = Integer.parseInt(sections[3]);
            if (tempo <= 0) {
                throw new DucatsException(message, "tempo");
            }
            song = new Song(songName, key, tempo);

            songList.add(song);
            storage.updateFile(songList);
            return ui.formatNewSong(songList.getSongList(), song);

        } catch (IndexOutOfBoundsException e) {
            throw new DucatsException("", "index");
        } catch (NumberFormatException e) {
            throw new DucatsException("", "number_index");
        } catch (Exception e) {
            if (e instanceof DucatsException && ((DucatsException) e).getType().equals("io")) {
                throw new DucatsException("", "io");
            } else if (e instanceof DucatsException && ((DucatsException) e).getType().equals("key")) {
                throw new DucatsException(message, "key");
            } else if (e instanceof DucatsException && ((DucatsException) e).getType().equals("time_sig")) {
                throw new DucatsException(message, "time_sig");
            } else if (e instanceof DucatsException && ((DucatsException) e).getType().equals("tempo")) {
                throw new DucatsException(message, "tempo");
            } else if (e instanceof DucatsException && ((DucatsException) e).getType().equals("repeat_song_name")) {
                throw new DucatsException(message, "repeat_song_name");
            }  else if (e instanceof DucatsException && ((DucatsException) e).getType().equals("special_characters")) {
                throw new DucatsException(message, "special_characters");
            } else if (e instanceof DucatsException && ((DucatsException) e).getType().equals("whitespace_name")) {
                throw new DucatsException(message, "whitespace_name");
            } else {
                throw new DucatsException(message, "new");
            }
        }
    }

    /**
     * Returns a boolean value representing whether the program will terminate or not, used in
     * ducats.Duke to reassign a boolean variable checked at each iteration of a while loop.
     *
     * @return a boolean value that represents whether the program will terminate after the command
     */
    @Override
    public boolean isExit() {
        return false;
    }

    //@@author rohan-av
    /**
     * Returns an integer corresponding to the duration, tempo and time signature if the command starts a metronome.
     * Else, returns an array containing -1.
     *
     * @return the integer array corresponding to the parameters of the Metronome class
     */
    @Override
    public int[] startMetronome() {
        return new int[]{-1, -1, -1, -1};
    }
}
