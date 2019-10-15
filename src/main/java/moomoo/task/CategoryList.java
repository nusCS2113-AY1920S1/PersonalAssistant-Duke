package moomoo.task;

import java.util.ArrayList;

public class CategoryList {
    private ArrayList<Category> catList;

    public CategoryList() {
        catList = new ArrayList<Category>();
    }

    public CategoryList(ArrayList<Category> inList) {
        this.catList = inList;
    }
    
    //Methods
    public int size() {
        return catList.size();
    }
    
    public Category get(int i) {
        return catList.get(i);
    }
    
    public double getGrandMonthTotal() {
        double total = 0;
        for (Category category : catList)
        {
            total += category.getCategoryMonthTotal();
        }
        return total;
    }
    
    public double getLargestExpenditure() {
        double expenditure = 0;
        for (Category category : catList)
        {
            if (category.getCategoryMonthTotal() > expenditure)
            {
                expenditure = category.getCategoryMonthTotal();
            }
        }
        return expenditure;
    }
}

