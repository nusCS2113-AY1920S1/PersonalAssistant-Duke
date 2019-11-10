package degree;

import exception.DukeException;
import list.DegreeList;
import main.Duke;
import module.ModuleList;
import storage.Storage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DegreeManager {
    //private Map<String, List<String>> degrees = new HashMap<>();
    private Map<String, Degree> degreeInfo = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private Map<String, List<String>> disjointSetFake = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private ModuleList masterList = new ModuleList();
    private String degreeOneKey = "";
    private String degreeTwoKey = "";
    private boolean foundFlag = true;

    /**
     * Constructs a degree manager class which handles queries for degrees and returns the appropiate information
     * @param storage is the storage class which holds all stored information
     * @throws DukeException when a degree has failed to load or could not be found
     */
    public DegreeManager(Storage storage) throws DukeException {
        List<String> listdegrees = storage.fetchListOutput("listdegrees");
        if(listdegrees == null)
            throw new DukeException("listdegrees.csv file not found");
        for (String listdegree : listdegrees) {
            String[] split = listdegree.split(",");
            addDegree(split, storage.fetchListOutput(split[0]));
            disjointSetFake.put(split[0], degreeInfo.get(split[0]).getAlias());
            masterList.add(degreeInfo.get(split[0]).getMaster());
        }
    }

    /**
     * Empty Instance of Degree Manager
     *
     */
    public DegreeManager() {

    }

    /**
     * Adds a new Degree to the list of degree information
     *
     * @param split is the comma separated file containing information about the degree and its options
     * @throws DukeException is thrown if there is no degree information in the first column
     */
    private void addDegree(String[] split, List<String> degreecsv) throws DukeException {
        try {
            if (split.length == 1)
                degreeInfo.put(split[0], new Degree(degreecsv));
            else {
                if (split[0].isBlank())
                    throw new DukeException("Unable to find main degree");
                else
                    degreeInfo.put(split[0], new Degree(degreecsv));
            }
        } catch (DukeException e) {
            throw new DukeException("For Degree " + split[0] + ": " + e.getLocalizedMessage());
        }
    }


    public void print(String item) throws DukeException {
        foundFlag = true;
        for(Map.Entry<String, List<String>> aliases: disjointSetFake.entrySet())
        {
            String degree = aliases.getKey();
            List<String> commonNames = aliases.getValue();
            for(String x : commonNames){
                if(x.equalsIgnoreCase(item)){
                    degreeInfo.get(degree).print();
                    return;
                }
            }
        }
        foundFlag = false;
        throw new DukeException(item + " was not found in our records!");
    }

    public boolean isKey(String alias)
    {
        for(Map.Entry<String, List<String>> aliases: disjointSetFake.entrySet())
        {
            List<String> commonNames = aliases.getValue();
            for(String x : commonNames){
                if(x.equalsIgnoreCase(alias)){
                    return true;
                }
            }
        }
        return false;
    }

    public String findAnyDegree(String degreeName)
    {
        for(Map.Entry<String, List<String>> aliases: disjointSetFake.entrySet())
        {
            String degree = aliases.getKey();
            List<String> commonNames = aliases.getValue();
            for(String x : commonNames){
                if(x.equalsIgnoreCase(degreeName)){
                    return degree;
                }
            }
        }
        return "";
    }

    /**
     * Returns the size of this degreeManager.
     *
     * @return The number of degrees in this degreeManager.
     */
    public int size() {
        return degreeInfo.size();
    }

    public void compare(String input) throws DukeException {
        foundFlag = true;
        String[] split = input.split("\\s+");
        if(split.length > 7) {
            foundFlag = false;
            throw new DukeException("Too many arguments!");
        }
        if(split.length <= 1) {
            foundFlag = false;
            throw new DukeException("Too few arguments!");
        }
        twoKeyGenerator(split);
        if(degreeOneKey.isBlank() || degreeTwoKey.isBlank())
            throw new DukeException("Unable to find any matching degrees for: " + input);
        if(degreeOneKey.equalsIgnoreCase(degreeTwoKey))
            throw new DukeException("Invalid Comparison to Self.");

        degreeInfo.get(degreeOneKey).compare(degreeInfo.get(degreeTwoKey));
    }

    public void twoKeyGenerator(String[] split)
    {

        for(int i = 1; i < split.length; i++) {
            StringBuilder degreeOne = new StringBuilder();
            StringBuilder degreeTwo = new StringBuilder();
            for (int j = 0; j < i; j++) {
                degreeOne.append(split[j]).append(" ");
            }
            degreeOne.setLength(degreeOne.length() - 1);
            for (int j = i; j < split.length; j++) {
                degreeTwo.append(split[j]).append(" ");
            }
            degreeTwo.setLength(degreeTwo.length() - 1);
            if (isKey(degreeOne.toString()) && isKey(degreeTwo.toString())) {
                this.degreeOneKey = findAnyDegree(degreeOne.toString());
                this.degreeTwoKey = findAnyDegree(degreeTwo.toString());
                return;
            }
        }
        this.degreeOneKey = "";
        this.degreeTwoKey = "";
    }

    public String getDegreeOneKey()
    {
        return this.degreeOneKey;
    }

    public String getDegreeTwoKey()
    {
        return this.degreeTwoKey;
    }

    /**
     * Returns the map of keywords to aliases
     *
     * @return the map indicating the keyword and its aliases
     */
    public Map<String, List<String>> getKeywords()
    {
        return this.disjointSetFake;
    }

    /**
     * Returns the modules of a certain degree as a moduleList.
     *
     * @param degree The name of the degree corresponding to the modules required.
     * @return The modules of this degree as a moduleList.
     */
    public ModuleList getModuleList(String degree) {
        return degreeInfo.get(degree).getMaster();
    }

    /**
     * Method to return the full name of a degree course to check if a degree exists.
     *
     * @return String full name of the course/degree
     */
    public String getFullDegreeName(String degree) {
        return degreeInfo.get(degree).getProperName();
    }

    /**
     * Method to return a flag to check if a degree exists.
     *
     * @return boolean check for existence of a degree.
     */
    public boolean getFoundFlag() {
        return this.foundFlag;
    }
}
