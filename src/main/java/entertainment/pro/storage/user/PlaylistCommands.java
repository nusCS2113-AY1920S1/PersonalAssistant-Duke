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
    public void add(TreeMap<String, ArrayList<String>> flagMap, ArrayList<MovieInfoObject> mMovies) throws IOException {
        ArrayList<Long> userMovies = new ArrayList<>(20);
        ArrayList<MovieInfoObject> playlistMovies = new ArrayList<>(20);
        for (String log : flagMap.get("-m")) {
            int index = Integer.parseInt(log.trim());
            System.out.println(index);
            userMovies.add((mMovies.get(--index)).getId());
            playlistMovies.add(mMovies.get(index));
            System.out.println("hello looky here " + mMovies.get(index).getFullPosterPathInfo());
        }
        Playlist playlist = editPlaylistJson.load();
        ArrayList<PlaylistMovieInfoObject> newPlaylistMovies = convert(playlistMovies);
        playlist.add(newPlaylistMovies);
        editPlaylistJson.editPlaylist(playlist);
    }

    /**
     * to remove movies from playlist.
     */
    public void remove(TreeMap<String, ArrayList<String>> flagMap) throws IOException {
//        ArrayList<Long> userMovies = new ArrayList<>(20);
        Playlist playlist = editPlaylistJson.load();
        ArrayList<PlaylistMovieInfoObject> toDelete = new ArrayList<>();
        for (String log : flagMap.get("-m")) {
            int index = Integer.parseInt(log.trim());
            System.out.println(index);
//            userMovies.add((mMovies.get(--index)).getID());
            toDelete.add(playlist.getMovies().get(--index));
        }
//        ArrayList<PlaylistMovieInfoObject> newPlaylistMovies = convert(toDelete);
//        if (playlist.getMovies().get(1).equals(newPlaylistMovies.get(0))) {
//            System.out.println("yes");
//        } else {
//            System.out.println("no");
//            System.out.println(newPlaylistMovies.get(0).getTitle());
//        }
        playlist.remove(toDelete);
//        playlist.remove(removeMovies);
        editPlaylistJson.editPlaylist(playlist);
        System.out.println("hehe");
    }

    /**
     * to clear all movies in playlist.
     */
    public void clear() throws IOException {
        Playlist playlist = editPlaylistJson.load();
        playlist.clear();
        editPlaylistJson.editPlaylist(playlist);
    }

    private ArrayList<PlaylistMovieInfoObject> convert(ArrayList<MovieInfoObject> movies) {
        ArrayList<PlaylistMovieInfoObject> convertMovies = new ArrayList<>();
        for (MovieInfoObject log : movies) {
            Date date = log.getReleaseDateInfo();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String string = dateFormat.format(date);
            System.out.println("help " + log.getTitle() + " " + log.getFullPosterPathInfo());
            //int fakeType = 12345;
            boolean fakeType = false;

            PlaylistMovieInfoObject testMovie = new PlaylistMovieInfoObject(fakeType, log.getId(), log.getTitle(), log.getReleaseDateInfo(), log.getSummaryInfo(), log.getRatingInfo(), log.getGenreIDInfo(), log.getFullPosterPathInfo(), log.getFullBackdropPathInfo(), log.isIsadultContent(), string);
            convertMovies.add(testMovie);
        }
        return convertMovies;
    }

    /**
     * set name/description to particular Playlist object.
     */
    public void setToPlaylist(TreeMap<String, ArrayList<String>> flagMap) throws IOException {
        if (flagMap.containsKey("-n") && !flagMap.containsKey("-d")) {
            setPlaylistName(appendFlagMap(flagMap.get("-n")));
        }
        if (flagMap.containsKey("-d") && !flagMap.containsKey("-n")) {
            setPlaylistDescription(appendFlagMap(flagMap.get("-d")));
        }
        if (flagMap.containsKey("-d") && flagMap.containsKey("-n")) {
            setAll(appendFlagMap(flagMap.get("-n")), appendFlagMap(flagMap.get("-d")));
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

    /**
     * change name of particular Playlist object.
     */
    public void setPlaylistName(String newName) throws IOException {
        Playlist playlist = editPlaylistJson.load();
        playlist.setPlaylistName(newName);
        editPlaylistJson.renamePlaylist(playlist, newName);
    }

    /**
     * change description of particular Playlist object.
     */
    public void setPlaylistDescription(String description) throws IOException {
        Playlist playlist = editPlaylistJson.load();
        playlist.setDescription(description);
        editPlaylistJson.editPlaylist(playlist);
    }

    /**
     * to allow setting of both name and description at the same time.
     */
    public void setAll(String newName, String description) throws IOException {
        Playlist playlist = editPlaylistJson.load();
        playlist.setPlaylistName(newName);
        playlist.setDescription(description);
        editPlaylistJson.renamePlaylist(playlist, newName);
        editPlaylistJson.editPlaylist(playlist);
    }
}
