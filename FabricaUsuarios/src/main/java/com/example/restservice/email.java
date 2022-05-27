
package com.example.restservice; 
//importar paquete
//package --------

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session; 
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Ramses Ortega
 */
 //------------------------------------------------------------------------------------------//
 //-----------------------------Importante---------------------------------------------------//
 //Incluir en las librerías, la librería Javax.mail.jar
 //Disponible en este enlace : https://github.com/javaee/javamail/releases/download/JAVAMAIL-1_6_2/javax.mail.jar
public class email {
	
	public email(){
	}
	
    
    public void SendMail(String mensaje,String destino, String asunto) {
		//usuario y contraseña del usuario de google que vayamos a utilizar 
		String Username = "sdyswdam";
		String PassWord = "wkhafehmfqqpjppg";
	
	
		//propiedades del mensaje
	
		String Mensage = mensaje;
		String To = destino;
		String Subject = asunto;
	
		//propiedades de la conexión
		
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Username, PassWord);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(To));
            message.setSubject(Subject);
            message.setText(Mensage);

            Transport.send(message);
            //JOptionPane.showMessageDialog(this, "Su mensaje ha sido enviado");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
}
}