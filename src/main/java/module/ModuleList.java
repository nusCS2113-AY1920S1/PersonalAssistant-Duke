package module;

import javafx.util.Pair;
import parser.Parser;

import java.util.*;

public class ModuleList {
    private Set<Module> modList = new TreeSet<>();
    private int sum = 0;

    /**
     * Default Module List constructor
     */
    public ModuleList()
    {

    }

    /**
     * Creates a new Module List using an existng Set of Modules
     *
     * @param list is a Set of Modules
     */
    private ModuleList(Set<Module> list)
    {
        this.modList = new TreeSet<>(list);
        for(Module x: modList)
        {
            this.sum += x.getMc();
        }
    }

    /**
     * Function to add a Module to the Module List
     * @param wry is a Module
     */
    public void add (Module wry)
    {
        if(!this.modList.contains(wry)){
            modList.add(wry);
            this.updateSum(wry.getMc());
        }
    }

    public void add(ModuleList other)
    {
        for(Module x: other.getModules())
        {
            this.add(x);
        }
    }

    /**
     * Fetches the set of modules in the ModuleList
     *
     * @return Set<Module> is the current set of Modules in the ModuleList
     */
    public Set<Module> getModules()
    {
        return this.modList;
    }


    /**
     * Compares this ModuleList with the other ModuleList, returns similar modules as a ModuleList
     *
     * the pair contains the difference for the calling object, then the compared object
     *
     * @param other is the other ModuleList to be compared to
     * @return ModuleList containing similar modules
     */
    public ModuleList getSimilar(ModuleList other) {
        Set<Module> intersection = new HashSet<>(other.getModules());
        intersection.retainAll(this.getModules());
        return new ModuleList(intersection);
    }

    /**
     * Returns the a Set of Modules from the Module List which are not in the set of modules to be compared;
     *
     * @param other is the the other ModuleList to be compared to
     * @return the set of modules which belong to this ModuleList but are not in the other ModuleList
     */
    public ModuleList getDifference(ModuleList other)
    {
        ModuleList subset = this.getSimilar(other);
        Set<Module> difference = new TreeSet<>();
        for(Module temp: this.getModules())
        {
            if(!subset.getModules().contains(temp))
                difference.add(temp);
        }
        return new ModuleList(difference);
    }

    /**
     * Returns the total number of MCs in the Module List
     *
     * @return sum of the modules in the ModuleList List<Module> data structure
     */
    public int getSum()
    {
        return this.sum;
    }

    /**
     * Increases the current mc count by the amount given
     *
     * @params mc is the amount for the sum to be increased by
     */
    private void updateSum(int mc)
    {
        this.sum += mc;
    }

    /**
     * Prints out all modules in the list
     * Follows maximum screen width
     *
     */
    public void print()
    {
        for(Module x: this.modList)
        {
            x.print();
        }
    }

    /**
     * Prints out all modules in the list
     * Follows maximum screen width
     *
     */
    public void printCentral(int setWidth)
    {
        for(Module x: this.modList)
        {
            int padLen = (Parser.windowWidth - setWidth)/2;
            char[] pad = new char[padLen];
            Arrays.fill(pad, ' ');
            String module = x.getPrint(setWidth);
            StringBuilder line = new StringBuilder();
            line.append(pad);
            line.append(module);
            while(line.length() < Parser.windowWidth)
            {
                line.append(" ");
            }
        }
    }
}
