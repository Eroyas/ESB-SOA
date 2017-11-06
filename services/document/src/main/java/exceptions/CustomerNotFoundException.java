package exceptions;

public class CustomerNotFoundException extends Exception{

    public CustomerNotFoundException(){
        super("The customerId linked with the payment wasn't found.");
    }

}
