package entertainment.pro.logic.parsers.commands;

import entertainment.pro.model.MovieInfoObject;
import entertainment.pro.storage.user.PlaylistCommands;
import entertainment.pro.storage.utils.EditProfileJson;
import entertainment.pro.storage.utils.ProfileCommands;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;
import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;

import java.io.IOException;
import java.util.ArrayList;

public class PlaylistCommand extends CommandSuper {

    public PlaylistCommand(Controller uicontroller) {
        super(COMMANDKEYS.playlist, CommandStructure.cmdStructure.get(COMMANDKEYS.playlist), uicontroller);
    }

    @Override
    public void executeCommands() throws IOException {
        switch (this.getSubRootCommand()) {
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
        case list:
            executePlaylistListing();
            break;
        case back:
            executeBackToPlaylistInfo();
            break;
        default:
            break;
        }
    }

    /**
     * create new playlist.
     * root: playlist
     * sub: create
     * payload: playlist name
     * flag: none
     */
    private void executeCreatePlaylist() throws IOException {
        MovieHandler movieHandler = ((MovieHandler) this.getUiController());
        ProfileCommands profileCommands = new ProfileCommands(new EditProfileJson().load());
        profileCommands.addPlaylist(this.getPayload());
        PlaylistCommands testCommand = new PlaylistCommands(this.getPayload());
        testCommand.create();
        movieHandler.clearSearchTextField();
        movieHandler.setLabels();
    }

    /**
     * delete playlist.
     * root: playlist
     * sub: delete
     * payload: playlist name
     * flag: none
     */
    private void executeDeletePlaylist() throws IOException {
        MovieHandler movieHandler = ((MovieHandler) this.getUiController());
        ProfileCommands profileCommands = new ProfileCommands(new EditProfileJson().load());
        profileCommands.deletePlaylist(this.getPayload());
        PlaylistCommands testCommand = new PlaylistCommands(this.getPayload());
        testCommand.delete();
        movieHandler.clearSearchTextField();
        movieHandler.setLabels();
    }

    /**
     * add movie titles to playlist.
     * root: playlist
     * sub: add
     * payload: playlist name
     * flag: -m (movie number -- not movie ID)
     */
    private void executeAddToPlaylist() throws IOException {
        MovieHandler movieHandler = ((MovieHandler) this.getUiController());
        if (movieHandler.getPageTracker().isMainPage()) {
            PlaylistCommands testCommand = new PlaylistCommands(this.getPayload());
            testCommand.add(this.getFlagMap(), movieHandler.getmMovies());
        }
        /*
        set feedback to tell user cant add movies here?
         */
        movieHandler.clearSearchTextField();
    }

    /**
     * remove movie titles from playlist.
     * root: playlist
     * sub: remove
     * payload: playlist name
     * flag: -m (movie number -- not movie ID)
     */
    private void executeRemoveFromPlaylist() throws IOException {
        MovieHandler movieHandler = ((MovieHandler) this.getUiController());
        if (movieHandler.getPageTracker().isPlaylistInfo()) {
            movieHandler.setPlaylistName(this.getPayload());
            PlaylistCommands testCommand = new PlaylistCommands(this.getPayload());
            testCommand.remove(this.getFlagMap());
            movieHandler.refresh();
        }
        /*
        set feedback to tell user cant add movies here?
         */
        movieHandler.clearSearchTextField();
    }

    /**
     * edit playlist's name and description.
     * root: playlist
     * sub: set
     * payload: playlist name
     * flag: -n (for new playlist name) -d (for new playlist description)
     * so far can only take one worded description :/:/:/:/ D:
     */
    private void executeSetToPlaylist() throws IOException {
        MovieHandler movieHandler = ((MovieHandler) this.getUiController());
        PlaylistCommands testCommand = new PlaylistCommands(this.getPayload());
        testCommand.setToPlaylist(this.getFlagMap());
        if (this.getFlagMap().containsKey("-n")) {
            String appendName = appendFlagMap(this.getFlagMap().get("-n"));
            ProfileCommands profileCommands = new ProfileCommands(new EditProfileJson().load());
            profileCommands.renamePlaylist(this.getPayload(), appendName);
            movieHandler.setPlaylistName(appendName);
        }
        movieHandler.clearSearchTextField();
        movieHandler.refresh();
    }

    /**
     * clear out all movies in particular playlist.
     * root: playlist
     * sub: clear
     * payload: playlist name
     * flag: none
     */
    private void executeClearPlaylist() throws IOException {
        MovieHandler movieHandler = ((MovieHandler)this.getUiController());
        PlaylistCommands testCommand = new PlaylistCommands(this.getPayload());
        testCommand.clear();
        movieHandler.clearSearchTextField();
        movieHandler.refresh();
    }

    /**
     * to go to the page of playlist list.
     * root: playlist
     * sub: list
     * payload: none
     * flag: none
     */
    private void executePlaylistListing() throws IOException {
        MovieHandler movieHandler = ((MovieHandler)this.getUiController());
        if (!movieHandler.getPageTracker().isPlaylistList()) {
            movieHandler.showPlaylistList();
        }
        movieHandler.clearSearchTextField();
    }

    private void executeBackToPlaylistInfo() throws IOException {
        System.out.println("yeboi we here");
        MovieHandler movieHandler = ((MovieHandler)this.getUiController());
        movieHandler.backToPlaylistInfo();
    }

    private String appendFlagMap(ArrayList<String> flagMapArrayList) {
        String appends = "";
        boolean flag = true;
        for (String log : flagMapArrayList) {
            if (!flag) {
                appends += ", ";
            }
            appends += log.trim();
            flag = false;
        }
        return appends;
    }
}
