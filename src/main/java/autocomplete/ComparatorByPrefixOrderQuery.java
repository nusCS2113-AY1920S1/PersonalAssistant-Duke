package autocomplete;

import java.util.Comparator;

public class ComparatorByPrefixOrderQuery implements Comparator<Term> {
    private int r;

    public ComparatorByPrefixOrderQuery(int r) {
        this.r = r;
    }

    @Override
    public int compare(Term a, Term b) {
        String prefixA;
        String prefixB;

        if (a.getQuery().length() < r) {
            prefixA = a.getQuery();
        } else {
            prefixA = a.getQuery().substring(0, r);
        }

        if (b.getQuery().length() < r) {
            prefixB = b.getQuery();
        } else {
            prefixB = b.getQuery().substring(0, r);
        }

        return prefixA.compareTo(prefixB);
    }
}
