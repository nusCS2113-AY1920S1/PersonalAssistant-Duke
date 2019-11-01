package entertainment.pro.logic.parsers.commands;

import entertainment.pro.commons.exceptions.PlaylistExceptions;
import entertainment.pro.model.MovieInfoObject;
import entertainment.pro.model.Playlist;
import entertainment.pro.model.UserProfile;
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
        String createPlaylistName = this.getPayload();
        UserProfile userProfile = new EditProfileJson().load();
        ProfileCommands profileCommands = new ProfileCommands(userProfile);
        PlaylistCommands playlistCommands = new PlaylistCommands(createPlaylistName);
        try {
            PlaylistExceptions.checkNameExist(createPlaylistName, userProfile);
            profileCommands.addPlaylist(createPlaylistName);
            playlistCommands.create();
            movieHandler.setLabels();
            movieHandler.refresh();
        } catch (PlaylistExceptions e) {
            System.out.println(e);
            movieHandler.setGeneralFeedbackText(e.getMessage());
        }
        movieHandler.clearSearchTextField();
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
        String deletePlaylistName = this.getPayload();
        UserProfile userProfile = new EditProfileJson().load();
        ProfileCommands profileCommands = new ProfileCommands(userProfile);
        try {
            PlaylistExceptions.checkPayloadPlaylist(deletePlaylistName, userProfile);
            profileCommands.deletePlaylist(deletePlaylistName);
            PlaylistCommands playlistCommands = new PlaylistCommands(deletePlaylistName);
            playlistCommands.delete();
            movieHandler.setLabels();
            movieHandler.refresh();
        } catch (PlaylistExceptions e) {
            System.out.println(e);
        }
        movieHandler.clearSearchTextField();
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
        String playlistName = this.getPayload();
        UserProfile userProfile = new EditProfileJson().load();
        if (movieHandler.getPageTracker().isMainPage()) {
            try {
                PlaylistExceptions.checkPayloadPlaylist(playlistName, userProfile);
                PlaylistCommands playlistCommands = new PlaylistCommands(playlistName);
                playlistCommands.add(this.getFlagMap(), movieHandler.getmMovies());
                movieHandler.refresh();
            } catch (PlaylistExceptions e) {
                System.out.println(e);
            }
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
        String playlistName = this.getPayload();
        UserProfile userProfile = new EditProfileJson().load();
        if (movieHandler.getPageTracker().isPlaylistInfo()) {
            try {
                PlaylistExceptions.checkPayloadPlaylist(playlistName, userProfile);
            } catch (PlaylistExceptions e) {
                System.out.println(e);
                return;
            }
            movieHandler.clearSearchTextField();
            movieHandler.setPlaylistName(playlistName);
            PlaylistCommands playlistCommands = new PlaylistCommands(this.getPayload());
            playlistCommands.remove(this.getFlagMap());
            movieHandler.refresh();
        }
        /*
        set feedback to tell user cant remove movies here?
         */
        movieHandler.clearSearchTextField();
    }

    /**
     * edit playlist's name and description.
     * root: playlist
     * sub: set
     * payload: playlist name
     * flag: -n (for new playlist name)
     *       -d (for new playlist description)
     */
    private void executeSetToPlaylist() throws IOException {
        MovieHandler movieHandler = ((MovieHandler) this.getUiController());
        String playlistName = this.getPayload();
        UserProfile userProfile = new EditProfileJson().load();
        try {
            PlaylistExceptions.checkPayloadPlaylist(playlistName, userProfile);
        } catch (PlaylistExceptions e) {
            System.out.println(e);
            return;
        }
        if (this.getFlagMap().containsKey("-n")) {
            String appendName = appendFlagMap(this.getFlagMap().get("-n"));
            try {
                PlaylistExceptions.checkNameExist(appendName, userProfile);
                PlaylistCommands playlistCommands = new PlaylistCommands(playlistName);
                playlistCommands.setToPlaylist(this.getFlagMap());
                ProfileCommands profileCommands = new ProfileCommands(new EditProfileJson().load());
                profileCommands.renamePlaylist(this.getPayload(), appendName);
                movieHandler.setPlaylistName(appendName);
                movieHandler.refresh();
            } catch (PlaylistExceptions e) {
                System.out.println(e);
            }
        } else {
            PlaylistCommands playlistCommands = new PlaylistCommands(playlistName);
            playlistCommands.setToPlaylist(this.getFlagMap());
            movieHandler.refresh();
        }
        movieHandler.clearSearchTextField();
    }

    /**
     * clear out all movies in particular playlist.
     * root: playlist
     * sub: clear
     * payload: playlist name
     * flag: none
     */
    private void executeClearPlaylist() throws IOException {
        MovieHandler movieHandler = ((MovieHandler) this.getUiController());
        String playlistName = this.getPayload();
        UserProfile userProfile = new EditProfileJson().load();
        try {
            PlaylistExceptions.checkPayloadPlaylist(playlistName, userProfile);
            PlaylistCommands playlistCommands = new PlaylistCommands(playlistName);
            playlistCommands.clear();
            movieHandler.refresh();
        } catch (PlaylistExceptions e) {
            System.out.println(e);
        }
        movieHandler.clearSearchTextField();
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
