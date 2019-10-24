package entertainment.pro.logic.parsers.commands;

import entertainment.pro.storage.user.PlaylistCommands;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;
import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;

import java.io.IOException;

public class PlaylistCommand extends CommandSuper {

    public PlaylistCommand(Controller uicontroller) {
        super(COMMANDKEYS.playlist, CommandStructure.cmdStructure.get(COMMANDKEYS.playlist) , uicontroller);
    }

    @Override
    public void executeCommands() throws IOException {
        switch (this.getSubRootCommand()){
            case create:
                executeCreatePlaylist();
                break;
            case delete:
                executeDeletePlaylist();
                break;
            case add:
                executeAddToPlaylist();
                break;
            case remove:
                executeRemoveFromPlaylist();
                break;
            case set:
                executeSetToPlaylist();
                break;
            case clear:
                executeClearPlaylist();
                break;
            default:
                break;
        }
    }

    /**
     * create new playlist.
     * root: playlist
     * sub: create
     * payload: <playlist name>
     * flag: none
     */
    private void executeCreatePlaylist() throws IOException {
        MovieHandler movieHandler = ((MovieHandler) this.getUIController());
        PlaylistCommands command = new PlaylistCommands(movieHandler.getPlaylists());
        command.newPlaylist(this.getPayload());
        movieHandler.clearSearchTextField();
    }

    /**
     * delete playlist.
     * root: playlist
     * sub: delete
     * payload: <playlist name>
     * flag: none
     */
    private void executeDeletePlaylist() throws IOException {
        MovieHandler movieHandler = ((MovieHandler) this.getUIController());
        PlaylistCommands command = new PlaylistCommands(movieHandler.getPlaylists());
        command.deletePlaylist(this.getPayload());
        movieHandler.clearSearchTextField();
    }

    /**
     * add movie titles to playlist.
     * root: playlist
     * sub: add
     * payload: <playlist name>
     * flag: -m (movie number -- not movie ID)
     */
    private void executeAddToPlaylist() throws IOException {
        MovieHandler movieHandler = ((MovieHandler) this.getUIController());
        PlaylistCommands command = new PlaylistCommands(movieHandler.getPlaylists());
        command.addToPlaylist(this.getPayload(), this.getFlagMap(), movieHandler.getmMovies());
        movieHandler.clearSearchTextField();
    }

    /**
     * remove movie titles from playlist.
     * root: playlist
     * sub: remove
     * payload: <playlist name>
     * flag: -m (movie number -- not movie ID)
     */
    private void executeRemoveFromPlaylist() throws IOException {
        MovieHandler movieHandler = ((MovieHandler) this.getUIController());
        PlaylistCommands command = new PlaylistCommands(movieHandler.getPlaylists());
        command.removeFromPlaylist(this.getPayload(), this.getFlagMap(), movieHandler.getmMovies());
        movieHandler.clearSearchTextField();
    }

    /**
     * edit playlist's name and description.
     * root: playlist
     * sub: set
     * payload: <playlist name>
     * flag: -n (for new playlist name) -d (for new playlist description)
     * so far can only take one worded description :/:/:/:/ D:
     */
    private void executeSetToPlaylist() throws IOException {
        MovieHandler movieHandler = ((MovieHandler) this.getUIController());
        PlaylistCommands command = new PlaylistCommands(movieHandler.getPlaylists());
        command.setToPlaylist(this.getPayload(), this.getFlagMap());
        movieHandler.clearSearchTextField();
    }

    /**
     * clear out all movies in particular playlise
     * root: playlist
     * sub: clear
     * payload: <playlist name>
     * flag: none
     */
    private void executeClearPlaylist() throws IOException {
        MovieHandler movieHandler = ((MovieHandler)this.getUIController());
        PlaylistCommands command = new PlaylistCommands(movieHandler.getPlaylists());
        command.clearPlaylist(this.getPayload());
        movieHandler.clearSearchTextField();
    }
}
