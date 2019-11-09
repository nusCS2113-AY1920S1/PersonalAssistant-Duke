package duke.ui;

import duke.commons.LogsCenter;
import duke.logic.Logic;
import duke.model.BudgetView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class BudgetBar extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(BudgetBar.class);

    private static final String FXML_FILE_NAME = "BudgetBar.fxml";
    public Logic logic;
    private Map <Integer,ProgressBar> budgetBars = new HashMap<>();

    @FXML
    GridPane gridPane;

    @FXML
    VBox vBox1;

    @FXML
    VBox vBox2;

    @FXML
    VBox vBox3;

    @FXML
    VBox vBox4;

    @FXML
    VBox vBox5;

    @FXML
    VBox vBox6;

    public BudgetBar(Logic logic) {
        super(FXML_FILE_NAME,null);
        this.logic = logic;
        gridPane.setStyle("-fx-background-color: mintcream;");
        gridPane.setGridLinesVisible(true);
        gridPane.setSnapToPixel(true);

        for(int viewPane = 1; viewPane <= 6; viewPane++) {
            ProgressBar bar = new ProgressBar();
            Text category = new Text();
            Text remaining = new Text();

            bar.setPrefWidth(250);
            bar.setPrefHeight(30);
            bar.setProgress(percentage(viewPane,logic));
            budgetBars.put(viewPane,bar);

            if(percentage(viewPane,logic) > 0.9) {
                bar.setStyle("-fx-accent: red;");
            } else if (percentage(viewPane,logic) > 0.65) {
                bar.setStyle("-fx-accent: orange;");
            } else if (percentage(viewPane,logic) > 0.40) {
                bar.setStyle("-fx-accent: yellow");
            } else {
                bar.setStyle("-fx-accent: green");
            }

            if(percentage(viewPane,logic) < 1) {
                if(remainder(viewPane,logic).compareTo(BigDecimal.ZERO) == 0) {
                    remaining.setText("     No budget set.");
                } else {
                    remaining.setText("     Remaining budget: $" + remainder(viewPane, logic));
                }
            } else if(percentage(viewPane,logic) == 1) {
                remaining.setText("     Budget of " + logic.getBudgetTag(logic.getBudgetViewCategory().get(viewPane)) + " reached!");
            } else {
                remaining.setText("     Exceeded budget by $" + remainder(viewPane,logic).negate() + "!");
            }

            remaining.setStyle("-fx-font-size: 16px;");
            category.setStyle("-fx-font-size: 25px;");

            if(!logic.getBudgetViewCategory().containsKey(viewPane)) {
                bar.setVisible(false);
                remaining.setVisible(false);
                category.setText("Type \"viewBudget " + viewPane + " /tag #category\" to add a budget view in this pane.");
                category.setWrappingWidth(140);
                category.setStyle("-fx-font-size: 12px;");
                category.setTextAlignment(TextAlignment.CENTER);
            } else {
                String tag = logic.getBudgetViewCategory().get(viewPane);
                category.setText(tag.toUpperCase());
            }

            if(viewPane == 1) {
                vBox1.getChildren().addAll(category, bar, remaining);
                vBox1.setSpacing(10);
            } else if(viewPane == 2) {
                vBox2.getChildren().addAll(category, bar, remaining);
                vBox2.setSpacing(10);
            } else if(viewPane == 3) {
                vBox3.getChildren().addAll(category, bar, remaining);
                vBox3.setSpacing(10);
            } else if(viewPane == 4) {
                vBox4.getChildren().addAll(category, bar, remaining);
                vBox4.setSpacing(10);
            } else if(viewPane == 5) {
                vBox5.getChildren().addAll(category, bar, remaining);
                vBox5.setSpacing(10);
            } else {
                vBox6.getChildren().addAll(category, bar, remaining);
                vBox6.setSpacing(10);
            }
        }
    }

    public double percentage(int viewPane, Logic logic) {
        String category = logic.getBudgetViewCategory().get(viewPane);
        double percent = logic.getTagAmount(category).doubleValue() / logic.getBudgetTag(category).doubleValue();

        //In the case where percent is NaN (Not a Number)
        if(Double.isNaN(percent)) {
            percent = 0.0;
        }

        return percent;
    }

    public BigDecimal remainder(int viewPane, Logic logic) {
        String category = logic.getBudgetViewCategory().get(viewPane);
        BigDecimal remaining = logic.getBudgetTag(category).subtract(logic.getTagAmount(category));

        return remaining;
    }

}
