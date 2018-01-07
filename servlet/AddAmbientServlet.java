package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.esapi.errors.AccessControlException;
import org.owasp.esapi.reference.DefaultHTTPUtilities;

import componenti.UserAccount;
import utils.DBUtils;
import utils.MyUtils;

/**
 * Servlet implementation class CreateAmbientServlet
 * 
 * 
 * @author gandalf
 *
 */
 
@WebServlet(urlPatterns = { "/addAmbient"})
public class AddAmbientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	String username;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddAmbientServlet() {
        super();
    }

	/**
	 * Show ambient create page
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("ambientList", AmbientListServlet.ambienti);
		
		username = UserListServlet.name;
		
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/addAmbient.jsp");
		
		dispatcher.forward(request, response);
	}

	/**
	 * When the user enters the ambient information, and click Submit.
	 * This method will be called.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection conn = MyUtils.getStoredConnection(request);
		
		UserAccount user = null;
		
		UserAccount newUser = null;
		
		String errorString = null;

		String ambient = (String) request.getParameter("selAmb");
		username = (String) request.getParameter("username");
		
		int amb = Integer.parseInt(ambient);
		

		try {
			user = DBUtils.findUser(conn, username);
			
		} catch (SQLException e) {
			
			System.out.println("SQLException");
		}
		
		newUser = new UserAccount(username);
		
		newUser.setPassword(user.getPassword());
		newUser.setPrivilegi(user.getPrivilegi());
		newUser.setAmbientID(amb);
		
		if (errorString == null) {
			try {
				
				DBUtils.insertUser(conn, newUser);
				
			} catch (SQLException e) {
				
				System.out.println("SQLException");
				errorString = e.getMessage();
			}
		}
		
		
		// Store infomation to request attribute, before forward to views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("user", user);
		
		// If error, forward to Create page.
		if (errorString != null) {
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/addAmbient.jsp");
		dispatcher.forward(request, response);
		}
		
		// If everything nice.
		// Redirect to the ambient listing page.
		else {
			DefaultHTTPUtilities utilities = new DefaultHTTPUtilities();
			String path = request.getContextPath() + "/userList";
			sendRedirect(utilities, path);
			//response.sendRedirect(path);
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

