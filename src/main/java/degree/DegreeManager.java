package degree;

import exception.DukeException;
import list.DegreeList;
import storage.Storage;

import java.io.*;
import java.util.*;

public class DegreeManager {
    //private Map<String, List<String>> degrees = new HashMap<>();
    private Map<String, Degree> degreeInfo = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

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
     * Method to facilitate the deep cloning of this taskList.
     * Returns a copy of this taskList, but with different references.
     * This is to avoid shallow copying, which will also modify the saved state of the taskList.
     *
     * @return A copy of this taskList with different references to objects.
     */
    public DegreeManager deepClone() {
        try {
            //Serialization of object
            ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);
            objectOutputStream.writeObject(this);

            //De-serialization of object
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteInputStream);
            return (DegreeManager) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
            return null;
        }
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
        if(!degreeInfo.containsKey(degree))
            throw new DukeException(degree + " was not found in our records!");
        else
            degreeInfo.get(degree).print();
    }

    public void clear() {
        degreeInfo.clear();
    }

    public long size() {
        return degreeInfo.size();
    }

    public void compare(String input) throws DukeException {
        String[] split = input.split("\\s+");
        if(split.length > 2)
        {
            throw new DukeException("Too many arguments!");
        }
        if(split.length <= 1){
            throw new DukeException("Too few arguments!");
        }
        StringBuilder errorList = new StringBuilder();
        if(!degreeInfo.containsKey(split[0]))
        {
            errorList.append(split[0]);
            errorList.append(" ");
        }
        if(!degreeInfo.containsKey(split[1]))
        {
            errorList.append(split[1]);
        }
        if(errorList.length() > 0)
        {
            throw new DukeException("Unable to find matching degrees for: "+ errorList.toString());
        }
        degreeInfo.get(split[0]).compare(degreeInfo.get(split[1]));
    }
}
