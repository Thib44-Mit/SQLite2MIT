# SQLite2MIT

SQLite2MIT was created to provide a lightweight, educational and native SQLite interface for MIT App Inventor. Instead of converting SQL results into CSV or JSON, query results are returned directly as MIT App Inventor lists.
---

## Features

- Create or open SQLite databases
- Execute SQL statements (`CREATE`, `INSERT`, `UPDATE`, `DELETE`, ...)
- Execute `SELECT` queries
- Return query results directly as MIT App Inventor lists (`YailList`)
- Retrieve scalar values
- Error reporting
- Support for absolute and relative database paths

---

## Requirements

- MIT App Inventor Companion
- Android 8.0 or later (recommended)
- No external SQLite library required

SQLite is already included in Android.

---

## Installation

1. Download the latest `SQLite2MIT.aix` file.
2. In MIT App Inventor, open:

```
Palette → Extensions → Import Extension
```

3. Import the `.aix` file.

The extension appears as a non-visible component.

---

# Available Functions

## OpenDatabase(path)

Opens an existing SQLite database or creates it if it does not exist.

Example:

```text
OpenDatabase("Library.db")
```

or

```text
OpenDatabase("/storage/emulated/0/Download/Library.db")
```

Returns **true** if successful.

---
### Relative or absolute Paths

If the path does not begin with "/",

"Library.db"

the database is created inside the application's private directory. In this case the db file can't be seen by other users nor file explorater.

If the path begins with "/",

"/storage/emulated/0/Download/Library.db"

the specified absolute path is used. In this case the db file may be seen by other users and file explorater do be imported or exported

---

## CloseDatabase()

Closes the current database.

---

## ExecuteNonQuery(sql)

Executes SQL statements that do not return rows.

Examples:

```sql
CREATE TABLE Books(...)
```

```sql
INSERT INTO Books VALUES(...)
```

```sql
UPDATE Books SET ...
```

```sql
DELETE FROM Books ...
```

Returns **true** if successful.

---

## ExecuteScalar(sql)

Executes a query returning a single value.

Example:

```sql
SELECT COUNT(*) FROM Books
```

Returns the first column of the first row.

---

## SQLQuery(sql)

Executes a SELECT query.

Returns a MIT App Inventor **list of lists**.

Example:

```sql
SELECT Title, Author FROM Books
```

returns

```
(
   ("The Hobbit" "J.R.R. Tolkien")
   ("Dune" "Frank Herbert")
)
```

This format can be used directly with MIT App Inventor list blocks.

---

## LastError()

Returns the last SQLite error message.

Returns an empty string if no error occurred.

---

## DatabasePath()

Returns the absolute path of the currently opened database.

---

## Version()

Returns information about

- SQLite2MIT version
- SQLite version
- Android API version

---

# Example

```sql
CREATE TABLE Person(
    Id INTEGER PRIMARY KEY,
    Name TEXT,
    Age INTEGER
);
```

```sql
INSERT INTO Person(Name,Age)
VALUES('Paul',35);
```

```sql
SELECT Name,Age
FROM Person;
```

Result returned to MIT:

```
(
   ("Paul" 35)
)
```

---

# Error Handling

Always check

```
LastError()
```

after an operation returns `false`.

---
---

# Debugging

SQLite2MIT includes an optional debug log to help diagnose problems during development.

Debug logging can be enabled from the extension by setting the **Debug** property to `true`.

When enabled, all major database operations are written to a log file, including:

- opening and closing databases
- executed SQL statements
- database paths
- error messages
- diagnostic information

The log is intended for development and troubleshooting only and should normally be disabled in production applications.

### Log file location

With MIT AI Companion, the log file is created in the application's external private storage:
/storage/emulated/0/Android/data/edu.mit.appinventor.aicompanion3/files/SQLite2MIT.log

Without companion in /storage/emulated/0/Android/data/appinventorxxxxxx/files/SQLite2MIT.log
```
This location is readable and writable by the application and remains available until the application's data is cleared.

### Typical debugging procedure

1. Set the **Debug** property to `true`.
2. Run the application.
3. Reproduce the problem.
4. Open the file `SQLite2MIT.log`.
5. If necessary, send the log file together with the SQL statements that produced the error.

The log file is appended to between executions. It can safely be deleted at any time while the application is not running.

# Current Version

SQLite2MIT 1.0.0

---

# Roadmap

Future versions may include:

- Transactions
- Prepared statements
- BLOB support
- Metadata functions

---

# License

MIT License

---

# Author

Thibault de Chanvalon

Developed for the MIT App Inventor community.
