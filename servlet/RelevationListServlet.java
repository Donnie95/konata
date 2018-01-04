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

import componenti.Rilevazione;
import exceptions.NullException;
import exceptions.ZeroException;
import utils.*;
/**
 * 
 * @author gandalf
 *
 */
@WebServlet(urlPatterns = { "/relevationList" })
public class RelevationListServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public RelevationListServlet() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = MyUtils.getStoredConnection(request);
		
		String errorString = null;
		
		ArrayList<Rilevazione> rilevazioni = null;
		
		try {
			
			rilevazioni = DBUtils.queryRilevazioni(conn, SensorListServlet.id);	
			
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
		request.setAttribute("relevationList", rilevazioni);
		
		//Forward to /WEB-INF/views/ambientList.jsp
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/relevationList.jsp");
		
		dispatcher.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
}
