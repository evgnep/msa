package ru.otus.grpc.server

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import ru.otus.grpc.proto.BookOuterClass
import ru.otus.grpc.proto.BookOuterClass.Book
import ru.otus.grpc.proto.BookServiceGrpcKt
import java.util.concurrent.CopyOnWriteArrayList

class BooksService: BookServiceGrpcKt.BookServiceCoroutineImplBase() {
    private val books = mutableListOf<Book>()
    private val callbacks = CopyOnWriteArrayList<(Book)->Unit>()

    override suspend fun createBook(request: Book): BookOuterClass.Empty {
        books.add(request)
        callbacks.forEach { it(request) }
        return BookOuterClass.Empty.getDefaultInstance()
    }

    override fun newBooks(request: BookOuterClass.Empty): Flow<Book> {
        return callbackFlow {
            val callback = { book : Book ->
                trySend(book)
                Unit
            }
            callbacks.add(callback)
            awaitClose { callbacks.remove (callback) }
        }
    }
}