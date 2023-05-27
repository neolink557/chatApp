package com.example.chatapp.data

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

sealed class Response(Status: Status, message: String? = null) {
    class Success(message: String? = null) : Response(Status.SUCCESS, message)
    class Error(message: String? = null) : Response(Status.ERROR, message)
    class Loading(message: String? = null) : Response(Status.LOADING, message)
}
