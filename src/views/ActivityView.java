package views;

import abstracts.View;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.Bill;
import models.Log;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class ActivityView extends View {

    private Parent view;
    private ScrollPane logsPane;
    private VBox logsContainer, statistcsContainer, activityContainer;
    private PieChart chart;
    private Text noOfTotalBills, totalCostText, filterResult;
    private TableView<Bill> table;
    private TableColumn noOfProducts, amount, createdAt, employee, username;
    private ComboBox timePeriodPicker;
    private DatePicker startDate, endDate;

    public ActivityView() {
        logsPane = new ScrollPane();
        logsPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        logsPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        view = createView();
    }

    public VBox createView() {
        logsContainer = new VBox();
        statistcsContainer = new VBox();
        activityContainer = new VBox();
        chart = new PieChart();
        chart.setLabelsVisible(true);
        chart.setLegendSide(Side.LEFT);

        noOfTotalBills = new Text();
        noOfTotalBills.getStyleClass().add("row");
        totalCostText = new Text();
        totalCostText.getStyleClass().add("row");
        HBox totalBills = new HBox();
        totalBills.getChildren().addAll(new Label("Total bills: "), noOfTotalBills);
        totalBills.getStyleClass().add("row");
        HBox totalCost = new HBox();
        totalCost.getChildren().addAll(new Label("Total amount of money made: "), totalCostText);
        totalCost.getStyleClass().add("row");
        statistcsContainer.getChildren().addAll(chart, totalBills, totalCost);

        table = new TableView<Bill>();
        table.setMinWidth(500);
        noOfProducts = new TableColumn("No. of products");
        amount = new TableColumn("Amount ($)");
        createdAt = new TableColumn("Created at");
        employee = new TableColumn("Employee");
        username = new TableColumn("Username");
        table.getColumns().addAll(noOfProducts, amount, createdAt, employee, username);
        timePeriodPicker = new ComboBox();
        timePeriodPicker.setPromptText("Choose a time period");
        startDate = new DatePicker();
        startDate.setValue(LocalDate.now());
        startDate.setDisable(true);
        endDate = new DatePicker();
        endDate.setValue(LocalDate.now());
        endDate.setDisable(true);
        VBox timePeriods = new VBox();
        filterResult = new Text();
        filterResult.getStyleClass().add("row");
        VBox.setMargin(filterResult, new Insets(20,0,0,0));
        timePeriods.getChildren().addAll(timePeriodPicker, startDate, endDate, filterResult);
        timePeriods.setAlignment(Pos.CENTER);
        HBox hBox = new HBox();
        hBox.getChildren().addAll(table, timePeriods);
        HBox.setHgrow(timePeriods, Priority.ALWAYS);
        hBox.getStyleClass().add("container");
        activityContainer.getChildren().addAll(hBox);

        TabPane tabs = new TabPane();
        Tab statistics = new Tab("Statistics", statistcsContainer);
        statistics.setClosable(false);
        Tab activity = new Tab("Activity", activityContainer);
        activity.setClosable(false);
        Tab logs = new Tab("Logs", logsPane);
        logsPane.getStyleClass().add("container");
        logs.setClosable(false);
        tabs.getTabs().addAll(statistics, activity, logs);

        VBox vBox = new VBox();
        VBox.setVgrow(tabs, Priority.ALWAYS);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(tabs);
        vBox.getStyleClass().add("container");
        return vBox;
    }

    public TableView<Bill> getTable() {
        return table;
    }

    public TableColumn getNoOfProducts() {
        return noOfProducts;
    }

    public TableColumn getAmount() {
        return amount;
    }

    public TableColumn getCreatedAt() {
        return createdAt;
    }

    public TableColumn getEmployee() {
        return employee;
    }

    public TableColumn getUsername() {
        return username;
    }

    public ComboBox getTimePeriodPicker() {
        return timePeriodPicker;
    }

    public Text getFilterResult() {
        return filterResult;
    }

    public DatePicker getStartDate() {
        return startDate;
    }

    public DatePicker getEndDate() {
        return endDate;
    }

    public VBox getStatistcsContainer() {
        return statistcsContainer;
    }

    public Text getNoOfTotalBills() {
        return noOfTotalBills;
    }

    public Text getTotalCostText() {
        return totalCostText;
    }

    public VBox getLogsContainer() {
        return logsContainer;
    }

    public HBox addLogRow(Log log) {
        HBox hBox = new HBox();
        hBox.getStyleClass().add("row");
        hBox.getChildren().addAll(new Label(log.getValue()), new Label(log.getAccount().getUsername(), new Label(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(log.getCreatedAt()))));
        hBox.setSpacing(10);
        return hBox;
    }

    public ScrollPane getLogsPane() {
        return logsPane;
    }

    public PieChart getChart() {
        return chart;
    }

    public Parent getView() {
        return view;
    }
}
