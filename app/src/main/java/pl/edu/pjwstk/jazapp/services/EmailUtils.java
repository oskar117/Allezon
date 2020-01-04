package pl.edu.pjwstk.jazapp.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

@ApplicationScoped
public class EmailUtils {

    public Session emailAuth(){
        final String fromEmail = "allezon.jazz@gmail.com";
        final String password = "zaq1@WSX";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);
        return session;
    }

    public void sendEmail(String toEmail, String subject, String body){
        try
        {

            MimeMessage msg = new MimeMessage(emailAuth());

            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("allezon.jazz@gmail.com", "Allezon"));

            msg.setReplyTo(InternetAddress.parse("allezon.jazz@gmail.com", false));

            msg.setSubject(subject, "UTF-8");

            msg.setText(body, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            System.out.println("Message is ready: " + Arrays.toString(msg.getFrom()));
            Transport.send(msg);

            System.out.println("EMail Sent Successfully!!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
