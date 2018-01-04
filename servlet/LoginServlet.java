package servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import componenti.UserAccount;
import exceptions.NullException;
import exceptions.ZeroException;
import utils.*;
/**
 * 
 * @author gandalf
 *
 */
@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	
	/**
	 * nome utente
	 */
	public static String name = null;
	/**
	 * password utente
	 */
	public static String pass = null;

	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}
	
	//Show login page
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Forward to /WEB-INF/views/loginView.jsp users cannot access directly into JSP pages placed in WEB-INF
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");
		
		dispatcher.forward(request, response);
	}
	
	//When the user enters userName & password, and click Submit this method will be executed
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = MyUtils.getStoredConnection(request);
		
		name = request.getParameter("userName");
		pass = request.getParameter("password");
		String rememberMeStr = request.getParameter("rememberMe");
		boolean remember = "Y".equals(rememberMeStr);
		
		UserAccount user = null;
		boolean hasError = false;
		String errorString = null;
		
		ServletContext cs = getServletContext();
		
		String path = cs.getRealPath("/") + "/WEB-INF/Rilevazioni.txt";
		
		File rilevazioniF = new File(path);
		
		obtainRelev(rilevazioniF, conn);
		
		if(name == null || pass == null || name.length() == 0 || pass.length() == 0) {
			
			hasError = true;
			errorString = "Richiesti username e password";
			
		} else {
			
			try {
				//Find User in the DB
				user = DBUtils.findUser(conn, name, pass);
				
				if(user == null) {
					
					hasError = true;
					errorString = "Username o password invalidi";
				}
			
			} catch(SQLException e) {
				
				System.out.println("SQLException");
				hasError = true;
				errorString = e.getMessage();
			}
		}
		
		//If error, forward to /WEB-INF/views/login.jsp
		if(hasError) {
			
			user = new UserAccount();
			user.setUserName(name);
			user.setPassword(pass);
			
			
			//Store information in request attribute, before forward
			request.setAttribute("errorString", errorString);
			request.setAttribute("user", user);
			
			//Forward to /WEB-INF/views/login.jsp
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");
			
			dispatcher.forward(request, response);
		}
		
		//If no error store user information in session and redirect to Ambients page
		else {
			
			HttpSession session = request.getSession();
			MyUtils.storeLoginedUser(session, user);
			
			//if user checked "Remenber Me"
			if(remember) {
				MyUtils.storeUserCookie(response, user);
			} 
			//Else delete cookie
			else {
				MyUtils.deleteUserCookie(response);
			}
			
			//Redirect to Ambient page
			response.sendRedirect(request.getContextPath() + "/ambientList");
		}
	}
	
	public void obtainRelev(File file, Connection conn) throws FileNotFoundException {
		
		try {
			
			MyUtils.obtainRelev(file, conn);
			
		} catch (NullException e) {
			
			System.out.println("NullException");
			
		} catch (SQLException e) {
			
			System.out.println("SQLException");
			
		} catch (ZeroException e) {
			
			System.out.println("ZeroException");
		}
	}
}
