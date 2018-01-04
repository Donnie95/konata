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
 * Servlet implementation class EditAmbientServlet
 */
@WebServlet("/editUser")
public class EditUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	String oldUser;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditUserServlet() {
        super();
    }

	/**
	 * Show product edit page
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = MyUtils.getStoredConnection(request);
		
		UserAccount user = null;
		
		String errorString = null;
		
		oldUser = UserListServlet.userName;
		
		System.out.println(oldUser);
		
		try {
			
			user = DBUtils.findUser(conn, oldUser);
			
		} catch (SQLException e) {
			
			System.out.println("SQLException");
			errorString = e.getMessage();
			
		}
		
		/*if no error, the ambient does not exist to edit.
		Redirect to ambientList page*/
		if(errorString != null && user == null) {
			response.sendRedirect(request.getServletPath() + "/userList");
			return;
		}
		
		//store errorString in request attribute, before forward to views
		request.setAttribute("errorString", errorString);
		request.setAttribute("user", user);
		
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/editUser.jsp");
		
		dispatcher.forward(request, response);
	}

	/*
	 * After the user modifies the ambient information, and click Submit, 
	 * this method will be executed
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		Connection conn = MyUtils.getStoredConnection(request);
		
		UserAccount user = null;
		
		String errorString = null;
		
		String username = (request.getParameter("username"));
		String password = (request.getParameter("password"));
		String privilegi = (request.getParameter("selPriv"));
		
		System.out.println(username);

		int priv = Integer.parseInt(privilegi);
		
		user = new UserAccount(username);
		
		System.out.println(username);
		
		user.setPassword(password);
		user.setPrivilegi(priv);
		
		try {
			
			DBUtils.updateUser(conn, oldUser, user);
			
		} catch (SQLException e) {
			
			System.out.println("SQLException");
			
			errorString = e.getMessage();
		}
		
		//Store information to request attribute, before forward to views
		request.setAttribute("errorString", errorString);
		request.setAttribute("user", user);
		
		//If error, forward to Edit page
		if(errorString != null ) {
			
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/editUser.jsp");
			dispatcher.forward(request, response);
		}
		
		//If everything nice, redirect to the ambient listing page
		else {
			response.sendRedirect(request.getContextPath() + "/userList");
		}
	}
}

