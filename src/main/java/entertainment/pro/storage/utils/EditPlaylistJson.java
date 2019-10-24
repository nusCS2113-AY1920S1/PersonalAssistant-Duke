package entertainment.pro.storage.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entertainment.pro.model.Playlist;

import java.io.*;
import java.util.ArrayList;

/**
 * class that deals with editing the Playlist.json file
 */
public class EditPlaylistJson {
    private ObjectMapper mapper = new ObjectMapper();
    private File file;
    private InputStream inputStream;
    private TypeReference<ArrayList<Playlist>> typeReference = new TypeReference<ArrayList<Playlist>>() {};


    public EditPlaylistJson() throws FileNotFoundException {
        file = new File("EPdata/Playlists.json");
        this.inputStream = new FileInputStream(file);
    }

    public ArrayList<Playlist> load() throws IOException {
        return mapper.readValue(inputStream, typeReference);
    }

    /**
     * to create and add new playlist to json file
     */
    public void addPlaylist(Playlist playlist) throws IOException {
        File oldFile = file;
        File newFile = new File("EPdata/tempPlaylists.json");
        ArrayList<Playlist> playlists = load();
        playlists.add(playlist);
        mapper.writeValue(newFile, playlists);
        inputStream.close();
        oldFile.delete();
        newFile.renameTo(new File(file.getAbsolutePath()));
    }

    /**
     * to delete a particular playlist from json file
     */
    public void removePlaylist(String playlistName) throws IOException {
        File oldFile = file;
        File newFile = new File("EPdata/tempPlaylists.json");
        ArrayList<Playlist> playlists = load();
        ArrayList<Playlist> newPlaylists = new ArrayList<>(10);
        for (Playlist log : playlists){
            if (!log.getListName().equals(playlistName)){
                newPlaylists.add(log);
            }
        }
        mapper.writeValue(newFile, newPlaylists);
        inputStream.close();
        oldFile.delete();
        newFile.renameTo(new File(file.getAbsolutePath()));
    }

    /**
     * to edit any particular playlist in json file
     */
    public void editPlaylist(ArrayList<Playlist> playlists) throws IOException {
        File oldFile = file;
        File newFile = new File("EPdata/tempPlaylists.json");
        mapper.writeValue(newFile, playlists);
        inputStream.close();
        oldFile.delete();
        newFile.renameTo(new File(file.getAbsolutePath()));
    }
}
