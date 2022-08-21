package ru.otus.graphql

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.BatchMapping
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import ru.otus.graphql.dao.AuthorDao


@Controller
class AuthorController(
    private val authorDao: AuthorDao,
) {
    @BatchMapping
    fun books(authors: List<Author>): Map<Author, List<Book>> =
        n1helper(authors, { it.id}, authorDao::getAuthorsBooks, listOf())


    @QueryMapping
    fun authorsLike(@Argument like: String): List<Author> =
        authorDao.getAllAuthorsLike(like)

    @MutationMapping
    fun createAuthor(@Argument name: String): Author =
        authorDao.createAuthor(name)

}