package entertainment.pro.logic.parsers.commands;

import entertainment.pro.commons.exceptions.InvalidFormatCommandException;
import entertainment.pro.commons.exceptions.InvalidParameterException;
import entertainment.pro.commons.exceptions.logic.PlaylistExceptions;
import entertainment.pro.model.Playlist;
import entertainment.pro.model.UserProfile;
import entertainment.pro.storage.user.PlaylistCommands;
import entertainment.pro.storage.utils.EditPlaylistJson;
import entertainment.pro.storage.utils.EditProfileJson;
import entertainment.pro.storage.user.ProfileCommands;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;
import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;

import java.io.IOException;
import java.util.ArrayList;

public class PlaylistCommand extends CommandSuper {

    public PlaylistCommand(Controller uicontroller) {
        super(COMMANDKEYS.PLAYLIST, CommandStructure.cmdStructure.get(COMMANDKEYS.PLAYLIST), uicontroller);
    }

    @Override
    public void executeCommands() throws IOException {
        switch (this.getSubRootCommand()) {
        case CREATE:
            executeCreatePlaylist();
            break;
        case DELETE:
            executeDeletePlaylist();
            break;
        case ADD:
            executeAddToPlaylist();
            break;
        case REMOVE:
            executeRemoveFromPlaylist();
            break;
        case SET:
            executeSetToPlaylist();
            break;
        case CLEAR:
            executeClearPlaylist();
            break;
        case LIST:
            executePlaylistListing();
            break;
        case BACK:
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
        try {
            PlaylistExceptions.checkCreateCommand(createPlaylistName, userProfile);
            profileCommands.addPlaylist(createPlaylistName);
            PlaylistCommands playlistCommands = new PlaylistCommands(createPlaylistName);
            playlistCommands.create();
            movieHandler.setLabels();
            movieHandler.refresh();
        } catch (InvalidParameterException | InvalidFormatCommandException e) {
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
            PlaylistExceptions.checkDeleteCommand(deletePlaylistName, userProfile);
            profileCommands.deletePlaylist(deletePlaylistName);
            PlaylistCommands playlistCommands = new PlaylistCommands(deletePlaylistName);
            playlistCommands.delete();
            movieHandler.setLabels();
            movieHandler.refresh();
        } catch (InvalidParameterException | InvalidFormatCommandException e) {
            movieHandler.setGeneralFeedbackText(e.getMessage());
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
                PlaylistExceptions.checkAddCommand(playlistName, this.getFlagMap(),
                        userProfile, movieHandler.getmMovies());
                EditPlaylistJson editPlaylistJson = new EditPlaylistJson(playlistName);
                Playlist playlist = editPlaylistJson.load();
                PlaylistCommands playlistCommands = new PlaylistCommands(playlistName);
                playlist = playlistCommands.add(playlist, this.getFlagMap(), movieHandler.getmMovies());
                editPlaylistJson.editPlaylist(playlist);
            } catch (InvalidFormatCommandException | InvalidParameterException | IOException e) {
                movieHandler.setGeneralFeedbackText(e.getMessage());
            }
        } else {
            movieHandler.setGeneralFeedbackText("there are no shows here to be added :( try making a search first!");
        }
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
                PlaylistExceptions.checkRemoveCommand(playlistName, this.getFlagMap(),
                        userProfile, movieHandler.getmMovies());
                movieHandler.setPlaylistName(playlistName);
                EditPlaylistJson editPlaylistJson = new EditPlaylistJson(playlistName);
                Playlist playlist = editPlaylistJson.load();
                PlaylistCommands playlistCommands = new PlaylistCommands(playlistName);
                playlist = playlistCommands.remove(playlist, this.getFlagMap());
                editPlaylistJson.editPlaylist(playlist);
                movieHandler.refresh();
            } catch (InvalidParameterException | InvalidFormatCommandException e) {
                movieHandler.setGeneralFeedbackText(e.getMessage());
            }
        } else {
            movieHandler.setGeneralFeedbackText("there are not shows here to be removed :( "
                    + "try going into the playlist first using the command: \n playlist list");
        }
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
        EditProfileJson editProfileJson = new EditProfileJson();
        UserProfile userProfile = editProfileJson.load();
        try {
            PlaylistExceptions.checkSetCommand(playlistName, this.getFlagMap(), userProfile);
            PlaylistCommands playlistCommands = new PlaylistCommands(playlistName);
            EditPlaylistJson editPlaylistJson = new EditPlaylistJson(playlistName);
            Playlist playlist = editPlaylistJson.load();
            if (this.getFlagMap().containsKey("-n")) {
                String newName = appendFlagMap(this.getFlagMap().get("-n"));
                userProfile.renamePlaylist(playlistName, newName);
                editProfileJson.updateProfile(userProfile);
                if (movieHandler.getPlaylistName().equals(playlistName)) {
                    movieHandler.setPlaylistName(newName);
                }
                editPlaylistJson.renamePlaylist(playlist, newName);
                editPlaylistJson = new EditPlaylistJson(newName);
            }
            playlist = playlistCommands.setToPlaylist(playlist, this.getFlagMap());
            editPlaylistJson.editPlaylist(playlist);
            movieHandler.refresh();
        } catch (InvalidParameterException | InvalidFormatCommandException e) {
            movieHandler.setGeneralFeedbackText(e.getMessage());
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
            PlaylistExceptions.checkClearCommand(playlistName, userProfile);
            PlaylistCommands playlistCommands = new PlaylistCommands(playlistName);
            EditPlaylistJson editPlaylistJson = new EditPlaylistJson(playlistName);
            Playlist playlist = editPlaylistJson.load();
            playlistCommands.clear(playlist);
            editPlaylistJson.editPlaylist(playlist);
            movieHandler.refresh();
        } catch (InvalidParameterException | InvalidFormatCommandException e) {
            movieHandler.setGeneralFeedbackText(e.getMessage());
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
        } else {
            movieHandler.setGeneralFeedbackText("you are already on this page! try other commands instead");
        }
        movieHandler.clearSearchTextField();
    }

    /**
     * to back to the playlist info page after viewing a movie info in the playlist.
     * root: playlist
     * sub: back
     * payload: none
     * flag: none
     */
    private void executeBackToPlaylistInfo() throws IOException {
        System.out.println("yeboi we here");
        MovieHandler movieHandler = ((MovieHandler)this.getUiController());
        if (movieHandler.getPageTracker().isPlaylistMovieInfo()) {
            movieHandler.backToPlaylistInfo();
        } else {
            movieHandler.setGeneralFeedbackText("you can't do that here :o");
        }
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