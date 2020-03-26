package Exceptions;

public class NoRentedSpaceException extends Exception {

    public NoRentedSpaceException(){
        super();
    }

    public NoRentedSpaceException(String message){
        super(message);
    }

    public NoRentedSpaceException(String message, Throwable origin){
        super(message,origin);
    }
}
