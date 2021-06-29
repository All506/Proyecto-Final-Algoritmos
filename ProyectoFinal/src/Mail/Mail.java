package Mail;

import Domain.CircularLinkList;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author juanp
 */
public class Mail {

    //Constructor
    public Mail() {
    }

    //Metodo para enviar el correo de registrar estudiante
    public static void sendEmail(String recepient) throws Exception {
        System.out.println("Preparando para mandar el correo");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);

        //Cuenta desde donde mandamos el correo       
        String myAccountEmail = "foodfinders.cr@gmail.com";//CAMBIAR CUENTA
        String password = "foodFinders.cr123";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {//Aunteticacion de la cuenta gmail
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        Message message = sendEmailSuggestion(session, myAccountEmail, recepient);
        Transport.send(message);
        System.out.println("Correo Registrar listo");
    }

    //Contenido del message register(Correo)
    private static Message sendEmailSuggestion(Session session, String myAccountEmail, String recepient) throws IOException{
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));

            //MultiPart
            Multipart emailContent = new MimeMultipart();

            //Body 
            MimeBodyPart mimeBody = new MimeBodyPart();
            String text = "Your information about your search: \n";
//            text += "\n";
//            for (int i = 1; i < data.length; i++) {
//                if (i == 2) {
//                    //text += (dataName[i] + ": " + data[i] + " - " + Util.Utility.getCarrerByID(data[2]).getDescription() +"\n");
//                }else{
////                    text += (dataName[i] + ": " + data[i] + "\n");
//                }
//                
//            }
//            text += "\nYour User is: " + data[1] + " and your Password is: " + data[0];
            mimeBody.setText(text);

            //Image Attachment
//            MimeBodyPart pdfAttachment = new MimeBodyPart();
            //pdfAttachment.attachFile("src/img/logo-ucr.png");

//            emailContent.addBodyPart(pdfAttachment);
            emailContent.addBodyPart(mimeBody);

            //Agregamos al mensaje el contenido a mandar
            message.setSubject("Food Finders");
            message.setContent(emailContent);

            return message;
        } catch (MessagingException me) {
            System.out.println(me);
        }
        return null;
    }

    

}//end class
