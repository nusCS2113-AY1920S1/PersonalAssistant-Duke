package controlpanel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import money.Account;
import money.Expenditure;
import money.Goal;
import money.Income;
import money.Installment;
import money.Item;

public class MoneyStorage {

    private String fileName;
    private SimpleDateFormat simpleDateFormat;

    public MoneyStorage(String filePath) {
        fileName = filePath;
        simpleDateFormat  = new SimpleDateFormat("d/M/yyyy");
    }

    public void writeToFile(Account account) {
        try{
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("");
            bufferedWriter.write("BS | " + account.getBaseSavings() + "\n");

            for (Income i : account.getIncomeListTotal()) {
                bufferedWriter.write("I | " + i.getPrice() + " | " + i.getDescription() +
                        " | " + i.getPayday() + "\n");
            }

            for (Expenditure exp : account.getExpListTotal()) {
                bufferedWriter.write("EXP | " + exp.getPrice() + " | " + exp.getDescription() + " | " +
                        exp.getCategory() + " | " + exp.getBoughtTime() + "\n");
            }

            for (Goal g : account.getShortTermGoals()) {
                bufferedWriter.write("G | " + g.getPrice() + " | " + g.getDescription() + " | " +
                        g.getCategory() + " | " + g.getGoalBy() + " | " + g.getPriority() + "\n");
            }

            for (Installment ins : account.getInstallments()) {
                bufferedWriter.write("INS | " + ins.getPrice() + " | " + ins.getDescription() + " | " +
                        ins.getCategory() + " | " + ins.getBoughtTime() + "\n");
            }

            bufferedWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
