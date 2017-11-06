package network;

public class MailSender {

    private String senderEmail = "bookings@tta.al";

    public MailSender() {
    }

    public boolean sendMail(String content){
        try{
            //TODO: sending mail

            return true;
        }
        catch (Exception e){
            System.err.println("Cannot send mail.");
            return false;
        }
    }

    public String getSenderEmail() {
        return senderEmail;
    }
}
