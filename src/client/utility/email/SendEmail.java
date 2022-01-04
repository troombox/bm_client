package client.utility.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * The Class SendEmail.
 * main is running a thread
 */
public class SendEmail extends Thread {
	
	/** The msg. */
	private String msg;
	
	/** The to. */
	private String to;
	
	 /**
 	 * Instantiates a new send email.
 	 *
 	 * @param email to send to
 	 * @param msg to send
 	 */
 	public SendEmail (String to, String msg) {
		 this.to = to;
		 this.msg=msg;
	 }
	
	  /**
  	 * The main method. run a thread
  	 *
  	 * @param args the arguments
  	 */
  	public void main(String[] args) {
	    this.start();
	  }
	  
  	/**
  	 * Run.
  	 * send status update email that contains the given message to the given mail that are initialized in the constructor
  	 */
  	public void run() {
	    
	    	final String from = "mebite857@gmail.com";
	    	final String Password = "group@#16";

	 
	        Properties prop = new Properties();
	  		prop.put("mail.smtp.host", "smtp.gmail.com");
	        prop.put("mail.smtp.port", "587");
	        prop.put("mail.smtp.auth", "true");
	        prop.put("mail.smtp.starttls.enable", "true"); 

	         
	         Session session = Session.getInstance(prop,
	                 new javax.mail.Authenticator() {
	                     protected PasswordAuthentication getPasswordAuthentication() {
	                         return new PasswordAuthentication(from, Password);
	                     }
	                 });

	         try {

	             Message message = new MimeMessage(session);
	             message.setFrom(new InternetAddress(from));
	             System.out.println("the mail is: "+ to);
	             message.setRecipients(Message.RecipientType.TO,  InternetAddress.parse(to));
	            
	             message.setSubject("Your order's status updated!");
	             message.setText(msg);

	             Transport.send(message);

	         } catch (MessagingException e) {
	             e.printStackTrace();
	         }
	  }

	}
