package moomoo.stubs;

import moomoo.feature.category.Category;
import moomoo.feature.category.Expenditure;

import java.time.Month;
import java.util.ArrayList;
import java.time.LocalDate;

public class CategoryStub extends Category {
    private String name;
    private ArrayList<ExpenditureStub> category;

    public CategoryStub() {
        super("");
    }

    /**
     * Creates a category.
     * @param name name of category
     */
    public CategoryStub(String name) {
        super("");
        this.category = new ArrayList<>();
        this.name = name;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public double getTotal(int month, int year) {
        double totalCost = 0.00;
        for (int i = 0; i < category.size(); i++) {
            ExpenditureStub currExpenditure = category.get(i);
            if (currExpenditure.getDate().getMonthValue() == month
                    && currExpenditure.getDate().getYear() == year) {
                totalCost += currExpenditure.getCost();
            }
        }
        return totalCost;
    }

    @Override
    public void add(Expenditure newExpenditure) {
        this.category.add(new ExpenditureStub("Value 1",100, LocalDate.of(2017, Month.DECEMBER, 20)));
        this.category.add(new ExpenditureStub("Value 2", 100, LocalDate.of(2018, Month.DECEMBER, 20)));
        this.category.add(new ExpenditureStub("Value 3", 100, LocalDate.of(2019, Month.JANUARY, 20)));

        this.category.add(new ExpenditureStub("Value 4", 200, LocalDate.of(2019, Month.AUGUST, 20)));
        this.category.add(new ExpenditureStub("Value 5", 150, LocalDate.of(2019, Month.SEPTEMBER, 20)));
        this.category.add(new ExpenditureStub("Value 6", 50, LocalDate.of(2019, Month.OCTOBER, 25)));
        this.category.add(new ExpenditureStub("Value 7", 65, LocalDate.of(2019, Month.NOVEMBER, 21)));
    }
}
