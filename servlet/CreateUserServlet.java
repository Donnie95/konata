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

import componenti.UserAccount;
import utils.DBUtils;
import utils.MyUtils;

/**
 * Servlet implementation class CreateUserServlet
 */
@WebServlet("/createUser")
public class CreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("ambientList", AmbientListServlet.ambienti);
		
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/createUser.jsp");
		
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection conn = MyUtils.getStoredConnection(request);
		
		UserAccount user = null;
		
		String name = (String) request.getParameter("userName");
		String password = (String) request.getParameter("password");
		String privilegi = (String) request.getParameter("selPriv");
		String ambiente = (String) request.getParameter("selAmb");
		
		int priv = Integer.parseInt(privilegi);
		int amb = Integer.parseInt(ambiente);
		
		user = new UserAccount(name);
		
		user.setPrivilegi(priv);
		
		user.setPassword(password);
		
		user.setAmbientID(amb);
		
		String errorString = null;
		
		if (errorString == null) {
			try {
				DBUtils.insertUser(conn, user);
				
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
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/createUser.jsp");
			dispatcher.forward(request, response);
		}
		
		// If everything nice.
		// Redirect to the ambient listing page.
		else {
			response.sendRedirect(request.getContextPath() + "/userList");
		}
	}
}
