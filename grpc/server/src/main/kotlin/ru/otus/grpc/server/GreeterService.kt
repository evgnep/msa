package ru.otus.grpc.server

import ru.otus.grpc.proto.GreeterGrpcKt
import ru.otus.grpc.proto.Simple

class GreeterService: GreeterGrpcKt.GreeterCoroutineImplBase() {
    override suspend fun sayHello(request: Simple.HelloRequest): Simple.HelloReply {
        return Simple.HelloReply.newBuilder().setMessage("Hello, " + request.name).build()
    }

    override suspend fun calculate(request: Simple.CalculateRequest): Simple.CalculateResponse {
        val result = when (request.operation) {
            Simple.Operation.PLUS -> request.a + request.b
            Simple.Operation.MINUS -> request.a - request.b
            else -> throw UnsupportedOperationException("Unknown operation: " + request.operationValue)
        }
        return Simple.CalculateResponse.newBuilder()
            .setResult(result)
            .build();
    }
}