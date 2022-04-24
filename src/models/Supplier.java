package models;

import db.Database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Supplier implements Serializable {
    private static final long serialVersionUID = 6L;

    private String id;
    private String name;
    private ArrayList<String> items;

    public Supplier(String name) {
        this.name = name;
        this.items = new ArrayList<String>();
        id = UUID.randomUUID().toString();
    }

    public Supplier(String id, String name) {
        this.name = name;
        this.items = new ArrayList<String>();
        this.id = id;
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public String toString() {
        return name;
    }
}
