package Exceptions;

public class RegistrationNumberFormatException extends RuntimeException {

    public RegistrationNumberFormatException(){
        super();
    }

    public RegistrationNumberFormatException(String message){
        super(message);
    }

    public RegistrationNumberFormatException(String message,Throwable origin){
        super(message,origin);
    }
}
