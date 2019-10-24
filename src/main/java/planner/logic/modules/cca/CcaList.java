//@@author LongLeCE

package planner.logic.modules.cca;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CcaList extends ArrayList<Cca> {

    public CcaList() {
        super();
    }

    public CcaList(int initialCapacity) {
        super(initialCapacity);
    }

    public CcaList(Collection<? extends Cca> c) {
        super(c);
    }

    /**
     * Returns list of ccas which have the search
     * keyword included in their name.
     * @param keyword Parsed keyword of the cca to be searched.
     * @return Returns the cca list where each cca contains the search keyword.
     */
    public List<Cca> find(String keyword) {
        List<Cca> results = new ArrayList<>();
        String[] keywordSplit = keyword.trim().split(" +");
        Set<String> ccaNameSplit;
        boolean match;
        for (Cca cca : this) {
            match = true;
            ccaNameSplit = new HashSet<>(Arrays.asList(cca.getTask().trim().split(" +")));
            for (String keywordPart: keywordSplit) {
                if (!ccaNameSplit.contains(keywordPart)) {
                    match = false;
                    break;
                }
            }
            if (match) {
                results.add(cca);
            }
        }
        return results;
    }

    /**
     * Set cca list to some other list.
     * @param ccas new cca list
     */
    public void set(Collection<? extends Cca> ccas) {
        this.clear();
        this.addAll(ccas);
    }

    /**
     * Check whether given cca clashes with current ccas.
     * @param cca given cca
     * @return true if clashes else false
     */
    public boolean clashes(Cca cca) {
        for (Cca currentCca: this) {
            if (cca.isClashing(currentCca)) {
                return true;
            }
        }
        return false;
    }
}
