package models;

import utility.Session;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

public class Log implements Serializable {
    final static long serialVersionUID = 7L;

    private Date createdAt = new Date(System.currentTimeMillis());
    private Account account = Session.currentSession.account;
    private String value;

    public Log(String value) {
        this.value = value;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getValue() {
        return value;
    }

    public Account getAccount() {
        return account;
    }
}
