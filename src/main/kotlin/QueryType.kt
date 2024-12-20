package com.platform

enum class QueryType {
    DELETE,
    INSERT,
    SELECT,
    UPDATE;

    companion object {
        fun isDMLStatement(queryType: QueryType) = queryType in listOf(DELETE, INSERT, UPDATE)
    }
}