package models;

import db.Database;
import utility.Session;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Bill implements Serializable {
    private static final long serialVersionUID = 7L;

    private String id;
    private ArrayList<Product> products;
    private ArrayList<Integer> quantities;
    private Account account = Session.currentSession.account;
    private double total_cost;
    private LocalDate createdAt;

    public Bill() {
        products = new ArrayList<Product>();
        quantities = new ArrayList<Integer>();
        total_cost = 0;
        createdAt = LocalDate.now();
        id = UUID.randomUUID().toString();
    }

    public String getID() {
        return id;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Integer> getQuantities() {
        return quantities;
    }

    public double getTotalCost() {
        return total_cost;
    }

    public void addToTotalCost(double cost) {
        total_cost += cost;
    }

    public void removeFromTotalCost(double cost) {
        total_cost -= cost;
        if (total_cost < 0)
            total_cost = 0;
    }

    public int getProductsSize() {
        int size = 0;
        for (int n : quantities) {
            size += n;
        }
        return size;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public String getCreatedAtString() {
        return createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public Account getAccount() {
        return account;
    }
}
