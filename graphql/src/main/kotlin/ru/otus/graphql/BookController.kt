package ru.otus.graphql

import org.slf4j.LoggerFactory
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.BatchMapping
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.graphql.data.method.annotation.SubscriptionMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import ru.otus.graphql.dao.BookDao
import java.util.concurrent.CopyOnWriteArrayList

private val log = LoggerFactory.getLogger("book")

@Controller
class BookController(
    private val bookDao: BookDao,
) {
    @QueryMapping
    fun greeting(): String {
        return "Hello world"
    }

    @QueryMapping
    fun books(): List<Book> =
        bookDao.getAllBooks()

    @SchemaMapping
    fun authors(book: Book): List<Author> =
        bookDao.getBookAuthors(book.id)

    @BatchMapping
    fun authorsN1(books: List<Book>): Map<Book, List<Author>> =
        n1helper(books, { it.id}, bookDao::getBooksAuthors, listOf())

    @MutationMapping
    fun createBook(@Argument name: String, @Argument year: Int): Book =
        bookDao.createBook(name, year)
}