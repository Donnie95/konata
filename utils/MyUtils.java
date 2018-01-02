package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import componenti.Rilevazione;
import componenti.Sensore;
import componenti.UserAccount;
import exceptions.NullException;
import exceptions.ZeroException;

public class MyUtils {

	public static String File = "C:/Users/steve/Desktop/Sintesi.pdf";
	public static Font bigFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	public static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
	public static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	public static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	
	public static final String ATT_NAME_CONNECTION = "ATTRIBUTE_FOR_CONNECTION";
	private static final String ATT_NAME_USER_NAME = "ATTRIBUTE_FOR_STORE_USER_NAME_IN_COOKIE";
	public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//Store Connection in request attribute.
	//Information stored only exist during requests
	public static void storeConnection(ServletRequest request, Connection conn) {
		request.setAttribute(ATT_NAME_CONNECTION, conn);
	}
	
	//Get connection object has been stored in attribute of request
	public static Connection getStoredConnection(ServletRequest request) {
		
		Connection conn = (Connection) request.getAttribute(ATT_NAME_CONNECTION);
		return conn;
	}
	
	// Store user info in Session.
	public static void storeLoginedUser(HttpSession session, UserAccount loginedUser) {
	// On the JSP can access via ${loginedUser}
		session.setAttribute("loginedUser", loginedUser);
	}
	
	// Get the user information stored in the session.
	public static UserAccount getLoginedUser(HttpSession session) {
		
		UserAccount loginedUser = (UserAccount) session.getAttribute("loginedUser");
		return loginedUser;	
	}
	
	//Store info in Cookie
	public static void storeUserCookie(HttpServletResponse response, UserAccount user) {
		
		System.out.println("Store user coockie");
		Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, user.getUserName());	
		
		//1 day (converted to seconds)
		cookieUserName.setMaxAge(24*60*60);
		response.addCookie(cookieUserName);
	}
	
	public static String getUserNameInCookie(HttpServletRequest request) {
		
		Cookie[] cookies = request.getCookies();
		
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if(ATT_NAME_USER_NAME.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	
	//Delete cookie
	public static void deleteUserCookie(HttpServletResponse response) {
		
		Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, null);
		
		//0 seconds (this cookie expire immediately)
		cookieUserName.setMaxAge(0);
		
		response.addCookie(cookieUserName);
	}
	
	public static void obtainRelev(File file, Connection conn) throws FileNotFoundException, NullException, SQLException, ZeroException {
		
		Scanner inputStream = new Scanner(file);
		
		String riga = null;
		
		while(inputStream.hasNextLine()) {
			
			riga = inputStream.nextLine();
			
			String[] array = riga.split(",");
			
			Rilevazione rilevazione = new Rilevazione();
			
			//Controllo la prima stringa
			if(Character.isLowerCase(array[0].charAt(0))) {
				
				rilevazione.setDescrizione(array[0]);
				
			} else if(Character.isUpperCase(array[0].charAt(0))) {
				
				rilevazione.setMessaggio(array[0]);
				
			} else {
				
				Sensore sens = DBUtils.findSensore(conn, array[0]);
				
				int sensId = Integer.parseInt(array[0]);
				
				rilevazione.setSensID(sensId);
				rilevazione.setMarca(sens.getMarca());
				rilevazione.setModello(sens.getModello());
			}
			
			//Controllo la seconda stringa
			if(Character.isLowerCase(array[1].charAt(0))) {
				
				rilevazione.setDescrizione(array[1]);
				
			} else if(Character.isUpperCase(array[1].charAt(0))) {
				
				rilevazione.setMessaggio(array[1]); 
				
			} else {
				
				Sensore sens = DBUtils.findSensore(conn, array[1]);
				
				int sensId = Integer.parseInt(array[1]);
				
				rilevazione.setSensID(sensId);
				rilevazione.setMarca(sens.getMarca());
				rilevazione.setModello(sens.getModello());
			}
			
			//Controllo la terza stringa
			if(Character.isLowerCase(array[2].charAt(0))) {
				
				rilevazione.setDescrizione(array[2]);
				
			} else if(Character.isUpperCase(array[2].charAt(0))) {
				
				rilevazione.setMessaggio(array[2]);
				
			} else {
				
				Sensore sens = DBUtils.findSensore(conn, array[2]);
				
				int sensId = Integer.parseInt(array[2]);
				
				rilevazione.setSensID(sensId);
				rilevazione.setMarca(sens.getMarca());
				rilevazione.setModello(sens.getModello());
			}
			
			//Trasformo la quarta stringa
			Date parsed = null;
			
			try {
				
				parsed = formatter.parse(array[3]);
				
			} catch (ParseException e1) {

				e1.printStackTrace();
			}
			
			Timestamp data = new java.sql.Timestamp(parsed.getTime());
			
			rilevazione.setData(data);
			
			DBUtils.insertRelev(conn, rilevazione);
		}
		
		inputStream.close();
	}
	
	public static void createPDF(ArrayList<Rilevazione> sintesi, String username, String name, OutputStream out) throws DocumentException, IOException {
		
		try {
			Document document = new Document();
		
			PdfWriter.getInstance(document, out);
		
			document.open();
		
			addMetadati(document);
			addPreface(document, username, name);
			addContent(document, sintesi);
			
			document.close();
		
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void addMetadati(Document document) {
		document.addTitle("Sintesi Ambiente");
		document.addKeywords("Java, PDF, iText");
		document.addAuthor("SensorManager");
		document.addCreator("SensorManager");
	}
	
	public static void addPreface(Document document, String username, String name) throws DocumentException {
		
		Paragraph prefazione = new Paragraph();
		
		//Aggiungo una linea vuota
		addEmptyLine(prefazione, 1);
		
		//Aggiungo il titolo
		prefazione.add(new Paragraph("Sintesi Rilevazioni Ambiente: " + name, bigFont));
		
		addEmptyLine(prefazione, 1);
		
		//Documento generato da
		prefazione.add(new Paragraph("Documento generato da: " + username, smallBold));
		
		prefazione.add(new Paragraph("" + new Date(), smallBold));
		
		addEmptyLine(prefazione, 1);
		
		prefazione.add(new Paragraph("Generato da iText", smallBold));
		
		addEmptyLine(prefazione, 1);
		
		document.add(prefazione);
	}
	
	public static void addContent(Document document, ArrayList<Rilevazione> sintesi) throws DocumentException {
		
		PdfPTable tabella = new PdfPTable(5);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
		PdfPCell table1 = new PdfPCell(new Phrase("Marca", smallBold));
		PdfPCell table2 = new PdfPCell(new Phrase("Modello", smallBold));
		PdfPCell table3 = new PdfPCell(new Phrase("Messaggio", smallBold));
		PdfPCell table4 = new PdfPCell(new Phrase("Descrizione", smallBold));
		PdfPCell table5 = new PdfPCell(new Phrase("Date", smallBold));
		
		tabella.addCell(table1);
		tabella.addCell(table2);
		tabella.addCell(table3);
		tabella.addCell(table4);
		tabella.addCell(table5);
		
		for(Rilevazione ril : sintesi) {
			PdfPCell cell1 = new PdfPCell(new Phrase(ril.getMarca()));
			PdfPCell cell2 = new PdfPCell(new Phrase(ril.getModello()));
			PdfPCell cell3 = new PdfPCell(new Phrase(ril.getMessaggio()));
			PdfPCell cell4 = new PdfPCell(new Phrase(ril.getDescrizione()));
			
			String date = dateFormat.format(ril.data);
			
			PdfPCell cell5 = new PdfPCell(new Phrase(date));
			
			tabella.addCell(cell1);
			tabella.addCell(cell2);
			tabella.addCell(cell3);
			tabella.addCell(cell4);
			tabella.addCell(cell5);
		}
		
		document.add(tabella);
	}
	
	public static void addEmptyLine(Paragraph paragraph, int number) {
		
		for(int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
}
