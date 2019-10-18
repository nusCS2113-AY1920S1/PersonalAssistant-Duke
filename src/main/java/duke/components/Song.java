package duke.components;

import duke.components.Bar;
import duke.components.Note;
import duke.components.Song;
import duke.components.Chord;

import java.util.ArrayList;
import java.util.Iterator;

public class Song {

    private String name;
    private String key; // default key: C Major (implemented in NewCommand)
    private int tempo; // default tempo: 120 (implemented in NewCommand)

    private ArrayList<Bar> bars;
    private ArrayList<Group> groups;
    private ArrayList<String> songChart;

    /**
     * Constructor for Song object, taking in a name, key and tempo.
     *
     * @param name the name of the Song
     * @param key the key the Song is to be composed in
     * @param tempo the tempo at which the Song is to be played
     */
    public Song(String name, String key, int tempo) {
        this.name = name;
        this.key = key;
        this.tempo = tempo;
        this.bars = new ArrayList<>();
        this.groups = new ArrayList<>();
        this.songChart = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public int getTempo() {
        return tempo;
    }

    public ArrayList<Bar> getBars() {
        return bars;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    /**
     * Creates a grouping of Bar objects to be easily copied and inserted for repetitions
     * in music.
     *
     * @param name the name of the Group (e.g. Verse, Chorus, Pre-Chorus)
     * @param startingId ID of the Bar to start copying from
     * @param endingId ID of the Bar to end the copying
     */
    public void createGroup(String name, int startingId, int endingId) {
        ArrayList<Bar> groupedBars = new ArrayList<>();
        for (int i = startingId; i <= endingId; i++) {
            groupedBars.add(bars.get(i - 1));
        }
        groups.add(new Group(name, groupedBars));
    }

    public void addBar(Bar bar) {
        bars.add(bar);
        updateSongChart(bar);
    }

    public int getNumBars() {
        return bars.size();
    }

    /**
     * Updates the Song with the new list of Bar objects.
     * @param newBars the list of new Bar objects for the Song
     */
    public void updateBars(ArrayList<Bar> newBars) {
        this.bars = newBars;
        for (Bar bar: newBars) {
            updateSongChart(bar);
        }
    }

    /**
     * Updates the SongChart, the string representation of the Song.
     * @param bar the new Bar to be added to the Song
     */
    private void updateSongChart(Bar bar) {
        songChart.addAll(bar.getBarChart());
    }

    /**
     * Returns a String representation of the Song for the user to view.
     * @return a String representation of the Song to be viewed by the user
     */
    public String showSongChart() {

        StringBuilder formattedChart1 = new StringBuilder();
        for (String chordString: songChart) {
            formattedChart1.append(chordString).append(" ");
        }
        System.out.println(formattedChart1.toString());
        //System.out.println(this.getBars().get(0).getChords().get(0).getNotes().get(0).getPitch());
        //return formattedChart1.toString();

        ArrayList<Bar> barList  = this.getBars();
        Iterator<Bar> iterator1 = barList.iterator();
        StringBuilder formattedChart = new StringBuilder();
        int i = 0;
        //System.out.println(bars.get(0).getChords().get(0).getNotes().get(0).getPitch());
        while (iterator1.hasNext()) {
            Bar barToAnalyse = iterator1.next();
            ArrayList<Chord> chordToAnalyse  =  barToAnalyse.getChords();
            Iterator<Chord> iterator = chordToAnalyse.iterator();
            System.out.println(chordToAnalyse.size());
            int j = 0;
            while (iterator.hasNext() && j < chordToAnalyse.size() / 2) {
                formattedChart.append("{");
                Chord tempChordToAnalyse = iterator.next();
                ArrayList<Note> notesAnalyse  =  tempChordToAnalyse.getNotes();
                System.out.print(notesAnalyse.size());
                Iterator<Note> iterator2 = notesAnalyse.iterator();
                while (iterator2.hasNext()) {
                    Note tempNote = iterator2.next();
                    formattedChart.append(tempNote.getPitch());
                    formattedChart.append(" ");
                }
                formattedChart.append("}");
                j += 1;
            }

            formattedChart.append("|");
            i += 1;
        }
        return formattedChart.toString();


    }

    /**
     * Returns a String representation of the Song to be used for persistent storage.
     *
     * @return a storage-friendly String representation
     */
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(name).append(" ");
        for (Bar bar: bars) {
            result.append(bar.toString()).append(" ");
        }
        return result.toString();
    }

    /**
     * Returns a String representation of the Song to be used for persistent storage.
     *
     * @return a storage-friendly String representation
     */
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(name).append(" ");
        for (Bar bar: bars) {
            result.append(bar.toString()).append(" ");
        }
        return result.toString();
    }
}
