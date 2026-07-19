# SQLite2MIT API Reference

Version 1.0.0

---

# Overview

SQLite2MIT provides direct access to the SQLite engine included with Android.

All SQL statements use the standard SQLite syntax.

Results from `SELECT` statements are returned as native MIT App Inventor lists (`YailList`).

---

# Component

SQLite2MIT is a non-visible MIT App Inventor component.

---

# Properties

## Debug

Type

```
Boolean
```

Default

```
false
```

Description

Enables or disables the debug log.

When enabled, SQL statements, database operations and diagnostic information are written into the log file.

---

# Functions

## Version()

Returns version information.

Return type

```
String
```

Example

```
SQLite2MIT 1.0.0 / SQLite 3.xx.x / Android API xx
```

---

## OpenDatabase(path)

Opens an existing SQLite database or creates a new one.

### Parameters

| Name | Type | Description |
|------|------|-------------|
| path | String | Absolute or relative database path |

### Returns

```
Boolean
```

### Relative path

Example

```text
Library.db
```

The database is created in the application's **private internal storage**.

Characteristics:

- No Android storage permissions are required.
- The database is private to the application.
- Other applications normally cannot access it.
- Most file explorers cannot browse this location.
- To exchange the database with another application or a computer, the MIT application must explicitly implement import and/or export functions (using MIT App Inventor file blocks).

---

### Absolute path

Example

```text
/storage/emulated/0/Android/data/<your.package.name>/files/Library.db
```

Characteristics:

- The database location is chosen by the application.
- The database can be shared more easily with external tools, subject to Android storage restrictions.
- Access to shared folders (for example `Download`) depends on the Android version and the permissions granted to the application.
---

## CloseDatabase()

Closes the current database.

Returns

```
Boolean
```

---

## ExecuteNonQuery(sql)

Executes an SQL statement that does not return rows.

Supported SQL statements include

- CREATE TABLE
- CREATE INDEX
- INSERT
- UPDATE
- DELETE
- DROP TABLE
- ALTER TABLE

### Parameters

| Name | Type |
|------|------|
| sql | String |

### Returns

```
Boolean
```

---

## ExecuteScalar(sql)

Executes a query returning a single value.

### Parameters

| Name | Type |
|------|------|
| sql | String |

### Returns

```
Object
```

Only the first column of the first row is returned.

Typical example

```sql
SELECT COUNT(*) FROM Books;
```

---

## SQLQuery(sql)

Executes a SELECT query.

### Parameters

| Name | Type |
|------|------|
| sql | String |

### Returns

```
YailList
```

Each row is itself a YailList.

Example

```sql
SELECT Name, Age
FROM Person;
```

Result

```
(
   ("Paul" 35)
   ("Marie" 28)
)
```

Example using JOIN

```sql
SELECT
    BOOK.Title,
    AUTHOR.Name
FROM BOOK
INNER JOIN AUTHOR
ON BOOK.AuthorId=AUTHOR.Id;
```

---

## DatabasePath()

Returns the absolute path of the opened database.

### Returns

```
String
```

---

## LastError()

Returns the last SQLite error.

### Returns

```
String
```

Returns an empty string if no error occurred.

Typical errors

```
table xxx does not exist

syntax error

database is locked
```

---

# Debug Log

The debug log is enabled through the Debug property.

Current location under MIT AI Companion

```
/storage/emulated/0/Android/data/edu.mit.appinventor.aicompanion3/files/SQLite2MIT.log
```

The log contains

- database opening
- database closing
- SQL statements
- errors
- internal diagnostics

The log file is appended and may safely be deleted while the application is not running.

---

# SQLite Compatibility

SQLite2MIT relies exclusively on the SQLite engine included with Android.

No external SQLite library is required.

The available SQL features therefore depend on the Android version.

---

# Thread Safety

SQLite2MIT is intended to be used from the MIT App Inventor main thread.

Concurrent database access from multiple threads is not currently supported.

---

# Error Handling

Most functions return

- **true** on success
- **false** on failure

Detailed information is available through

```
LastError()
```

---

# Future Extensions

Planned improvements include

- SQL transactions
- Prepared statements
- Metadata functions
- BLOB support

---

# License

MIT License
