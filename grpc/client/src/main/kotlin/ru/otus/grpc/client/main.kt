package ru.otus.grpc.client

import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.runBlocking
import ru.otus.grpc.proto.BookOuterClass
import ru.otus.grpc.proto.BookServiceGrpcKt
import ru.otus.grpc.proto.GreeterGrpcKt
import ru.otus.grpc.proto.Simple

fun main() = runBlocking {
    val channel = ManagedChannelBuilder.forAddress("localhost", 15001)
        .usePlaintext()
        .build()

    val stub = GreeterGrpcKt.GreeterCoroutineStub(channel)
    val request = Simple.CalculateRequest.newBuilder()
        .setA(10.0)
        .setB(20.0)
        .setOperation(Simple.Operation.PLUS)
        .build()
    println(stub.calculate(request).result)

    val booksStub = BookServiceGrpcKt.BookServiceCoroutineStub(channel)
    booksStub.newBooks(BookOuterClass.Empty.getDefaultInstance()).collect {
        println("Receive: $it")
    }
}