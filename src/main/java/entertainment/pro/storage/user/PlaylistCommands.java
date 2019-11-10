package entertainment.pro.storage.user;

import entertainment.pro.model.MovieInfoObject;
import entertainment.pro.model.Playlist;
import entertainment.pro.model.PlaylistMovieInfoObject;
import entertainment.pro.storage.utils.EditPlaylistJson;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

/**
 * class that contains all methods that deal with individual Playlist object and the list of Playlist objects.
 */
public class PlaylistCommands {
    String playlistName;
    private EditPlaylistJson editPlaylistJson;

    public PlaylistCommands(String name) {
        playlistName = name;
        editPlaylistJson = new EditPlaylistJson(name);
    }

    public void create() throws IOException {
        Playlist playlist = new Playlist(playlistName);
        editPlaylistJson.createPlaylist(playlist);
    }

    public void delete() {
        editPlaylistJson.deletePlaylist();
    }


    /**
     * to add movies to playlist.
     */
    public Playlist add(Playlist playlist, TreeMap<String, ArrayList<String>> flagMap,
                        ArrayList<MovieInfoObject> movies) throws IOException {
        ArrayList<MovieInfoObject> playlistMovies = new ArrayList<>();
        for (String log : flagMap.get("-m")) {
            int index = Integer.parseInt(log.trim()) - 1;
            playlistMovies.add(movies.get(index));
        }
        ArrayList<PlaylistMovieInfoObject> newPlaylistMovies = convert(playlistMovies);
        return playlist.add(newPlaylistMovies);
    }

    /**
     * to remove movies from playlist.
     */
    public Playlist remove(Playlist playlist, TreeMap<String, ArrayList<String>> flagMap) throws IOException {
        ArrayList<PlaylistMovieInfoObject> toDelete = new ArrayList<>();
        for (String log : flagMap.get("-m")) {
            int index = Integer.parseInt(log.trim()) - 1;
            toDelete.add(playlist.getMovies().get(index));

        }
        return playlist.remove(toDelete);
    }

    /**
     * to clear all movies in playlist.
     */
    public Playlist clear(Playlist playlist) throws IOException {
        return playlist.clear();
    }

    /**
     * set name/description to particular Playlist object.
     */
    public Playlist setToPlaylist(Playlist playlist, TreeMap<String, ArrayList<String>> flagMap) throws IOException {
        if (flagMap.containsKey("-n") && !flagMap.containsKey("-d")) {
            return setPlaylistName(playlist, appendFlagMap(flagMap.get("-n")));
        } else if (flagMap.containsKey("-d") && !flagMap.containsKey("-n")) {
            return setPlaylistDescription(playlist, appendFlagMap(flagMap.get("-d")));
        } else {
            return setAll(playlist, appendFlagMap(flagMap.get("-n")), appendFlagMap(flagMap.get("-d")));
        }
    }

    /**
     * change name of particular Playlist object.
     */
    private Playlist setPlaylistName(Playlist playlist, String newName) {
        return playlist.setPlaylistName(newName);
    }

    /**
     * change description of particular Playlist object.
     */
    private Playlist setPlaylistDescription(Playlist playlist, String description) {
        return playlist.setDescription(description);
    }

    /**
     * to allow setting of both name and description at the same time.
     */
    private Playlist setAll(Playlist playlist, String newName, String description) {
        return playlist.setBoth(newName, description);
    }

    private ArrayList<PlaylistMovieInfoObject> convert(ArrayList<MovieInfoObject> movies) {
        ArrayList<PlaylistMovieInfoObject> convertMovies = new ArrayList<>();
        for (MovieInfoObject log : movies) {
            Date date = log.getReleaseDateInfo();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String string = dateFormat.format(date);
            System.out.println("help " + log.getTitle() + " " + log.getFullPosterPathInfo());
            boolean fakeType = false;
            PlaylistMovieInfoObject testMovie = new PlaylistMovieInfoObject(fakeType, log.getId(),
                    log.getTitle(), log.getReleaseDateInfo(), log.getSummaryInfo(), log.getRatingInfo(),
                    log.getGenreIdInfo(), log.getFullPosterPathInfo(), log.getFullBackdropPathInfo(),
                    log.isAdultContent(), string);
            convertMovies.add(testMovie);
        }
        return convertMovies;
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
