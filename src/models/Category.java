package models;

import db.Database;

import java.io.Serializable;
import java.util.UUID;

public class Category implements Serializable {
    private static final long serialVersionUID = 5L;

    private String id;
    private String name;

    public Category(String name) {
        this.name = name;
        id = UUID.randomUUID().toString();
    }

    public Category(String id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }
}
