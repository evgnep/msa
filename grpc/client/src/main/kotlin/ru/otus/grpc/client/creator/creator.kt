package ru.otus.grpc.client.creator

import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.runBlocking
import ru.otus.grpc.proto.BookOuterClass
import ru.otus.grpc.proto.BookServiceGrpcKt

fun main(): Unit = runBlocking {
    val channel = ManagedChannelBuilder.forAddress("localhost", 15001)
        .usePlaintext()
        .build()

    val booksStub = BookServiceGrpcKt.BookServiceCoroutineStub(channel)
    booksStub.createBook(BookOuterClass.Book.newBuilder().setName("A").build())
    booksStub.createBook(BookOuterClass.Book.newBuilder().setName("B").build())
}