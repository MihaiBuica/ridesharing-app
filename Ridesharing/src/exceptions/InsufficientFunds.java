package exceptions;

public class InsufficientFunds extends RideShareAppException{
    public InsufficientFunds(String message) {
        super(message);
    }
}
