package sam.bee.email.client;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * Created by Administrator on 2016/7/15.
 */
public class IMAPClient {

    public static void main(String[] args) throws Exception {

        File f = new File(System.getProperty("user.home") + File.separator + ".stock" + File.separator + "email.properties");
        Properties p = new Properties();
        p.load(new InputStreamReader(new FileInputStream(f), "UTF-8"));
        String from =(String)p.get("imap.from");
        //qq email access token. the detail for http://service.mail.qq.com/cgi-bin/help?subtype=1&&id=28&&no=1001256
        String password = (String)p.get("imap.accessToken");
        String to =(String)p.get("imap.to");
        String subject = (String)p.get("imap.subject");
        String content = (String)p.get("imap.content");
        new IMAPClient().sendMessage(subject,content);
       // new IMAPClient().sendEmail(from, password, to, subject, content);
    }

    public void sendMessage(String subject, String content) throws IOException, GeneralSecurityException, MessagingException {
        File f = new File(System.getProperty("user.home") + File.separator + ".stock" + File.separator + "email.properties");
        Properties p = new Properties();
        p.load(new InputStreamReader(new FileInputStream(f), "UTF-8"));
        String from =(String)p.get("imap.from");
        //qq email access token. the detail for http://service.mail.qq.com/cgi-bin/help?subtype=1&&id=28&&no=1001256
        String password = (String)p.get("imap.accessToken");
        String to =(String)p.get("imap.to");
        sendEmail(from, password, to, subject, content.toString());
    }

    public void sendEmail(String from , String password, String to, String subject, String content) throws GeneralSecurityException, MessagingException {
        String QQ_SMTP = "smtp.qq.com";

        Properties props = new Properties();

        props.setProperty("mail.debug", "true");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.host", QQ_SMTP);
        props.setProperty("mail.transport.protocol", "smtp");
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);
        Session session = Session.getInstance(props);
        Message msg = new MimeMessage(session);
        msg.setSubject(subject);

        msg.setText(content);
        msg.setFrom(new InternetAddress(from));
        Transport transport = session.getTransport();
        transport.connect(QQ_SMTP, from, password);
        transport.sendMessage(msg, new Address[] { new InternetAddress(to) });
        transport.close();
    }

}



