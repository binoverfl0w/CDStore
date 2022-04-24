package models;

import db.Database;
import javafx.beans.property.SimpleStringProperty;
import utility.Utilities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class Employee implements Serializable {
    private static final long serialVersionUID = 2L;

    private String id;
    private String name;
    private String surname;
    private String email;
    private long phone;
    private double salary;
    private String imgpath;
    private transient Account account;
    private String birthday;

    public Employee(String name, String surname, String email, long phone, double salary, LocalDate birthday) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.salary = salary;
        this.birthday = birthday.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.id = UUID.randomUUID().toString();
    }

    public String createAccount(int level) {
        String username = (name.charAt(0) + surname + String.valueOf(this.birthday.split("/")[2]).substring(2,4)).toLowerCase();
        String password = Utilities.generateRandomString();
        Account acc = new Account(username, password, level, id);
        return password;
    }

    public void createAccount(String username, String password, int level) {
        this.account = new Account(username.toLowerCase(), password, level, id);
    }

    public String generateUsername() {
        return (name.charAt(0) + surname + String.valueOf(this.birthday.split("/")[2]).substring(2,4)).toLowerCase();
    }

    public String generatePassword() {
        return Utilities.generateRandomString();
    }

    public void detachAccount() {
        if (account != null) {
            account.setEmployeeID(null);
            account = null;
        }
    }

    public String getId() { return id; }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public double getSalary() {
        return salary;
    }

    public String getBirthday() {
        return birthday.toString();
    }

    public long getPhone() {
        return phone;
    }

    public Account getAccount() {
        if (account == null) {
            for (Account acc : Database.Accounts.getObjects()) {
                if (Objects.equals(acc.getEmployeeID(), id)) {
                    account = acc;
                    break;
                }
            }
        }
        return account;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setImgpath(String path) {
        this.imgpath = path;
    }

    public void setAccount(Account acc) {
        acc.setEmployeeID(id);
        account = acc;
    }
}
