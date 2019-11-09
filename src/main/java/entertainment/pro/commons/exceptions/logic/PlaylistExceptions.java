package entertainment.pro.commons.exceptions.logic;

import entertainment.pro.commons.PromptMessages;
import entertainment.pro.commons.exceptions.InvalidFormatCommandException;
import entertainment.pro.commons.exceptions.InvalidParameterException;
import entertainment.pro.model.MovieInfoObject;
import entertainment.pro.model.Playlist;
import entertainment.pro.model.PlaylistMovieInfoObject;
import entertainment.pro.model.UserProfile;
import entertainment.pro.storage.utils.EditPlaylistJson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * logic for handling exceptions in playlist commands.
 */
public class PlaylistExceptions {
    /**
     * to check if create command is in correct format.
     * command: playlist create PLAYLIST_NAME
     * checks: whether user input payload
     *         whether playlist name for new playlist is in use
     */
    public static void checkCreateCommand(String payload, UserProfile userProfile)
            throws InvalidFormatCommandException, InvalidParameterException {
        checkPayload(payload);
        checkNameExist(payload, userProfile);
    }

    /**
     * to check if delete command is in correct format.
     * command: playlist delete PLAYLIST_NAME
     * checks: whether user input payload
     *         whether playlist to be deleted exists
     */
    public static void checkDeleteCommand(String payload, UserProfile userProfile)
            throws InvalidFormatCommandException, InvalidParameterException {
        checkPayload(payload);
        checkPayloadPlaylist(payload, userProfile);
    }

    /**
     * to check if add command is in correct format.
     * command: playlist add PLAYLIST_NAME [-m INDEX...]
     * checks: whether user input payload
     *         whether playlist to be edited exists
     *         whether INDEX is a number
     *         whether INDEX is within range
     *         whether the movie is already in the playlist
     */
    public static void checkAddCommand(String payload, Map<String, ArrayList<String>> flagMap,
                                       UserProfile userProfile, ArrayList<MovieInfoObject> movies)
            throws InvalidFormatCommandException, InvalidParameterException, IOException {
        checkPayload(payload);
        checkPayloadPlaylist(payload, userProfile);
        Playlist playlist = new EditPlaylistJson(payload).load();
        for (Map.Entry<String, ArrayList<String>> flag : flagMap.entrySet()) {
            checkFlagMap(flag);
            for (String log : flag.getValue()) {
                checkIndexInput(log);
                int index = Integer.parseInt(log.trim()) - 1;
                checkIndex(index, movies.size());
                checkMovieForAdd(movies.get(index), playlist);
            }
        }
    }

    /**
     * to check if remove command is in correct format.
     * command: playlist remove PLAYLIST_NAME [-m INDEX...]
     * checks: whether user input payload
     *         whether playlist to be edited exists
     *         whether INDEX is a number
     *         whether INDEX is within range (if within range then movie is already in playlist and can be deleted)
     */
    public static void checkRemoveCommand(String payload, Map<String, ArrayList<String>> flagMap,
                                          UserProfile userProfile, ArrayList<MovieInfoObject> movies)
            throws InvalidFormatCommandException, InvalidParameterException {
        checkPayload(payload);
        checkPayloadPlaylist(payload, userProfile);
        for (Map.Entry<String, ArrayList<String>> flag : flagMap.entrySet()) {
            checkFlagMap(flag);
            for (String log : flag.getValue()) {
                checkIndexInput(log);
                int index = Integer.parseInt(log.trim()) - 1;
                System.out.println("ehehehheheeheh" + movies.size());
                checkIndex(index, movies.size());
            }
        }
    }

    /**
     * to check if set command is in correct format.
     * command: playlist set PLAYLIST_NAME [-n NEW_PLAYLIST_NAME] [-d NEW_DESCRIPTION]
     * checks: whether user input payload
     *         whether playlist to be edited exists
     *         whether NEW_PLAYLIST_NAME is in use
     */
    public static void checkSetCommand(String payload, Map<String, ArrayList<String>> flagMap,
                                       UserProfile userProfile)
            throws InvalidFormatCommandException, InvalidParameterException {
        checkPayload(payload);
        checkPayloadPlaylist(payload, userProfile);
        for (Map.Entry<String, ArrayList<String>> flag : flagMap.entrySet()) {
            checkSetFlagMap(flag);
            if (flag.getKey().equals("-n")) {
                String newName = appendFlagMap(flag.getValue());
                checkNameExist(newName, userProfile);
            }
        }
    }

    /**
     * to check if clear command is in correct format.
     * command: playlist clear PLAYLIST_NAME
     * checks: whether user input payload
     *         whether playlist to be edited exists
     */
    public static void checkClearCommand(String payload, UserProfile userProfile)
            throws InvalidFormatCommandException, InvalidParameterException {
        checkPayload(payload);
        checkPayloadPlaylist(payload, userProfile);
    }

    /**
     * to check whether that command's payload is empty.
     * command: all playlist commands
     * @throws InvalidFormatCommandException when payload is empty
     */
    private static void checkPayload(String payload) throws InvalidFormatCommandException {
        if (payload.isEmpty()) {
            throw new InvalidFormatCommandException(PromptMessages.PLAYLIST_PAYLOAD_EMPTY);
        }
    }

    /**
     * to check whether playlist with that name exists.
     * command: create / set -n
     * @throws InvalidParameterException when that name already belongs to an existing playlist
     */
    private static void checkNameExist(String name, UserProfile userProfile) throws InvalidParameterException {
        for (String log : userProfile.getPlaylistNames()) {
            if (name.equals(log)) {
                throw new InvalidParameterException(PromptMessages.PLAYLIST_EXISTS_START + name
                        + PromptMessages.PLAYLIST_EXISTS_END);
            }
        }
    }

    /**
     * to check whether a particular movie can be added to playlist.
     * command: add -m
     * @throws InvalidParameterException when movie already belongs to playlist
     */
    private static void checkMovieForAdd(MovieInfoObject movie, Playlist playlist) throws InvalidParameterException {
        boolean flag = false;
        for (PlaylistMovieInfoObject log : playlist.getMovies()) {
            if (movie.getId() == log.getId()) {
                flag = true;
                break;
            }
        }
        if (flag) {
            throw new InvalidParameterException(PromptMessages.CANNOT_ADD_TO_PLAYLIST_START + movie.getMovieTitle()
                    + PromptMessages.CANNOT_ADD_TO_PLAYLIST_END);
        }
    }

    /**
     * to check whether playlist name in playload exists.
     * command: set / add / remove / clear / delete
     * @throws InvalidParameterException when payload playlist does not exist
     */
    private static void checkPayloadPlaylist(String name, UserProfile userProfile) throws InvalidParameterException {
        boolean flag = true;
        for (String log : userProfile.getPlaylistNames()) {
            if (name.equals(log)) {
                flag = false;
                break;
            }
        }
        if (flag) {
            throw new InvalidParameterException(PromptMessages.PLAYLIST_DOES_NOT_EXISTS_START + name
                    + PromptMessages.PLAYLIST_DOES_NOT_EXISTS_END);
        }
    }

    /**
     * to check whether the index indicated is out of range.
     * command: add -m / remove -m
     * @throws InvalidParameterException when index is out of bounds
     */
    private static void checkIndex(int i, int size) throws InvalidParameterException {
        if ((i < 0) || (i >= size)) {
            throw new InvalidParameterException(PromptMessages.INDEX_OUT_OF_BOUNDS_START + ++i
                    + PromptMessages.INDEX_OUT_OF_BOUNDS_END + size);
        }
    }

    /**
     * check whether user input a number for movie index.
     * @throws InvalidParameterException when user input characters instead
     */
    private static void checkIndexInput(String input) throws InvalidParameterException {
        boolean flag = false;
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            flag = true;
        }
        if (flag) {
            throw new InvalidParameterException(PromptMessages.INDEX_NOT_NUMBER_START + input
                    + PromptMessages.INDEX_NOT_NUMBER_END);
        }
    }

    /**
     * check whether flag used is valid for this command.
     * command: add / remove
     * @throws InvalidFormatCommandException when flag is not [-m]
     */
    private static void checkFlagMap(Map.Entry<String, ArrayList<String>> log) throws InvalidFormatCommandException {
        if (!log.getKey().equals("-m")) {
            throw new InvalidFormatCommandException(PromptMessages.INVALID_FLAG_START + log.getKey()
                    + PromptMessages.INVALID_FLAG_END);
        }
    }

    /**
     * check whether flag used is valid for this command.
     * command: set
     * @throws InvalidFormatCommandException when flag is not [-n] or [-d]
     */
    private static void checkSetFlagMap(Map.Entry<String, ArrayList<String>> log)
            throws InvalidFormatCommandException {
        boolean flag = true;
        if (log.getKey().equals("-n") ||  log.getKey().equals("-d")) {
            flag = false;
        }
        if (flag) {
            throw new InvalidFormatCommandException(PromptMessages.INVALID_FLAG_START + log.getKey()
                    + PromptMessages.INVALID_FLAG_END);
        }
    }

    private static String appendFlagMap(ArrayList<String> flagMapArrayList) {
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