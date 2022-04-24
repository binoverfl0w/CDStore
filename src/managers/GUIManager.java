package managers;

import abstracts.View;
import controllers.*;
import db.Database;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Account;
import models.Bill;
import models.Employee;
import models.Log;
import utility.Logger;
import utility.Utilities;
import views.*;

public class GUIManager {

    public static Stage stage;

    public static Scene main, login, employees, accounts, suppliers;
    public static View employeesView, accountsView, suppliersView;
    public static Stage accountsStage, employeesStage;

    public final static int width = 1200, height = 800;

    public static void displayMainView() {
        stage = new Stage();
        stage.getIcons().add(new Image("images/icon.png"));
        if (main == null) {
            MainView mv = new MainView();
            MainController mc = new MainController(mv);
            main = new Scene(mv.getView(), width, height);
            main.getStylesheets().add("css/main.css");
        }
        stage.setTitle("Control panel");
        stage.setMinHeight(height);
        stage.setMinWidth(width);
        stage.setScene(main);
        stage.show();
        Utilities.centerStage(stage);
    }

    public static void displayLoginView() {
        stage = new Stage();
        stage.getIcons().add(new Image("images/icon.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        LoginView lv = new LoginView();
        LoginController lc = new LoginController(lv);
        login = new Scene(lv.getView(), 400, 400);
        login.getStylesheets().addAll("css/login.css", "css/sharedstyle.css");
        stage.setScene(login);
        stage.show();
        Utilities.centerStage(stage);
    }

    public static void displayEmployeesView() {
        Stage st = new Stage();
        st.getIcons().add(new Image("images/icon.png"));
        st.initModality(Modality.APPLICATION_MODAL);
        if (employees == null) {
            EmployeesView ev = new EmployeesView();
            employeesView = ev;
            EmployeesController ec = new EmployeesController(ev);
            employees = new Scene(ev.getView());
            employees.getStylesheets().addAll("css/employees.css", "css/sharedstyle.css");
        }
        st.setTitle("Employees");
        st.setScene(employees);
        st.show();
        employeesStage = st;
    }

    public static void displayEmployeeManager(Employee emp) {
        Stage st = new Stage();
        st.getIcons().add(new Image("images/icon.png"));
        st.initModality(Modality.APPLICATION_MODAL);
        ManageEmployeeView mev = new ManageEmployeeView();
        ManageEmployeeController mec = new ManageEmployeeController(mev, emp, st);
        mev.getProfileContainer().prefWidthProperty().bind(Bindings.divide(st.widthProperty(), 2));
        mev.getData().prefWidthProperty().bind(Bindings.divide(st.widthProperty(), 2));
        mev.getProfileContainer().prefHeightProperty().bind(st.heightProperty());
        Scene manageEmployee = new Scene(mev.getView(), 1200, 600);
        manageEmployee.getStylesheets().addAll("css/manageemployee.css", "css/sharedstyle.css");
        st.setTitle("Manage employee");
        st.setScene(manageEmployee);
        st.show();
    }

    public static void displayAddEmployeeView() {
        Stage st = new Stage();
        st.getIcons().add(new Image("images/icon.png"));
        st.setTitle("Add employee");
        st.initModality(Modality.APPLICATION_MODAL);
        ManageEmployeeView mev = new ManageEmployeeView();
        AddEmployeeController mec = new AddEmployeeController(mev, st);
        mev.getProfileContainer().prefWidthProperty().bind(Bindings.divide(st.widthProperty(), 2));
        mev.getData().prefWidthProperty().bind(Bindings.divide(st.widthProperty(), 2));
        mev.getProfileContainer().prefHeightProperty().bind(st.heightProperty());
        Scene addEmployee = new Scene(mev.getView(),1200, 600);
        addEmployee.getStylesheets().addAll("css/manageemployee.css", "css/sharedstyle.css");
        st.setScene(addEmployee);
        st.show();
    }

    public static void displayAccountsView() {
        Stage st = new Stage();
        st.setTitle("Accounts");
        st.getIcons().add(new Image("images/icon.png"));
        st.initModality(Modality.APPLICATION_MODAL);
        if (accounts == null) {
            AccountsView av = new AccountsView();
            accountsView = av;
            AccountsController ac = new AccountsController(av);
            accounts = new Scene(av.getView(), 600, 400);
        }
        accounts.getStylesheets().addAll("css/accounts.css","css/sharedstyle.css");
        st.setScene(accounts);
        st.show();
        accountsStage = st;
    }

    public static void displayAddAccountView() {
        Stage st = new Stage();
        st.initModality(Modality.APPLICATION_MODAL);
        st.setTitle("Add account");
        st.getIcons().add(new Image("images/icon.png"));
        ManageAccountView mav = new ManageAccountView();
        AddAccountController aac = new AddAccountController(mav, st);
        Scene addAccount = new Scene(mav.getView());
        addAccount.getStylesheets().addAll("css/accounts.css","css/sharedstyle.css");
        st.setScene(addAccount);
        st.show();
    }

    public static void displayAccountManager(Account acc) {
        Stage st = new Stage();
        st.initModality(Modality.APPLICATION_MODAL);
        st.setTitle("Manage account");
        st.getIcons().add(new Image("images/icon.png"));
        ManageAccountView mav = new ManageAccountView();
        ManageAccountController mac = new ManageAccountController(mav, acc, st);
        Scene addAccount = new Scene(mav.getView());
        addAccount.getStylesheets().addAll("css/accounts.css","css/sharedstyle.css");
        st.setScene(addAccount);
        st.show();
    }

    public static void refresh() {
        if (GUIManager.employeesView != null) {
            ((EmployeesView) GUIManager.employeesView).getTable().setItems(FXCollections.observableList(Database.Employees.getObjects()));
            ((EmployeesView) GUIManager.employeesView).getTable().refresh();
        }
        if (GUIManager.accountsView != null) {
            ((AccountsView) GUIManager.accountsView).getTable().setItems(FXCollections.observableList(Database.Accounts.getObjects()));
            ((AccountsView) GUIManager.accountsView).getTable().refresh();
        }
    }

    public static void displayStockView() {
        Stage st = new Stage();
        st.initModality(Modality.APPLICATION_MODAL);
        st.setTitle("Stock");
        st.getIcons().add(new Image("images/icon.png"));
        StockView sv = new StockView();
        StockController sc = new StockController(sv);
        Scene stock = new Scene(sv.getView(),1100,600);
        stock.getStylesheets().addAll("css/stock.css","css/sharedstyle.css");
        st.setScene(stock);
        st.show();
    }

    public static void displaySuppliersView() {
        Stage st = new Stage();
        st.initModality(Modality.APPLICATION_MODAL);
        st.setTitle("Suppliers");
        st.getIcons().add(new Image("images/icon.png"));
        if (suppliers == null) {
            SuppliersView sv = new SuppliersView();
            suppliersView = sv;
            SuppliersController sc = new SuppliersController(sv);
            suppliers = new Scene(sv.getView(), 800, 600);
            suppliers.getStylesheets().addAll("css/stock.css","css/sharedstyle.css");
        }
        st.setScene(suppliers);
        st.show();
    }

    public static void displayBillView() {
        Stage st = new Stage();
        st.initModality(Modality.APPLICATION_MODAL);
        st.setTitle("Bill");
        st.getIcons().add(new Image("images/icon.png"));
        BillView bv = new BillView();
        BillController bc = new BillController(bv);
        Scene bill = new Scene(bv.getView(),700,700);
        bill.getStylesheets().addAll("css/stock.css","css/sharedstyle.css");
        st.setScene(bill);
        st.show();
    }

    public static void displayCreatedBill(Bill bill) {
        Stage st = new Stage();
        st.initModality(Modality.APPLICATION_MODAL);
        st.setTitle("Bill no."+Database.Bills.getObjects().size());
        st.getIcons().add(new Image("images/icon.png"));
        CreatedBillView cbv = new CreatedBillView();
        CreatedBillController cbc = new CreatedBillController(cbv, bill);
        Scene showBill = new Scene(cbv.getView(), 600,600);
        showBill.getStylesheets().addAll("css/stock.css","css/sharedstyle.css");
        st.setScene(showBill);
        st.show();
    }

    public static void displayActivityView() {
        Stage st = new Stage();
        st.initModality(Modality.APPLICATION_MODAL);
        st.setTitle("Activity");
        st.getIcons().add(new Image("images/icon.png"));
        ActivityView av = new ActivityView();
        ActivityController ac = new ActivityController(av);
        Scene activity = new Scene(av.getView(), 1200, 600);
        activity.getStylesheets().addAll("css/activity.css","css/sharedstyle.css");
        st.setScene(activity);
        st.show();
    }
}
