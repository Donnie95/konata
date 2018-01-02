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

import utils.DBUtils;
import utils.MyUtils;

/**
 * Servlet implementation class deleteAmbientServlet
 */
@WebServlet("/deleteUser")
public class DeleteUserServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUserServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = MyUtils.getStoredConnection(request);
		
		String errorString = null;
		
		try {
			
			DBUtils.deleteUser(conn, UserListServlet.userName);
			
		} catch(SQLException e) {
			
			e.printStackTrace();
			errorString = e.getMessage();
		}
		
		//If has an error, redirect to the error page
		if(errorString != null) {
			
			//Store the information in the request attribute, before forward to views
			request.setAttribute("errorString", errorString);
			
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/deleteUserError.jsp");
			
			dispatcher.forward(request, response);
		}
		
		/*
		 * if everything nice
		 * redirect to the ambientList
		 */
		else {
			response.sendRedirect(request.getContextPath() + "/userList");
		}
	}

}
