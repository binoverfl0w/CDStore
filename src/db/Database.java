package db;

import models.*;
import utility.DatabaseException;
import utility.Session;
import utility.Utilities;

import java.io.*;
import java.util.ArrayList;

public class Database<T> {

    private ArrayList<T> objects = new ArrayList<T>();
    private String filename;

    public final static Database<Account> Accounts = new Database<Account>(Utilities.ACCOUNTS_PATH);
    public final static Database<Employee> Employees = new Database<Employee>(Utilities.EMPLOYEES_PATH);
    public final static Database<Product> Products = new Database<Product>(Utilities.PRODUCTS_PATH);
    public final static Database<Category> Categories = new Database<Category>(Utilities.CATEGORIES_PATH);
    public final static Database<Supplier> Suppliers = new Database<Supplier>(Utilities.SUPPLIERS_PATH);
    public final static Database<Bill> Bills = new Database<Bill>(Utilities.BILLS_PATH);
    public final static Database<Log> Logs = new Database<Log>(Utilities.LOGS_PATH);

    public Database(String filename) {
        this.filename = filename;
        try {
            readObjects();
        } catch (DatabaseException e) {
            e.show();
        }
    }

    private void readObjects() throws DatabaseException {
        File file = new File(filename);
        if (!file.exists())
            return;
        else {
            try(FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
                objects = (ArrayList<T>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new DatabaseException("Couldn't open the file: " + file);
            }
        }
    }

    private void writeObjects() throws DatabaseException {
        try (FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(objects);
        } catch (IOException e) {
            throw new DatabaseException("Couldn't write to file: " + filename);
        }
    }

    public ArrayList<T> getObjects() {
        return objects;
    }

    public void addObject(T obj) {
        objects.add(obj);
    }

    public static Session getLastSession() throws DatabaseException {
        File file = new File(Utilities.SESSION_PATH);
        if (!file.exists())
            return null;
        Session last = null;
        try(FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis)) {
            last = (Session) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new DatabaseException("Couldn't get last session.");
        }
        return last;
    }

    public static void saveSession() throws DatabaseException {
        try(FileOutputStream fos = new FileOutputStream(Utilities.SESSION_PATH);
        ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(Session.currentSession);
        } catch (IOException e) {
            throw new DatabaseException("Couldn't save current session.");
        }
    }

    public static void deleteSession() {
        File file = new File(Utilities.SESSION_PATH);
        if (file.exists())
            file.delete();
    }

    public void save() {
        try {
            writeObjects();
        } catch (DatabaseException e) {
            e.show();
        }
    }
}
