package Mail;

import Controller.ErrorController;
import Controller.LoadingController;
import Controller.LogInController;
import Objects.Search;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    public static void sendEmail(String recepient, Search search) throws Exception {
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

        Message message = sendEmailSuggestion(session, myAccountEmail, recepient, search);
        Transport.send(message);
        System.out.println("Correo Registrar listo");
    }

    //Contenido del message register(Correo)
    private static Message sendEmailSuggestion(Session session, String myAccountEmail, String recepient, Search search) throws IOException {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));

            //MultiPart
            Multipart emailContent = new MimeMultipart();

            //Body 
            MimeBodyPart mimeBody = new MimeBodyPart();
            String text = "Your information about your suggestion: \n";
            text += "\nDate: " + new SimpleDateFormat("dd-MM-yyyy").format(search.getSearchDate()) + " , Hour: " + search.getHour();
            text += "\nLocation: " + search.getLocation();   
            text += "\nSearch Item: " + search.getProduct();
            text += "\n";
            text += "\nSuggestions: \n";
            String[] sugg = search.getSuggestions().split("-");
            for (String sugg1 : sugg) {
                text += sugg1 + "\n";
            }

            mimeBody.setText(text);

            //Image Attachment
            MimeBodyPart pdfAttachment = new MimeBodyPart();
            pdfAttachment.attachFile("src/img/sugg.png");
            emailContent.addBodyPart(pdfAttachment);
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

     private void callAlert() {
        //Se llama la alerta
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/" + "Loading" + ".fxml"));
            Parent root1;
            root1 = (Parent) loader.load();
            //Se llama al controller de la nueva ventana abierta
            LoadingController controller = loader.getController();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Alert");
            //Se le asigna la información a la controladora
//            controller.fill(text);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}//end class
