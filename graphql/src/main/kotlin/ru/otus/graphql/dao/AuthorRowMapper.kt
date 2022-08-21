package ru.otus.graphql.dao

import org.springframework.jdbc.core.RowMapper
import ru.otus.graphql.Author
import java.sql.ResultSet

object AuthorRowMapper : RowMapper<Author> {
    override fun mapRow(rs: ResultSet, rowNum: Int) = Author(
        id = rs.getInt(Column.ID),
        name = rs.getString(Column.NAME)
    )
}