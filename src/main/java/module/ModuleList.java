package module;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ModuleList {
    private Set<Module> modList = new HashSet<>();

    /**
     * Default Module List constructor
     */
    public ModuleList()
    {

    }
    /**
     * Creates the Module List using the stored data input
     *
     * @param input List of Strings which should be in the csv format
     */
    public ModuleList(List<String> input)
    {

    }

    /**
     * Creates a new Module List using an existng Set of Modules
     *
     * @param list is a Set of Modules
     */
    private ModuleList(Set<Module> list)
    {
        this.modList = new HashSet<>(list);
    }

    /**
     * Function to add a Module to the Module List
     * @param wry is a Module
     */
    public void add (Module wry)
    {
        modList.add(wry);
    }

    /**
     * Fetches the set of modules in the ModuleList
     *
     * @return Set<Module> is the current set of Modules in the ModuleList
     */
    private Set<Module> getModules()
    {
        return this.modList;
    }


    /**
     * Compares this ModuleList with the other ModuleList, returning a pair consisting of a ModuleList
     * and another Pair of ModuleList
     * the first ModuleList is the similar ModuleLists
     * the pair contains the difference for the calling object, then the compared object
     *
     * @param other is the other ModuleList to be compared to
     * @return complicated pair structure as described above
     */
    public Pair<ModuleList, Pair<ModuleList, ModuleList>> compare(ModuleList other)
    {
        Set<Module> intersection = new HashSet<>(other.getModules());
        intersection.retainAll(this.getModules());
        ModuleList similar = new ModuleList(intersection);
        ModuleList differenceOne = new ModuleList(this.setDifference(intersection));
        ModuleList differenceTwo = new ModuleList(other.setDifference(intersection));
        return new Pair<>(similar, new Pair<>(differenceOne, differenceTwo));
    }

    /**
     * Returns the a Set of Modules from the Module List which are not in the set of modules to be compared;
     *
     * @param subset is the the module to be compared, it should be a strict subset of the calling object
     *               However no checks will be carried out to ensure this is the case
     * @return the set of modules which belong to the calling object but are not in the passed in set
     */
    private Set<Module> setDifference(Set<Module> subset)
    {
        Set<Module> difference = new HashSet<>();
        for(Module temp: this.getModules())
        {
            if(!subset.contains(temp))
                difference.add(temp);
        }
        return difference;
    }
}
