package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.esapi.errors.AccessControlException;
import org.owasp.esapi.reference.DefaultHTTPUtilities;

import componenti.Sensore;
import componenti.UserAccount;
import exceptions.NullException;
import exceptions.ZeroException;
import utils.*;
/**
 * 
 * @author gandalf
 *
 */
@WebServlet(urlPatterns = { "/sensorList" })
public class SensorListServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * id del sensore
	 */
	public static String id = null;
	/**
	 * id del sensore intero
	 */
	public static int idInt = 0;
	/**
	 * indicatore per la scelta della vista collegata
	 * al pulsante premuto
	 */
	public static String way = null;
	/**
	 * indicatore per scegliere la vista 
	 * dipendentemente dall'utente loggato
	 */
	public static int status = 0;
		
	public SensorListServlet() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = MyUtils.getStoredConnection(request);
		
		String errorString = null;
		ArrayList<Sensore> sensori = null;
		
		try {
			
			sensori = DBUtils.querySensori(conn, AmbientListServlet.id);
	
			UserAccount user = DBUtils.findUser(conn, LoginServlet.name);
			
			status = user.getPrivilegi();
			
		} catch(SQLException e) {
			
			System.out.println("SQLException");
			errorString = e.getMessage();
			
		} catch (ZeroException e) {
			
			System.out.println("ZeroException");
			errorString = e.getMessage();
			
		} catch (NullException e) {
			
			System.out.println("NullException");
			errorString = e.getMessage();
		}
		
		//Store info in request attribute, before forward to views
		request.setAttribute("errorString", errorString);
		request.setAttribute("sensorList", sensori);
		
		
		//Forward to /WEB-INF/views/sensorList.jsp
		if(status == 1) {
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/sensorList.jsp");
		
			dispatcher.forward(request, response);
		}
		
		if(status == 0) {
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/sensorListAdmin.jsp");
		
			dispatcher.forward(request, response);
		}
		
		if(status == 2) {
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/sensorListAdmin.jsp");
			
			dispatcher.forward(request, response);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		way = request.getParameter("way");
		id = request.getParameter("sensID");
		
		idInt = Integer.parseInt(id);
		int wayInt = Integer.parseInt(way);
		
		Sensore sens = null;
		String errorString = null;
		boolean hasError = false;
		
		if(hasError) {
			sens = new Sensore();
			try {
				sens.setId(idInt);
			} catch (ZeroException e) {

				System.out.println("ZeroException");
			}
			
			request.setAttribute("errorString", errorString);
			request.setAttribute("sensor", sens);
			
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/sensorList.jsp");
			
			dispatcher.forward(request, response);
		} 
		
		else {
			DefaultHTTPUtilities utilities = new DefaultHTTPUtilities();
			
			if(wayInt == 0) {
				String path = request.getContextPath() + "/relevationList";
				sendRedirect(utilities, path);
				//response.sendRedirect(path);
			}
			if(wayInt == 1) {
				String path = request.getContextPath() + "/editSensor";
				sendRedirect(utilities, path);
				//response.sendRedirect(path);
			}
			if(wayInt == 2) {
				String path = request.getContextPath() + "/deleteSensor";
				sendRedirect(utilities, path);
				//response.sendRedirect(path);
			}
		}
	}
	public void sendRedirect(DefaultHTTPUtilities utilities, String path) throws IOException {
		try {
			utilities.sendRedirect(path);
		} catch (AccessControlException e) {
			
			System.out.println("Errore");
		}
	}
}
