package covid19.core.util;


import com.sun.mail.smtp.SMTPTransport;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Classe que envia email conteudo HTML.
 */
public class HTMLEmailSender {
    public static String SMTP_SERVER = "smtpServer";
    public static String USERNAME = "userName";
    public static String PASSWORD = "password";
    public static String EMAIL_FROM = "emailFrom";
    public static String EMAIL_TO = "emailTo";
    public static String EMAIL_TOCC = "emailToCC";
    public static String EMAIL_SUBJECT = "emailSubject";
    public static String EMAIL_TEXT = "emailText";
    
    private HTMLEmail email = null;
    
    public HTMLEmailSender(Properties props) {
        this.email = new HTMLEmail.Builder().smtpServer(props.getProperty(SMTP_SERVER))
            .userName(props.getProperty(USERNAME))
            .password(props.getProperty(PASSWORD))
            .emailFrom(props.getProperty(EMAIL_FROM))
            .emailTo(props.getProperty(EMAIL_TO))
            .emailSubject(props.getProperty(EMAIL_SUBJECT))
            .emailText(props.getProperty(EMAIL_TEXT))
            .build();
        
    }
    
    public void send() {
        if(email == null){
            throw  new IllegalStateException("HTMLEmail inválido");
        }
        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    
        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);
    
        try {
        
            msg.setFrom(new InternetAddress(email.emailFrom()));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.emailTo(), false));
            msg.setSubject(email.emailSubject());
            msg.setDataHandler(new DataHandler(new HTMLDataSource(email.emailText())));
        
            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
            t.connect(email.smtpServer(), email.username(), email.password());
            t.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Response: " + t.getLastServerResponse());
        
            t.close();
        
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        
    }
    
    static class HTMLDataSource implements DataSource {
        private String html;
        
        public HTMLDataSource(String htmlString) {
            html = htmlString;
        }
        
        @Override
        public InputStream getInputStream() throws IOException {
            if (html == null) throw new IOException("html message is null!");
            return new ByteArrayInputStream(html.getBytes());
        }
        
        @Override
        public OutputStream getOutputStream() throws IOException {
            throw new IOException("This DataHandler cannot write HTML");
        }
        
        @Override
        public String getContentType() {
            return "text/html";
        }
        
        @Override
        public String getName() {
            return "HTMLDataSource";
        }
    }
    
}


class HTMLEmail {
    private final String smtpServer;  //"smtp server ";
    private final String username;  //"";
    private final String password;  //"";
    
    private final String emailFrom;  //"From@gmail.com";
    private final String emailTo;  //"email_1@yahoo.com, email_2@gmail.com";
    private final String emailToCC;  //"";
    
    private final String emailSubject;  //"Test Send Email via SMTP (HTML)";
    private final String emailText;  //"<h1>Hello Java Mail \n ABC123</h1>";
    
    
    
    private HTMLEmail(Builder builder) {
        this.smtpServer = builder.smtpServer;
        this.username = builder.userName;
        this.password = builder.password;
        this.emailFrom = builder.emailFrom;
        this.emailTo = builder.emailTo;
        this.emailToCC = builder.emailToCC;
        this.emailSubject = builder.emailSubject;
        this.emailText = builder.emailText;
    }
    
    
    
    public String smtpServer() {
        return smtpServer;
    }
    
    
    
    public String username() {
        return username;
    }
    
    
    
    public String password() {
        return password;
    }
    
    
    
    public String emailFrom() {
        return emailFrom;
    }
    
    
    
    public String emailTo() {
        return emailTo;
    }
    
    
    
    public String emailToCC() {
        return emailToCC;
    }
    
    
    
    public String emailSubject() {
        return emailSubject;
    }
    
    
    
    public String emailText() {
        return emailText;
    }
    
    public static class Builder {
        private String smtpServer;
        private String userName;
        private String password;
        private String emailFrom;
        private String emailTo;
        private String emailToCC;
        private String emailSubject;
        private String emailText;
        
        
        
        public Builder smtpServer(final String smtpServer) {
            this.smtpServer = smtpServer;
            return this;
        }
        
        
        
        public Builder userName(final String userName) {
            this.userName = userName;
            return this;
        }
        
        
        
        public Builder password(final String password) {
            this.password = password;
            return this;
        }
        
        
        
        public Builder emailFrom(final String emailFrom) {
            this.emailFrom = emailFrom;
            return this;
        }
        
        
        
        public Builder emailTo(final String emailTo) {
            this.emailTo = emailTo;
            return this;
        }
        
        
        
        public Builder emailToCC(final String emailToCC) {
            this.emailToCC = emailToCC;
            return this;
        }
        
        
        
        public Builder emailSubject(final String emailSubject) {
            this.emailSubject = emailSubject;
            return this;
        }
        
        
        
        public Builder emailText(final String emailText) {
            this.emailText = emailText;
            return this;
        }
        
        public HTMLEmail build(){
            return new HTMLEmail(this);
        }
        
    }
    
    
}