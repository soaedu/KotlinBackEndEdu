package com.platform

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val connection = getConnectionToDb()
    val isConnectionValid = checkConnection(connection)
    log("Connection is valid: $isConnectionValid")

    if (isConnectionValid) {
        val tableContent = "content"

        val query1Type = QueryType.DELETE
        val query1 = createQuery(
            connection,
            query1Type,
            tableContent
        )
        runQuery(
            query1,
            QueryType.isDMLStatement(query1Type)
        )

        val query2Type = QueryType.INSERT
        val query2 = createQuery(
            connection,
            query2Type,
            tableContent
        )
        runQuery(
            query2,
            QueryType.isDMLStatement(query2Type)
        )

        val query3Type = QueryType.UPDATE
        val query3 = createQuery(
            connection,
            query3Type,
            tableContent
        )
        runQuery(
            query3,
            QueryType.isDMLStatement(query3Type)
        )

        val query4Type = QueryType.SELECT
        val query4 = createQuery(
            connection,
            query4Type,
            tableContent
        )
        runQuery(
            query4,
            QueryType.isDMLStatement(query4Type)
        )
    }
}

fun checkConnection(conn: Connection) = conn.isValid(0)
fun createQuery(conn: Connection, queryType: QueryType, table: String): PreparedStatement {
    val query = when (queryType) {
        QueryType.DELETE -> "DELETE FROM $table WHERE id = 9"
        QueryType.INSERT -> "INSERT INTO $table VALUES(13, 'Objective-C')"
        QueryType.SELECT -> "SELECT * FROM $table ORDER BY id ASC"
        QueryType.UPDATE -> "UPDATE $table SET name = 'Go Lang' WHERE id = 5"
    }
    return conn.prepareStatement(
        query
    )
}
fun runQuery(query: PreparedStatement, isDMLQuery: Boolean) {
    log(query.toString())

    when (isDMLQuery) {
        true -> log(
            handleInsertOrUpdateQuery(query.executeUpdate())
        )
        false -> log(
            handleQueryResult(query.executeQuery()).toString()
        )
    }
}
fun getConnectionToDb(): Connection {
    val jdbcUrl = "jdbc:postgresql://localhost:5432/PlatformDB"
    val conn =  DriverManager.getConnection(
        jdbcUrl,
        "postgres",
        "bgt53Xd"
    )

    return conn
}
fun handleQueryResult(result: ResultSet): List<Content> {
    val listOfContent = mutableListOf<Content>()

    while (result.next()) {
        val id = result.getInt("id")
        val name = result.getString("name")
        log(
            "content item: " +
                    "\n\tid: $id" +
                    "\n\tname: $name"
        )
        listOfContent.add(
            Content(id, name)
        )
    }

    return listOfContent.toList()
}
fun handleInsertOrUpdateQuery(rowsAmount: Int): String {
    return "operation was performed for $rowsAmount row(s)"
}

fun log(msg: String) {
    println(msg)
}
