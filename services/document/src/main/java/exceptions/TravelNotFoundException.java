package exceptions;

public class TravelNotFoundException extends Exception {

    public TravelNotFoundException(){
        super("The travel linked with the payment wasn't found.");
    }
}
