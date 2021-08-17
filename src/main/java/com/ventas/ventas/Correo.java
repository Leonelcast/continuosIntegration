package com.ventas.ventas;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
/**
 * <p>Clase utilizada para usar los servicios de javaMailSender de springframeworkk</p>
 */
@Service
public class Correo {
    public Correo(){

    }; 

    @Autowired
    private JavaMailSender javaMailSender;


    /**
     * @param from de donde se esta enviando
     * @param to a quien se esta mandando 
     * @param subject el asunto del correo 
     * @param body contenido del mensaje     
     * @see <a href="https://www.baeldung.com/spring-email">Spring boot email</a>
     * @since 1.0
    */
    public void sendMail(String from, String to, String subject, String body) {

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setFrom(from);
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(body);

        javaMailSender.send(mail);
    }
}

