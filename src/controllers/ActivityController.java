package controllers;

import db.Database;
import interfaces.ControllerInterface;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;
import models.*;
import utility.Utilities;
import views.ActivityView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ActivityController implements ControllerInterface<ActivityView> {

    private Calendar cal1 = Calendar.getInstance(), cal2 = Calendar.getInstance();

    public ActivityController(ActivityView view) {
        setView(view);
    }

    @Override
    public void setView(ActivityView view) {
        view.getChart().setData(getCategoryData());

        view.getNoOfTotalBills().setText(String.valueOf(Database.Bills.getObjects().size()) + " bills");
        view.getTotalCostText().setText(getTotalCost() + " $");


        view.getNoOfProducts().setCellValueFactory(new PropertyValueFactory<>("productsSize"));
        view.getAmount().setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        view.getCreatedAt().setCellValueFactory(new PropertyValueFactory<>("createdAtString"));
        view.getEmployee().setCellValueFactory(new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures cellDataFeatures) {
                Bill bill = (Bill)cellDataFeatures.getValue();
                Account account = bill.getAccount();
                if (account != null) {
                    Employee employee = Utilities.findEmployeeById(account.getEmployeeID());
                    if (employee != null) {
                        return new SimpleStringProperty(employee.getName() + " " + employee.getSurname());
                    }
                }
                return new SimpleStringProperty("No employee");
            }
        });
        view.getUsername().setCellValueFactory(new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures cellDataFeatures) {
                Bill bill = (Bill)cellDataFeatures.getValue();
                Account account = bill.getAccount();
                if (account != null) {
                    return new SimpleStringProperty(account.getUsername());
                }
                return new SimpleStringProperty("No account");
            }
        });
        view.getTable().setItems(Stock.getBills());


        view.getStartDate().setConverter(new StringConverter<LocalDate>() {
            String pattern = "dd/MM/yyyy";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                view.getStartDate().setPromptText(pattern.toLowerCase());
            }

            @Override public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
        view.getEndDate().setConverter(new StringConverter<LocalDate>() {
            String pattern = "dd/MM/yyyy";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                view.getStartDate().setPromptText(pattern.toLowerCase());
            }

            @Override public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
        view.getTimePeriodPicker().setItems(FXCollections.observableArrayList(
                "Daily",
                "Monthly",
                "All time",
                "Between dates"
        ));
        view.getTimePeriodPicker().getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                view.getStartDate().setDisable(true);
                view.getEndDate().setDisable(true);
                if (t1.equals("Daily")) {
                    calculateDaily(view);
                } else if (t1.equals(("Monthly"))) {
                    calculateMonthly(view);
                } else if (t1.equals("All time")) {
                    calculateAllTime(view);
                } else if (t1.equals("Between dates")) {
                    view.getStartDate().setDisable(false);
                    view.getEndDate().setDisable(false);
                    calculatePeriod(view);
                    view.getStartDate().valueProperty().addListener(new ChangeListener<LocalDate>() {
                        @Override
                        public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate localDate, LocalDate t1) {
                            calculatePeriod(view);
                        }
                    });
                    view.getEndDate().valueProperty().addListener(new ChangeListener<LocalDate>() {
                        @Override
                        public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate localDate, LocalDate t1) {
                            calculatePeriod(view);
                        }
                    });
                }
            }
        });


        view.getLogsPane().setContent(view.getLogsContainer());
        for (int i = Database.Logs.getObjects().size() - 1; i >= 0; i--) {
            view.getLogsContainer().getChildren().add(view.addLogRow(Database.Logs.getObjects().get(i)));
        }
    }

    private void calculateDaily(ActivityView view) {
        LocalDate now = LocalDate.now();
        double amount = 0;
        for (Bill bill : Stock.getBills()) {
            if (bill.getCreatedAt().equals(now)) {
                amount += bill.getTotalCost();
            }
        }
        view.getFilterResult().setText("Daily amount: " + amount + " $");
    }

    private void calculateMonthly(ActivityView view) {
        Date now = new Date();
        cal2.setTime(now);
        double amount = 0;
        for (Bill bill : Stock.getBills()) {
            cal1.setTime( Date.from(bill.getCreatedAt().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
                if (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) {
                    amount += bill.getTotalCost();
                }
            }
        }
        view.getFilterResult().setText("Monthly amount: " + amount + " $");
    }

    private void calculateAllTime(ActivityView view) {
        double amount = 0;
        for (Bill bill : Stock.getBills()) {
            amount += bill.getTotalCost();
        }
        view.getFilterResult().setText("Total amount: " + amount + " $");
    }

    private void calculatePeriod(ActivityView view) {
        LocalDate start = view.getStartDate().getValue();
        LocalDate end = view.getEndDate().getValue();
        double amount = 0;
        for (Bill bill : Stock.getBills()) {
            if ((bill.getCreatedAt().isBefore(end) && bill.getCreatedAt().isAfter(start))
                || (bill.getCreatedAt().equals(start)) || bill.getCreatedAt().equals(end)){
                amount += bill.getTotalCost();
            }
        }
        String start_s = start.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String end_s = end.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        view.getFilterResult().setText("Between " + start_s + " and " + end_s + " : " + amount + " $");
    }

    private String getTotalCost() {
        double amount = 0;
        for (Bill bill : Database.Bills.getObjects()) {
            amount += bill.getTotalCost();
        }
        return String.valueOf(amount);
    }

    private ObservableList<PieChart.Data> getCategoryData() {
        ObservableList<PieChart.Data> data = FXCollections.observableList(new ArrayList<>());
        ArrayList<Category> tmp = new ArrayList<>();
        ArrayList<Integer> ints = new ArrayList<>();
        for (Bill bill : Database.Bills.getObjects()) {
            for (int j = 0; j < bill.getProducts().size(); j++) {
                Category category = Stock.getCategoryByID(bill.getProducts().get(j).getCategoryID());
                if (category != null) {
                    int i;
                    boolean found = false;
                    for (i = 0; i < tmp.size(); i++) {
                        if (category.getID().equals(tmp.get(i).getID())) {
                            ints.set(i, ints.get(i) + bill.getQuantities().get(j));
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        tmp.add(category);
                        ints.add(bill.getQuantities().get(j));
                    }
                }
            }
        }
        for (int i = 0; i < tmp.size(); i++) {
            data.add(new PieChart.Data(tmp.get(i).getName(), ints.get(i)));
        }
        return data;
    }
}
