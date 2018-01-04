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

import componenti.UserAccount;
import exceptions.NullException;
import exceptions.ZeroException;
import utils.*;
/**
 * 
 * @author gandalf
 *
 */
@WebServlet(urlPatterns = { "/userList" })
public class UserListServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	/**
	 * username
	 */
	public static String name = null;
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
	
	public UserListServlet() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = MyUtils.getStoredConnection(request);
		
		String errorString = null;
		ArrayList<UserAccount> utenti = null;
		
		try {
			
			utenti = DBUtils.queryUtenti(conn);

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
		request.setAttribute("userList", utenti);
		
		//Forward to /WEB-INF/views/userList.jsp
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/userList.jsp");
		
		dispatcher.forward(request, response);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		name = (request.getParameter("username"));
		way = (request.getParameter("way"));
		
		int wayInt = Integer.parseInt(way);
		
		UserAccount user = null;
		String errorString = null;
		boolean hasError = false;
		
		if(hasError) {
			
			user = new UserAccount();
			user.setUserName(name);
			
			request.setAttribute("errorString", errorString);
			request.setAttribute("user", user);
			
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/userList.jsp");
			
			dispatcher.forward(request, response);
		} 
		
		else {
			if(wayInt == 0)
				response.sendRedirect(request.getContextPath() + "/editUser");
			
			if(wayInt == 1)
				response.sendRedirect(request.getContextPath() + "/deleteUser");
			
			if(wayInt == 2)
				response.sendRedirect(request.getContextPath() + "/addAmbient");
		}
	}	
}
