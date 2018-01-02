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

@WebServlet(urlPatterns = { "/summary" })
public class SummaryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public static ArrayList<Rilevazione> sintesi;
	
	public static String way;

	public SummaryServlet() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = MyUtils.getStoredConnection(request);

		String errorString = null;
		sintesi = null;
		
		try {
			
			sintesi = DBUtils.querySintesi(conn, AmbientListServlet.id);
			
		} catch(SQLException e) {
			
			e.printStackTrace();
			errorString = e.getMessage();
			
		} catch (ZeroException e) {
			
			e.printStackTrace();
			errorString = e.getMessage();
			
		} catch (NullException e) {
			
			e.printStackTrace();
			errorString = e.getMessage();
		}
		
		//Store info in request attribute, before forward to views
		request.setAttribute("errorString", errorString);
		request.setAttribute("summary", sintesi);
		
		//Forward to /WEB-INF/views/summary.jsp
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/summary.jsp");
		
		dispatcher.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		String errorString = null;
		boolean hasError = false;
		
		if (hasError) {
			
			request.setAttribute("errorString", errorString);
			
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/summary.jsp");
			
			dispatcher.forward(request, response);
		}
		
		else {
			response.sendRedirect(request.getContextPath() + "/createPDF");
		}	
	}
}

