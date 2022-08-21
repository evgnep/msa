package ru.otus.graphql.dao

import org.springframework.jdbc.core.RowMapper
import ru.otus.graphql.Book
import java.sql.ResultSet

object BookRowMapper : RowMapper<Book> {
    override fun mapRow(rs: ResultSet, rowNum: Int) = Book(
        id = rs.getInt(Column.ID),
        name = rs.getString(Column.NAME),
        year = rs.getInt(Column.YEAR)
    )
}