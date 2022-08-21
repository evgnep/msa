package ru.otus.graphql

import java.time.LocalDate

data class Author(val id: Int, val name: String)

data class Book(
    val id: Int,
    val name: String,
    val year: Int,
)