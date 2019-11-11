package entertainment.pro.logic.parsers.commands;

import entertainment.pro.commons.exceptions.InvalidFormatCommandException;
import entertainment.pro.commons.exceptions.InvalidParameterException;
import entertainment.pro.commons.exceptions.logic.PlaylistExceptions;
import entertainment.pro.commons.strings.PromptMessages;
import entertainment.pro.model.Playlist;
import entertainment.pro.model.UserProfile;
import entertainment.pro.storage.user.PlaylistCommands;
import entertainment.pro.storage.utils.EditPlaylistJson;
import entertainment.pro.storage.utils.EditProfileJson;
import entertainment.pro.storage.user.ProfileCommands;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;
import entertainment.pro.commons.enums.CommandKeys;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlaylistCommand extends CommandSuper {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public PlaylistCommand(Controller uiController) {
        super(CommandKeys.PLAYLIST, CommandStructure.cmdStructure.get(CommandKeys.PLAYLIST), uiController);
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
        UserProfile userProfile = movieHandler.getUserProfile();
        try {
            PlaylistExceptions.checkCreateCommand(createPlaylistName, userProfile);
            PlaylistCommands command = new PlaylistCommands(createPlaylistName);
            command.create();
            ProfileCommands profileCommands = new ProfileCommands(userProfile);
            userProfile = profileCommands.addPlaylist(createPlaylistName);
            new EditProfileJson().updateProfile(userProfile);
            movieHandler.setLabels();
            movieHandler.refresh();
            logger.log(Level.INFO, PromptMessages.PLAYLIST_CREATED);
        } catch (InvalidParameterException | InvalidFormatCommandException e) {
            movieHandler.setGeneralFeedbackText(e.getMessage());
            logger.log(Level.WARNING, PromptMessages.PLAYLIST_CREATE_ERROR);
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
        UserProfile userProfile = movieHandler.getUserProfile();
        try {
            PlaylistExceptions.checkDeleteCommand(deletePlaylistName, userProfile);
            ProfileCommands profileCommands = new ProfileCommands(userProfile);
            userProfile = profileCommands.deletePlaylist(deletePlaylistName);
            PlaylistCommands playlistCommands = new PlaylistCommands(deletePlaylistName);
            playlistCommands.delete();
            new EditProfileJson().updateProfile(userProfile);
            movieHandler.setLabels();
            movieHandler.refresh();
            logger.log(Level.INFO, PromptMessages.PLAYLIST_DELETED);
        } catch (InvalidParameterException | InvalidFormatCommandException e) {
            movieHandler.setGeneralFeedbackText(e.getMessage());
            logger.log(Level.WARNING, PromptMessages.PLAYLIST_DELETE_ERROR);
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
    private void executeAddToPlaylist() {
        MovieHandler movieHandler = ((MovieHandler) this.getUiController());
        String playlistName = this.getPayload();
        UserProfile userProfile = movieHandler.getUserProfile();
        if (movieHandler.getPageTracker().isMainPage()) {
            try {
                PlaylistExceptions.checkAddCommand(playlistName, this.getFlagMap(),
                        userProfile, movieHandler.getmMovies());
                EditPlaylistJson editPlaylistJson = new EditPlaylistJson(playlistName);
                Playlist playlist = editPlaylistJson.load();
                PlaylistCommands playlistCommands = new PlaylistCommands(playlistName);
                playlist = playlistCommands.add(playlist, this.getFlagMap(), movieHandler.getmMovies());
                editPlaylistJson.editPlaylist(playlist);
                logger.log(Level.INFO, PromptMessages.SHOWS_ADDED);
            } catch (InvalidFormatCommandException | InvalidParameterException | IOException e) {
                movieHandler.setGeneralFeedbackText(e.getMessage());
                logger.log(Level.WARNING, PromptMessages.SHOWS_ADD_ERROR);
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
        UserProfile userProfile = movieHandler.getUserProfile();
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
                logger.log(Level.INFO, PromptMessages.SHOWS_REMOVED);
                movieHandler.refresh();
            } catch (InvalidParameterException | InvalidFormatCommandException e) {
                movieHandler.setGeneralFeedbackText(e.getMessage());
                logger.log(Level.WARNING, PromptMessages.SHOWS_REMOVE_ERROR);

            }
        } else {
            movieHandler.setGeneralFeedbackText("there are not shows here to be removed :( "
                    + "try going into the playlist first using the command: playlist list");
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
        UserProfile userProfile = movieHandler.getUserProfile();
        try {
            PlaylistExceptions.checkSetCommand(playlistName, this.getFlagMap(), userProfile);
            PlaylistCommands playlistCommands = new PlaylistCommands(playlistName);
            EditPlaylistJson editPlaylistJson = new EditPlaylistJson(playlistName);
            Playlist playlist = editPlaylistJson.load();
            if (this.getFlagMap().containsKey("-n")) {
                String newName = appendFlagMap(this.getFlagMap().get("-n"));
                userProfile.renamePlaylist(playlistName, newName);
                new EditProfileJson().updateProfile(userProfile);
                if (movieHandler.getPlaylistName().equals(playlistName)) {
                    movieHandler.setPlaylistName(newName);
                }
                editPlaylistJson.renamePlaylist(playlist, newName);
                editPlaylistJson = new EditPlaylistJson(newName);
                logger.log(Level.INFO, PromptMessages.PLAYLIST_RENAMED);
            }
            playlist = playlistCommands.setToPlaylist(playlist, this.getFlagMap());
            editPlaylistJson.editPlaylist(playlist);
            movieHandler.refresh();
            logger.log(Level.INFO, PromptMessages.PLAYLIST_DESCRIPTION);
        } catch (InvalidParameterException | InvalidFormatCommandException e) {
            movieHandler.setGeneralFeedbackText(e.getMessage());
            logger.log(Level.WARNING, PromptMessages.SETTING_ERROR);
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
        UserProfile userProfile = movieHandler.getUserProfile();
        try {
            PlaylistExceptions.checkClearCommand(playlistName, userProfile);
            PlaylistCommands playlistCommands = new PlaylistCommands(playlistName);
            EditPlaylistJson editPlaylistJson = new EditPlaylistJson(playlistName);
            Playlist playlist = editPlaylistJson.load();
            playlist = playlistCommands.clear(playlist);
            editPlaylistJson.editPlaylist(playlist);
            movieHandler.refresh();
            logger.log(Level.INFO, PromptMessages.PLAYLIST_CLEARED);
        } catch (InvalidParameterException | InvalidFormatCommandException e) {
            movieHandler.setGeneralFeedbackText(e.getMessage());
            logger.log(Level.WARNING, PromptMessages.PLAYLIST_CLEARED_ERROR);
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
            logger.log(Level.INFO, PromptMessages.PLAYLIST_LISTED);
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
        MovieHandler movieHandler = ((MovieHandler)this.getUiController());
        if (movieHandler.getPageTracker().isPlaylistMovieInfo()) {
            movieHandler.backToPlaylistInfo();
            logger.log(Level.INFO, PromptMessages.PLAYLIST_BACKED);
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