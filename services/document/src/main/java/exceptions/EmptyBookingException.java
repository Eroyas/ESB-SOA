package exceptions;

public class EmptyBookingException extends Exception {

    public EmptyBookingException(){
        super("The booking needs at least one kind of reservation (flight, hotel, car).");
    }
}
