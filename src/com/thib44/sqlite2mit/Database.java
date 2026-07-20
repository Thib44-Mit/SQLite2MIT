/*
 SQLite2MIT
 Version : 1.0.0

 Author : Thibault de Chanv...
 Started : July 2026

 This extension provides direct SQLite access for MIT App Inventor.
*************************
  Version : 1.0.1

 Author : Thibault de Chanv...
 Started : July 2026

 add isopen() test on query

*/

package com.thib44.sqlite2mit;

// Android SDK
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.Context;
import android.util.Log;

// Java Standard Library
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.List;
import java.util.ArrayList;



public class Database {

    private final Context context;
    private PrintWriter logWriter = null;
    private boolean debug = false;
    private SQLiteDatabase db = null;
    private String lastError = "";
    private String databasePath = "";

    public Database(Context context) {
        this.context = context;
    }

    public boolean open(String path) {
    // Relative paths (begin no a "/" ) are resolved against the application's private directory.
        log("OpenDatabase(" + path + ")");
        // log("Package      = " + context.getPackageName());
        // log("FilesDir     = " + context.getFilesDir());
        // log("CacheDir     = " + context.getCacheDir());

        try {
            close();
            File file;

            if (path.startsWith("/")) {
                // Chemin absolu
                file = new File(path);
            } else {
                // Chemin relatif au répertoire privé de l'application
                file = new File(context.getFilesDir(), path);
            }

            db = SQLiteDatabase.openOrCreateDatabase(file, null);

        log("Package      = " + context.getPackageName());
        log("FilesDir     = " + context.getFilesDir());
        log("CacheDir     = " + context.getCacheDir());

//            databasePath = file.getAbsolutePath();
            databasePath = db.getPath();
            lastError = "";

            return true;

        } catch (Exception e) {

            db = null;
            databasePath = "";
            lastError = e.getMessage();
            log("open() ERROR : " + e.getMessage());
            return false;
        }
    }

    public void close() {
        log("CloseDatabase()");
        if (db != null) {
            db.close();
            db = null;
        }
        databasePath = "";
        if (logWriter != null) {
            logWriter.close();
            logWriter = null;
            }
    }

    public boolean isOpen() {
        return db != null && db.isOpen();
    }

    public SQLiteDatabase getDatabase() {
        return db;
    }
    
    public String getDatabasePath() {
        return databasePath;
    }

    public String getLastError() {
        return lastError;
    }
    public void setDebug(boolean value) {
    debug = value;
    }

    private void log(String message) {

       if (!debug)
           return;

        try {

            if (logWriter == null) {

               File logfile = new File("storage/emulated/0/Android/data/"+context.getPackageName() + "/files/SQLite2MIT.log");
               // KO File logfile = new File("/storage/emulated/0/Download/SQLite2MIT.log");
               // chemin relatif ne fonctionne pas encore
               // File logfile = new File("/storage/emulated/0/Android/data/edu.mit.appinventor.aicompanion3/files/SQLite2MIT.log");

                logWriter = new PrintWriter(new FileWriter(logfile, true));

            }

            String stamp = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss.SSS",
                Locale.US).format(new Date());

            logWriter.println(stamp + "  " + message);
            logWriter.flush();


        } catch (Exception e) {
        //        throw new RuntimeException(e);
        // Ne jamais interrompre SQLite à cause du log

        }   
    }

    public boolean executeNonQuery(String sql) {
    if (!isOpen()) {
        lastError = "Database is not open";
        return false;
        }

        try {
            db.execSQL(sql);
            lastError = "";
            return true;
        } catch (Exception e) {
            lastError = e.getMessage();
            log("executeNonQuery() ERROR : " + e.getMessage());
            return false;
        }
    }

    public String executeScalar(String sql) {
    if (!isOpen()) {
        lastError = "Database is not open";
        return "";
    }

    Cursor cursor = null;

    try {
        cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            String value = cursor.getString(0);
            lastError = "";
            return value == null ? "" : value;
        }

        lastError = "";
        return "";

    } catch (Exception e) {
        lastError = e.getMessage();
        log("executeScalar() ERROR : " + e.getMessage());
        return "";
    } finally {
        if (cursor != null) {
            cursor.close();
            }
        }
    }
    public List<List<Object>> executeQuery(String sql){
    if (!isOpen()) {
        lastError = "Database is not open";
        return null;
    }    
    try {
        Cursor cursor = db.rawQuery(sql, null);
        List<List<Object>> result = new ArrayList<>();
        while (cursor.moveToNext()) {
          List<Object> row = new ArrayList<>();
          for (int i = 0; i < cursor.getColumnCount(); i++) {

            switch (cursor.getType(i)) {

            case Cursor.FIELD_TYPE_INTEGER:
                row.add(cursor.getLong(i));
                break;

            case Cursor.FIELD_TYPE_FLOAT:
                row.add(cursor.getDouble(i));
                break;

            case Cursor.FIELD_TYPE_STRING:
                row.add(cursor.getString(i));
                break;

            case Cursor.FIELD_TYPE_NULL:
                row.add("");
                break;

            default:
                row.add(cursor.getString(i));
            }
        }

        result.add(row);
        }

        cursor.close();

        return result;
        } catch (Exception e) {

            lastError = "executeQuery : " + e.getMessage();
            log("executeQuery() ERROR : " + e.getMessage());

            return null;
            }

        }

    public String sqliteVersion() {

    try {

        Cursor cursor = db.rawQuery("SELECT sqlite_version()", null);

        if (cursor.moveToFirst()) {
            String version = cursor.getString(0);
            cursor.close();
            return version;
        }

        cursor.close();
        return "";

    } catch (Exception e) {
        log("sqliteVersion() ERROR : " + e.getMessage());
        lastError = e.getMessage();
        return "";
        }
    }
}
