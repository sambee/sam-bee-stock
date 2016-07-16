package sam.bee.email.client;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Properties;

/**
 * Created by Administrator on 2016/7/15.
 */
public class SMTPClient {

    public static void main(String[] args) throws IOException {

        File f = new File(System.getProperty("user.home") + File.separator + ".stock" + File.separator + "email.properties");
        Properties p = new Properties();
        p.load(new InputStreamReader(new FileInputStream(f), "UTF-8"));
        String smtphost = (String)p.get("smtp.server.host");
        String user = (String)p.get("smtp.server.user");
        String password = (String)p.get("smtp.password");
        String from = (String)p.get("smtp.from");
        String to = (String)p.get("smtp.to");
        String subject = (String)p.get("smtp.subject");
        String content =(String)p.get("smtp.content");
        new SMTPClient().sendEmail(smtphost, user, password, from, to, subject, content);
    }

    public void sendEmail(String smtphost, String  user, String password, String from , String  to, String  subject, String content){

        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", smtphost);
            props.put("mail.smtp.auth", "true");
            Session ssn = Session.getInstance(props, null);

            MimeMessage message = new MimeMessage(ssn);

            InternetAddress fromAddress = new InternetAddress(from);
            message.setFrom(fromAddress);
            InternetAddress toAddress = new InternetAddress(to);
            message.addRecipient(Message.RecipientType.TO, toAddress);
            message.setSubject(subject);
            message.setText(content);

            Transport transport = ssn.getTransport("smtp");
            transport.connect(smtphost, user, password);
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            //transport.send(message);
            transport.close();

        } catch (Exception m) {
            m.printStackTrace();
        }
    }
}
