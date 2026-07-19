/*
 SQLite2MIT
 Version : 1.0.0

 Author : Bernard Thibault de Chanvalon
 Started : July 2026

 This extension provides direct SQLite access for MIT App Inventor.
*/

package com.thib44.sqlite2mit;

import android.util.Log;

// MIT App Inventor annotations
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;

// MIT App Inventor runtime
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.util.YailList;

// Java Standard Library
import java.util.ArrayList;
import java.util.List;

/*
 Android SDK
 https://developer.android.com/reference/android/database/sqlite

 MIT App Inventor Extension API
 https://github.com/mit-cml/appinventor-sources

 Java SE API
 https://docs.oracle.com/en/java/
*/


@DesignerComponent(
        version = 1,
        description = "SQLite interface for MIT App Inventor",
        category = ComponentCategory.EXTENSION,
        nonVisible = true,
        iconName = "")

@SimpleObject(external = true)

public class SQLite2MIT extends AndroidNonvisibleComponent {

    private static final String VERSION = "1.0.0";
    private boolean debug = true;
    private String lastError = "";
    private String parameterSeparator = "|";
    private final Database database;


    public SQLite2MIT(ComponentContainer container) {
        super(container.$form());
        database = new Database(container.$context());
    }

 //   private void log(String msg) {
 //       if (debug) {
 //           Log.i("SQLite2MIT", msg);
 //       }
 //   }

    @SimpleFunction(description = "Returns extension version.")
    public String Version() {
        return "SQLite2MIT " + VERSION + "\n SQLite version " + database.sqliteVersion() ;
    }


   @DesignerProperty(
            editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN,
            defaultValue = "False")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public void Debug(boolean value) {
        debug = value;
        database.setDebug(debug);
    }

    @SimpleProperty(description = "Returns debug mode.")
    public boolean Debug() {
        return debug;
    }

    @DesignerProperty(
            editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING,
            defaultValue = "|")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public void ParameterSeparator(String value) {
        parameterSeparator = value;
    }

    @SimpleProperty(description = "Returns parameter separator.")
    public String ParameterSeparator() {
        return parameterSeparator;
    }


    /**
 * Opens or creates a SQLite database.
 *
 * @param path Absolute or relative database path.
 * @return true if successful.
 */
    @SimpleFunction(description = "Open a SQLite database")
    public boolean OpenDatabase(String path) {
        return database.open(path);
    }


    @SimpleFunction(description = "Close the current database")
    public void CloseDatabase() {
        database.close();
    }

    @SimpleFunction(description = "Returns True if a database is open")
    public boolean IsOpen() {
        return database.isOpen();
    }
    @SimpleFunction(description = "Returns the last error message")
    public String LastError() {
        return database.getLastError();
    }

    @SimpleFunction(description = "Execute an SQL statement that returns no result")
    public boolean ExecuteNonQuery(String sql) {
        return database.executeNonQuery(sql);
    }

    @SimpleFunction(description = "Execute a query returning a single value")
    public String ExecuteScalar(String sql) {
        return database.executeScalar(sql);
    }

    @SimpleFunction(description = "Returns the absolute path of the opened database")
    public String DatabasePath() {
        return database.getDatabasePath();
    }

    @SimpleFunction(description = "Execute a SELECT query and return a list of rows.")
    public YailList SQLQuery(String sql) {
        List<List<Object>> result = database.executeQuery(sql);
        if (result == null) {
            return YailList.makeEmptyList();
        }

        List<Object> rows = new ArrayList<>();
        for (List<Object> row : result) {
            rows.add(YailList.makeList(row));
        }

        return YailList.makeList(rows);
    }


}

