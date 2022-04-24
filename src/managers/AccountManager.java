package managers;

import db.Database;
import models.Account;
import utility.DatabaseException;
import utility.Session;
import utility.Utilities;

import java.io.IOException;
import java.net.PortUnreachableException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Objects;

public class AccountManager {

    public static boolean isUserLoggedIn() {
        Session lastSession = Session.getLastSession();
        if (lastSession == null)
            return false;
        if (lastSession.rememberMe && checkAccount(lastSession.account)) {
            Session.currentSession = lastSession;
            try {
                Database.saveSession();
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public static boolean checkAccount(Account account) {
        ArrayList<Account> accounts = Database.Accounts.getObjects();
        for (Account acc : accounts) {
            if (Objects.equals(acc.getUsername(), account.getUsername()) && Objects.equals(acc.getPassword(), account.getPassword()))
                return true;
        }
        return false;
    }

    public static Account checkAccount(String username, String password) {
        ArrayList<Account> accounts = Database.Accounts.getObjects();
        for (Account acc : accounts) {
            if (Objects.equals(acc.getUsername(), username.toLowerCase()) && Objects.equals(acc.getPassword(), Utilities.encrypt(password)))
                return acc;
        }
        return null;
    }

    public static void createAdminAccount() {
        Database.Accounts.addObject(new Account("admin", "admin", Account.Level.ADMINISTRATOR));
        Database.Accounts.save();
    }
}
