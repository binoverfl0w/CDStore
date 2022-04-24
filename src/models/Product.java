package models;

import db.Database;

import java.io.Serializable;
import java.util.UUID;

public class Product implements Serializable {
    private static final long serialVersionUID = 4L;

    private String id;
    private String category_id;
    private String supplier_id;
    private Category category;
    private Supplier supplier;
    private String name;
    private int quantity;
    private double price;

    public Product(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        category_id = null;
        supplier_id = null;
        id = UUID.randomUUID().toString();
    }

    public String getID() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public String getCategoryID() {
        return category_id == null ? "" : category_id;
    }

    public String getSupplierID() {
        return supplier_id == null ? "" : supplier_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setSupplierID(String sid) {
        this.supplier_id = sid;
    }

    public void setCategoryID(String cid) {
        this.category_id = cid;
    }

    public String toString() {
        return name;
    }
}

