# SQLite2MIT API

## OpenDatabase

```
boolean OpenDatabase(path, createIfMissing)
```

Open an SQLite database.

The path may be absolute or relative.

Returns true if successful.

---

## CloseDatabase

Close current database.

---

## IsOpen

Returns true if a database is currently open.

---

## ExecuteQuery

```
YailList ExecuteQuery(sql, parameters)
```

Execute a SELECT statement.

Parameters are separated by ParameterSeparator.

Returns a YailList.

---

## ExecuteScalar

```
String ExecuteScalar(sql, parameters)
```

Returns first column of first row.

---

## ExecuteNonQuery

```
int ExecuteNonQuery(sql, parameters)
```

Execute

- INSERT
- UPDATE
- DELETE
- CREATE
- DROP
- ALTER
- VACUUM
- PRAGMA

Returns affected rows.

---

## LastInsertId

Return last inserted ROWID.

---

## LastError

Return last SQLite error.

---

## Property

ParameterSeparator

Default

```
|
```