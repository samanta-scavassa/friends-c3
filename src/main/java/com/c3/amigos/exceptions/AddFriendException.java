package com.c3.amigos.exceptions;

public class AddFriendException extends RuntimeException {

    public AddFriendException(String message){
        super(message);
    }

    public AddFriendException(String message, Throwable cause){
        super(message, cause);
    }

}
