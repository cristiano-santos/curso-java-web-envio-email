package enviando.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ObjetoEnviaEmail {

	private String userName = "cristianoprojetosdev@gmail.com";
	private String senha = "edho ccqo urck ayfd";
	// private String senha = "Russi@17";

	private String listaDestinatarios = "";
	private String nomeRemetente = "";
	private String assuntoEmail = "";
	private String textoEmail = "";

	public ObjetoEnviaEmail(String listaDestinatario, String nomeRemetente, String assuntoEmail, String textoEmail) {
		this.listaDestinatarios = listaDestinatario;
		this.nomeRemetente = nomeRemetente;
		this.assuntoEmail = assuntoEmail;
		this.textoEmail = textoEmail;
	}

	public void enviarEmail(boolean envioHTML) throws Exception {
		Properties props = new Properties();

		props.put("mail.smtp.ssl.trust", "*");
		props.put("mail.smtp.auth", "true"); /* Autorização */
		props.put("mail.smtp.starttls", "true"); /* Autenticação */
		props.put("mail.smtp.host", "smtp.gmail.com"); /* Servidor Gmail Google */
		props.put("mail.smtp.port", "465"); /* Porta do servidor */
		props.put("mail.smtp.socketFactory.port", "465"); /* Especifica a porta a ser conectada pelo socket */
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory"); /* Classe socket de conexão ao SMTP */

		Session session = Session.getInstance(props, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, senha);
			}
		});

		Address[] toUser = InternetAddress.parse(listaDestinatarios);

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName, nomeRemetente)); /* Quem está enviando */
		message.setRecipients(Message.RecipientType.TO, toUser); /* Email de destino */
		message.setSubject(assuntoEmail); /* Assunto do email */

		if (envioHTML) {
			message.setContent(textoEmail, "text/html; charset=utf-8");
		} else {
			message.setText(textoEmail);
		}

		Transport.send(message);
	}

	public void enviarEmailAnexo(boolean envioHTML) throws Exception {
		Properties props = new Properties();

		props.put("mail.smtp.ssl.trust", "*");
		props.put("mail.smtp.auth", "true"); /* Autorização */
		props.put("mail.smtp.starttls", "true"); /* Autenticação */
		props.put("mail.smtp.host", "smtp.gmail.com"); /* Servidor Gmail Google */
		props.put("mail.smtp.port", "465"); /* Porta do servidor */
		props.put("mail.smtp.socketFactory.port", "465"); /* Especifica a porta a ser conectada pelo socket */
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory"); /* Classe socket de conexão ao SMTP */

		Session session = Session.getInstance(props, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, senha);
			}
		});

		Address[] toUser = InternetAddress.parse(listaDestinatarios);

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName, nomeRemetente)); /* Quem está enviando */
		message.setRecipients(Message.RecipientType.TO, toUser); /* Email de destino */
		message.setSubject(assuntoEmail); /* Assunto do email */

		/* Parte 1 do email - texto e descrição do email. */

		MimeBodyPart corpoEmail = new MimeBodyPart();

		if (envioHTML) {
			corpoEmail.setContent(textoEmail, "text/html; charset=utf-8");
		} else {
			corpoEmail.setText(textoEmail);
		}

		List<FileInputStream> arquivos = new ArrayList<FileInputStream>();
		arquivos.add(simuladorDePDF());
		arquivos.add(simuladorDePDF());
		arquivos.add(simuladorDePDF());
		arquivos.add(simuladorDePDF());

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(corpoEmail);

		int index = 0;
		for (FileInputStream fileInputStream : arquivos) {
			/* Parte 2 do email - anexos do email. */
			MimeBodyPart anexoEmail = new MimeBodyPart();
			anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(fileInputStream, "application/pdf")));
			anexoEmail.setFileName("anexoemail" + index + ".pdf");

			multipart.addBodyPart(anexoEmail);

			index++;
		}

		message.setContent(multipart);

		Transport.send(message);
	}

	/**
	 * Esse metodo simula o PDF ou qualquer arquivo que possa ser enviado no anexo
	 * de email,
	 * 
	 * Retorna um PDF em branco com o texto do paragrafo de exemplo
	 */
	private FileInputStream simuladorDePDF() throws Exception {
		Document document = new Document();
		File file = new File("fileanexo.pdf");
		file.createNewFile();
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		document.add(new Paragraph("Conteudo de PDF anexo com Java Mail, texto do PDF"));
		document.close();

		return new FileInputStream(file);
	}
}
