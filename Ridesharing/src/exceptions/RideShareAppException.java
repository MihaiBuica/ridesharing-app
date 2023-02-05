package exceptions;

public class RideShareAppException extends Exception{
    private static final long serialVersionUID = 3214520997410884213L;
    public RideShareAppException(String message){
        super(message);
    }
}
