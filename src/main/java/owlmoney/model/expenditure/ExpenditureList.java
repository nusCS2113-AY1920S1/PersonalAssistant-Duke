package owlmoney.model.expenditure;

import java.util.ArrayList;

public class ExpenditureList {

    private ArrayList<Expenditure> expLists;

    public ExpenditureList() {
        expLists = new ArrayList<Expenditure>();
    }

    public void listExpenditure() {
        if(expLists.size() <= 0) {
            System.out.println("no expenditure found!");
        } else {
            for (int i = 0; i < expLists.size(); i++) {
                System.out.println(expLists.get(i).toString());
            }
        }
    }

    public void addToList(Expenditure exp) {
        expLists.add(exp);
    }
}
