package ru.otus.graphql.dao

import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Service
import ru.otus.graphql.Author
import ru.otus.graphql.Book
import java.sql.Statement

@Service
class BookDao(
    private val jdbc: JdbcOperations,
) {

    fun getAllBooks(): List<Book> {
        return jdbc.query("select * from book", BookRowMapper)
    }

    fun getBookAuthors(bookId: Int): List<Author> {
        return jdbc.query(
            """select a.* from author a inner join book_author ba on ba.author = a.id where ba.book = ?""",
            AuthorRowMapper, bookId
        )
    }

    private fun buildBookAuthorsQuery(bookIds: Collection<Int>): String {
        val ids = bookIds.joinToString(", ")
        return "select a.*, ba.book as book_id from author a inner join book_author ba on ba.author = a.id where ba.book in ($ids)"
    }

    fun getBooksAuthors(bookIds: Collection<Int>): Map<Int, List<Author>> {
        val result = mutableMapOf<Int, MutableList<Author>>()
        jdbc.query(buildBookAuthorsQuery(bookIds)) { rs, rowNum ->
            result.compute(rs.getInt("book_id")) { _, authors ->
                (authors ?: mutableListOf()).also { it.add(AuthorRowMapper.mapRow(rs, rowNum)) }
            }
        }
        return result
    }

    fun createBook(name: String, year: Int): Book {
        val keyHolder = GeneratedKeyHolder()
        jdbc.update({ con ->
            con.prepareStatement("insert into book(${Column.NAME}, ${Column.YEAR}) values (?, ?)", Statement.RETURN_GENERATED_KEYS)
                .also {
                    it.setString(1, name)
                    it.setInt(2, year)
                }
        }, keyHolder)

        return Book(keyHolder.key as Int, name, year)
    }
}