package autocomplete;

import java.util.Comparator;

public final class Term implements Comparable<Term> {
    private final String query;

    public Term(String query) {
        this.query = query;
    }

    public String getQuery() { return query; }

    @Override
    public int compareTo(Term o) { return this.query.compareTo(o.query); }

    public static Comparator<Term> byPrefixOrder(int r) { return new ComparatorByPrefixOrderQuery(r); }
}