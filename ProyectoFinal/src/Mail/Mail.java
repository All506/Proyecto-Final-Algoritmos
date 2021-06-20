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
    public static void sugestedEmail(String recepient, CircularLinkList[] data) throws Exception {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);

        //Cuenta desde donde mandamos el correo
        String myAccountEmail = "noreplyucr1@gmail.com";//CAMBIAR CUENTA
        String password = "ucr12345!";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {//Aunteticacion de la cuenta gmail
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        Message message = sendEmailRegister(session, myAccountEmail, recepient, data);
        Transport.send(message);

    }

    //Contenido del message register(Correo)
    private static Message sendEmailRegister(Session session, String myAccountEmail, String recepient, CircularLinkList[] data) throws IOException{
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));

            //MultiPart
            Multipart emailContent = new MimeMultipart();

            //Body 
            MimeBodyPart mimeBody = new MimeBodyPart();
//            String text = "Your information about your registration: \n";
//            text += "\n";
            for (int i = 1; i < data.length; i++) {
                if (i == 2) {
                    //text += (dataName[i] + ": " + data[i] + " - " + Util.Utility.getCarrerByID(data[2]).getDescription() +"\n");
                }else{
//                    text += (dataName[i] + ": " + data[i] + "\n");
                }
                
            }
//            text += "\nYour User is: " + data[1] + " and your Password is: " + data[0];
//            mimeBody.setText(text);

            //Image Attachment
            MimeBodyPart pdfAttachment = new MimeBodyPart();
            //pdfAttachment.attachFile("src/img/logo-ucr.png");

            emailContent.addBodyPart(pdfAttachment);
            emailContent.addBodyPart(mimeBody);

            //Agregamos al mensaje el contenido a mandar
            message.setSubject("Universidad de Costa Rica");
            message.setContent(emailContent);

            return message;
        } catch (MessagingException me) {
            System.out.println(me);
        }
        return null;
    }




}//end class
