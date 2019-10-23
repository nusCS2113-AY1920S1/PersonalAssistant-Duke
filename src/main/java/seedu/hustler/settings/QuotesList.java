package seedu.hustler.settings;

import java.util.ArrayList;

public class QuotesList {

    private ArrayList<Quotes> quoteList;

    /**
     * Random quotes or fixed quote;
     */
    private String displayState;



    public QuotesList() {

        this.quoteList = new ArrayList<>();

    }



    private ArrayList<Quotes> populateQuoteList() {

        quoteList.add(new Quotes("Just do it."));
        quoteList.add(new Quotes("The way to get started is to quit talking and begin doing."));
        quoteList.add(new Quotes("Focus on being productive instead of busy."));
        quoteList.add(new Quotes("Deliver more than expected."));
        quoteList.add(new Quotes("The scariest moment is always just before you start."));;

        return quoteList;
    }



}
