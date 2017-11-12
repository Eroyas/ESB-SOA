package network;

public class MailSender {

    private final static String senderEmail = "bookings@tta.al";

    public static boolean sendMail(String content, String dest){
        try{
            System.out.println("Sending mail to : " + dest + " from: " + senderEmail + " with content :\n" + content);

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
