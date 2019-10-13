package autocomplete;

import java.util.Arrays;

public final class AutoComplete {
    private final Term[] terms;

    public AutoComplete(Term[] terms) {
        this.terms = new Term[terms.length];
        for (int i = 0; i < terms.length; i++) {
            this.terms[i] = terms[i];
        }
        Arrays.sort(this.terms);
    }

    public Term[] allMatches(String prefix) {
        int firstIndex = BinarySearchDeluxe.firstIndexOf(terms, new Term(prefix), Term.byPrefixOrder(prefix.length()));
        if (firstIndex == -1) {
            return new Term[0];
        }
        int lastIndex  = BinarySearchDeluxe.lastIndexOf (terms, new Term(prefix), Term.byPrefixOrder(prefix.length()));
        Term[] matchTerms = new Term[1 + lastIndex - firstIndex];

        for (int i = 0; i < matchTerms.length; i++) {
            matchTerms[i] = terms[firstIndex++];
        }

        Arrays.sort(matchTerms, Term.byPrefixOrder(3)); //probably have to make r dynamic according to the amount of letters the user enter

        return matchTerms;
    }

    public int numberOfMatches(String prefix) {
        if (prefix == null) throw new NullPointerException("Prefix can't be null");
        return BinarySearchDeluxe.lastIndexOf (terms, new Term(prefix), Term.byPrefixOrder(prefix.length()))
                - BinarySearchDeluxe.firstIndexOf(terms, new Term(prefix), Term.byPrefixOrder(prefix.length())) + 1;
    }
}