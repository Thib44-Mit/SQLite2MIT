# SQLite2MIT

SQLite2MIT is a lightweight SQLite extension for MIT App Inventor.

The extension allows MIT applications to execute native SQLite SQL statements without learning a new API.

## Features

- SQLite database access
- Native SQL
- Prepared statements
- Relative or absolute database path
- Query results returned as MIT YailList
- No external libraries

## Current API

### Database

- OpenDatabase(path, createIfMissing)
- CloseDatabase()
- IsOpen()

### SQL

- ExecuteQuery(sql, parameters)
- ExecuteScalar(sql, parameters)
- ExecuteNonQuery(sql, parameters)

### Utilities

- LastInsertId()
- LastError()

## Philosophy

SQLite2MIT is not an ORM.

SQLite2MIT does not generate SQL.

SQLite2MIT simply executes SQL and returns the result.

The application keeps full control.

## Status

Development started in 2026.
Version 1.0 under development.