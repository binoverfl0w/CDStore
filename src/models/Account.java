package models;

import db.Database;
import utility.Utilities;

import java.io.Serializable;

public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private int level;
    private String employee_id;

    public Account(String u, String p, int l) {
        username = u;
        password = Utilities.encrypt(p);
        level = l;
        employee_id = null;
    }

    public Account(String u, String p, int l, String id) {
        username = u;
        password = Utilities.encrypt(p);
        level = l;
        employee_id = id;
    }

    public Account(String u, String p) {
        username = u;
        password = Utilities.encrypt(p);
        level = 1;
    }

    public static final class Level {
        public static final int CASHIER = 1;
        public static final int MANAGER = 2;
        public static final int ADMINISTRATOR = 3;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getLevel() {
        return level;
    }

    public String getEmployeeID() {
        return employee_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = Utilities.encrypt(password);
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setEmployeeID(String id) {
        this.employee_id = id;
    }

}