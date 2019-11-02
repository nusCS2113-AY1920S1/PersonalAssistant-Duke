package degree;

import exception.DukeException;
import list.DegreeList;
import module.ModuleList;
import storage.Storage;

import java.io.*;
import java.util.*;

public class DegreeManager {
    //private Map<String, List<String>> degrees = new HashMap<>();
    private Map<String, Degree> degreeInfo = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
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

    public void print(String degree) throws DukeException {
        this.foundFlag = true;
        if(!degreeInfo.containsKey(degree)) {
            this.foundFlag = false;
            throw new DukeException(degree + " was not found in our records!");
        }
        else
            degreeInfo.get(degree).print();
    }

    /**
     * Clears out the current degreeManager.
     */
    public void clear() {
        degreeInfo.clear();
    }

    /**
     * Returns the size of this degreeManager.
     *
     * @return The number of degrees in this degreeManager.
     */
    public int size() {
        return degreeInfo.size();
    }

    /**
     * Returns the modules of a certain degree as a moduleList.
     *
     * @param degree The name of the degree corresponding to the modules required.
     * @return The modules of this degree as a moduleList.
     */
    public ModuleList getModuleList(String degree) {
        return degreeInfo.get(degree).getModuleList();
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
