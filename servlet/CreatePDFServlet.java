package servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.DocumentException;

import componenti.Ambiente;
import exceptions.NullException;
import exceptions.ZeroException;
import utils.DBUtils;
import utils.MyUtils;

/**
 * Servlet implementation class deleteAmbientServlet
 */
@WebServlet("/createPDF")
public class CreatePDFServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreatePDFServlet() {
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
		
		OutputStream out = response.getOutputStream();
		
		String errorString = null;
		
		Ambiente amb = null;
		
		try {
			amb = DBUtils.findAmbiente(conn, AmbientListServlet.id);
			
		} catch (SQLException e) {
			
			System.out.println("SQLException");
			
		} catch (ZeroException e) {
		
			System.out.println("ZeroException");
			
		} catch (NullException e) {
			
			System.out.println("NullException");
		}
		
		try {
			
			MyUtils.createPDF(SummaryServlet.sintesi, LoginServlet.name, amb.getNome(), out);
			
		} catch (DocumentException e) {
			
			System.out.println("DocumentException");
			errorString = e.getMessage();
		}
		
		out.close();
		
		//If has an error, redirect to the error page
		if(errorString != null) {
			
			//Store the information in the request attribute, before forward to views
			request.setAttribute("errorString", errorString);
			
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/createPDFError.jsp");
			
			dispatcher.forward(request, response);
		}
	}

}
