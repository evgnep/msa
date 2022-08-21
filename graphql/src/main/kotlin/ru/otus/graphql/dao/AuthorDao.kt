package ru.otus.graphql.dao

import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Service
import ru.otus.graphql.Author
import ru.otus.graphql.Book
import java.sql.Statement

@Service
class AuthorDao(
    private val jdbc: JdbcOperations,
) {

    fun getAllAuthors(): List<Author> =
        jdbc.query("select * from author", AuthorRowMapper)

    fun getAllAuthorsLike(like: String): List<Author> =
        jdbc.query("select * from author where name like ?", AuthorRowMapper, like)


    private fun buildAuthorsBooksQuery(authorIds: Collection<Int>): String {
        val ids = authorIds.joinToString(", ")
        return "select b.*, ba.author as author_id from book b inner join book_author ba on ba.book = b.id where ba.author in ($ids)"
    }

    fun getAuthorsBooks(authorIds: Collection<Int>): Map<Int, List<Book>> {
        val result = mutableMapOf<Int, MutableList<Book>>()
        jdbc.query(buildAuthorsBooksQuery(authorIds)) { rs, rowNum ->
            result.compute(rs.getInt("author_id")) { _, authors ->
                (authors ?: mutableListOf()).also { it.add(BookRowMapper.mapRow(rs, rowNum)) }
            }
        }
        return result
    }

    fun createAuthor(name: String): Author {
        val keyHolder = GeneratedKeyHolder()
        jdbc.update({ con ->
            con.prepareStatement("insert into author(name) values (?)", Statement.RETURN_GENERATED_KEYS)
                .also { it.setString(1, name) }
        }, keyHolder)

        return Author(keyHolder.key as Int, name)
    }
}