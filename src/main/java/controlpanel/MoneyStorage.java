package controlpanel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
    private DateTimeFormatter dateTimeFormatter;

    public MoneyStorage(String filePath) {
        fileName = filePath;
        dateTimeFormatter  = DateTimeFormatter.ofPattern("d/M/yyyy");
    }

    public Account load() {
        Account account = new Account();
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] info = line.split(" @ ");
                if (!(info[0].equals("BS") || info[0].equals("I") || info[0].equals("EXP") ||
                        info[0].equals("G") || info[0].equals("INS"))) {
                    throw new DukeException("OOPS!! Your file has been corrupted/ input file is invalid!");
                }
                switch(info[0]) {
                    case "I":
                        Income i = new Income(Float.parseFloat(info[1]), info[2],
                                LocalDate.parse(info[3], dateTimeFormatter));
                        account.getIncomeListTotal().add(i);
                        break;
                    case "EXP":
                        Expenditure exp = new Expenditure(Float.parseFloat(info[1]), info[2], info[3],
                                LocalDate.parse(info[4], dateTimeFormatter));
                        account.getExpListTotal().add(exp);
                        break;
                    case "G":
                        Goal g = new Goal(Float.parseFloat(info[1]), info[2], info[3],
                                LocalDate.parse(info[4], dateTimeFormatter), info[5]);
                        account.getShortTermGoals().add(g);
                        break;
                    default:
                        break;
                }
            }
            bufferedReader.close();
        } catch (IOException | DateTimeParseException | DukeException e) {
            e.printStackTrace();
        }
        return account;
    }

    public void writeToFile(Account account) {
        try{
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("");
            bufferedWriter.write("BS @ " + account.getBaseSavings() + "\n");

            for (Income i : account.getIncomeListTotal()) {
                bufferedWriter.write("I @ " + i.getPrice() + " @ " + i.getDescription() +
                        " @ " + i.getPaidTime() + "\n");
            }

            for (Expenditure exp : account.getExpListTotal()) {
                bufferedWriter.write("EXP @ " + exp.getPrice() + " @ " + exp.getDescription() + " @ " +
                        exp.getCategory() + " @ " + exp.getBoughtTime() + "\n");
            }

            for (Goal g : account.getShortTermGoals()) {
                bufferedWriter.write("G @ " + g.getPrice() + " @ " + g.getDescription() + " @ " +
                        g.getCategory() + " @ " + g.getGoalBy() + " @ " + g.getPriority() + "\n");
            }

            for (Installment ins : account.getInstallments()) {
                bufferedWriter.write("INS @ " + ins.getPrice() + " @ " + ins.getDescription() + " @ " +
                        ins.getCategory() + " @ " + ins.getBoughtTime() + "\n");
            }

            bufferedWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
