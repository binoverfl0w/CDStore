package utility;

import db.Database;
import models.Log;

import java.util.Date;

public class Logger {

    public static void log(String log) {
        Database.Logs.addObject(new Log(log));
        Database.Logs.save();
    }
}
