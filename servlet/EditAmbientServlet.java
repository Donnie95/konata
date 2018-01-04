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

import componenti.Ambiente;
import exceptions.NullException;
import exceptions.ZeroException;
import utils.DBUtils;
import utils.MyUtils;

/**
 * Servlet implementation class EditAmbientServlet
 */
@WebServlet("/editAmbient")
public class EditAmbientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	int numSens;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditAmbientServlet() {
        super();
    }

	/**
	 * Show product edit page
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = MyUtils.getStoredConnection(request);
		
		Ambiente ambient = null;
		
		String errorString = null;
		
		try {
			
			ambient = DBUtils.findAmbiente(conn, AmbientListServlet.id);
			
			numSens = ambient.getNumeroSensori();
			
		} catch (SQLException e) {
			
			System.out.println("SQLException");
			errorString = e.getMessage();
			
		} catch (ZeroException e) {
			
			System.out.println("ZeroException");
			
		} catch (NullException e) {
			
			System.out.println("NullException");
		}
		
		/*if no error, the ambient does not exist to edit.
		Redirect to ambientList page*/
		if(errorString != null && ambient == null) {
			response.sendRedirect(request.getServletPath() + "/ambientList");
			return;
		}
		
		//store errorString in request attribute, before forward to views
		request.setAttribute("errorString", errorString);
		request.setAttribute("ambient", ambient);
		
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/editAmbient.jsp");
		
		dispatcher.forward(request, response);
	}

	/*
	 * After the user modifies the ambient information, and click Submit, 
	 * this method will be executed
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		Connection conn = MyUtils.getStoredConnection(request);
		
		Ambiente ambient = null;
		
		String errorString = null;
		
		String name = (request.getParameter("nome"));
		String ubicazione = (request.getParameter("ubicazione"));
		String tipo = (request.getParameter("selAmbient"));
		
		try {
			
			ambient = new Ambiente(name, tipo, ubicazione, numSens);
			
			ambient.setId(AmbientListServlet.idInt);
			
		} catch (ZeroException e) {

			System.out.println("ZeroException");
			
		} catch (NullException e) {
			
			System.out.println("NullException");
		}
		
		try {
			
			DBUtils.updateAmbient(conn, ambient);
			
		} catch (SQLException e) {
			
			System.out.println("SQLException");
			
			errorString = e.getMessage();
		}
		
		//Store information to request attribute, before forward to views
		request.setAttribute("errorString", errorString);
		request.setAttribute("ambient", ambient);
		
		//If error, forward to Edit page
		if(errorString != null ) {
			
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/editAmbient.jsp");
			dispatcher.forward(request, response);
		}
		
		//If everything nice, redirect to the ambient listing page
		else {
			response.sendRedirect(request.getContextPath() + "/ambientList");
		}
	}
}
