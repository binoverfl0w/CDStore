package utility;

import db.Database;
import models.Account;
import models.Employee;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Session implements Serializable {
    private static final long serialVersionUID = 3L;

    public Account account;
    public boolean rememberMe;
    public Employee employee;

    public static Session currentSession;

    public Session() {
        account = null;
        rememberMe = false;
    }

    public Session(Account account, boolean rememberMe) {
        this.account = account;
        if (this.account.getEmployeeID() != null)
            setEmployee();
        this.rememberMe = rememberMe;
    }

    public static Session getLastSession() {
        try {
            return Database.getLastSession();
        } catch (DatabaseException e) {
            e.show();
        }
        return null;
    }

    public void setEmployee() {
        ArrayList<Employee> employees = Database.Employees.getObjects();
        for (Employee emp : employees) {
            if (emp.getId() == account.getEmployeeID()) {
                employee = emp;
            }
        }
    }
}
