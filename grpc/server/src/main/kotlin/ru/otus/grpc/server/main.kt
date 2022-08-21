package ru.otus.grpc.server

import io.grpc.ServerBuilder

fun server() {

    val server = ServerBuilder
        .forPort(15001)
        .addService(GreeterService())
        .addService(BooksService())
        .build()

    Runtime.getRuntime().addShutdownHook(Thread {
        server.shutdown()
        server.awaitTermination()
    })

    server.start()
    server.awaitTermination()
}

fun main() {
    println("started!")
    server()
}