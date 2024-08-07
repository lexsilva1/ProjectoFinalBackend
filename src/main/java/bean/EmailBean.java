package bean;

import entities.UserEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.JsonUtils;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Properties;
/**
 * The bean class for the email.
 */
@Stateless
public class EmailBean {

    @EJB
    private UserBean userBean;
    @EJB TokenBean tokenBean;
    private static final Logger logger = LogManager.getLogger(EmailBean.class);
    private final String username = "forgexperimentalproject@hotmail.com";
    private final String password = System.getenv("SMTP_PASSWORD");
    private final String host = "smtp-mail.outlook.com";
    private final int port = 587;

    public EmailBean() {
    }

    /**
     * Send email.
     * @param to
     * @param subject
     * @param body
     * @return
     */
    public boolean sendEmail(String to, String subject, String body) {
        boolean sent = false;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(username, password);
            }
        });

        try {

            System.out.println("Sending email to " + to + "...");
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            System.out.println("Sending email...");

            Transport.send(message);
            sent = true;
        } catch (MessagingException e) {
            sent = false;
            e.printStackTrace();
        }
        logger.info("Email sent to " + to + ": " + sent);
        return sent;
    }
/**
     * Send confirmation email.
     * @param user
     * @return
     */
    public boolean sendConfirmationEmail(UserEntity user) {
        boolean sent = false;

        String userEmail = user.getEmail();
        String subject = "ForgeXpereimental Projects - Account Confirmation";
        String confirmationLink = "https://localhost:3000/Confirmation/" + tokenBean.findTokenByUserAndType(user, "REGISTER").getToken();
        String body = "You have registered with ForgeXpereimental Projects "  + ",\n\n"
                + "Please click on the link below to confirm your account.\n\n"
                + "Confirmation Link: " + confirmationLink;

        if (sendEmail(userEmail, subject, body)) {
            logger.info("Confirmation email sent to " + userEmail);
            sent = true;
        } else {
            // Verifica se já se passaram mais de 48 horas desde a criação do user
            LocalDateTime now = LocalDateTime.now();
            long hoursSinceCreation = ChronoUnit.HOURS.between(user.getCreationDate(), now);
            if (hoursSinceCreation > 48) {
                logger.info("User " + user.getEmail() + " has not confirmed their account within 48 hours. Removing user...");
                userBean.removeUser(user.getEmail());
                logger.info("User " + user.getEmail() + " removed.");
            }
        }
        //logger.info("Confirmation email sent to " + userEmail + ": " + sent);
        return sent;
    }
    /**
     * Send password reset email.
     * @param user
     * @return
     */
   public boolean sendPasswordResetEmail(UserEntity user) {
        boolean sent = false;

        String userEmail = user.getEmail();
        String subject = "Scrum - Password Reset";
        String resetLink = "https://localhost:3000/PasswordReset/" + tokenBean.findTokenByUserAndType(user, "PASSWORD_RESET").getToken();
        String body = "You have requested a password reset for your Scrum Platform account " + user.getFirstName() + ",\n\n"
                + "Please click on the link below to reset your password.\n\n"
                + "Password Reset Link: " + resetLink;

        if (sendEmail(userEmail, subject, body)) {
            logger.info("Password reset email sent to " + userEmail);
            sent = true;
        }
        logger.info("Password reset email sent to " + userEmail + ": " + sent);
        return sent;
    }
}
