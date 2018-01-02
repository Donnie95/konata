package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import componenti.Ambiente;
import componenti.Sensore;
import exceptions.NullException;
import exceptions.ZeroException;
import utils.DBUtils;
import utils.MyUtils;

/**
 * Servlet implementation class CreateSensorServlet
 */
@WebServlet(urlPatterns = { "/createSensor"})
public class CreateSensorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateSensorServlet() {
        super();
    }

	/**
	 * Show sensor create page
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/createSensor.jsp");
		
		dispatcher.forward(request, response);
	}


	/**
	 * When the user enters the sensor information, and click Submit.
	 * This method will be called.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection conn = MyUtils.getStoredConnection(request);
		
		Ambiente amb = null;
		
		try {
			amb = DBUtils.findAmbiente(conn, AmbientListServlet.id);
			
		} catch (SQLException | ZeroException | NullException e) {
			
			e.printStackTrace();
		}
		
		Sensore sensor = null;
		
		String marca = request.getParameter("marca");
		String modello = request.getParameter("modello");
		String tipo = request.getParameter("selSens");
		String annoStr = request.getParameter("anno");
		
		java.util.Date parsed = null;

		try {
			
			parsed = formatter.parse(annoStr);
			
		} catch (ParseException e1) {

			e1.printStackTrace();
		}
		
		java.sql.Date anno = new java.sql.Date(parsed.getTime());

		try {
			sensor = new Sensore(tipo, marca, modello, anno, AmbientListServlet.idInt);
			
		} catch (NullException e) {

			e.printStackTrace();
		}
		
		String errorString = null;
		
		if (errorString == null) {
			
			try {
				DBUtils.insertSensor(conn, sensor);
				
				DBUtils.incrementSens(conn, amb, AmbientListServlet.idInt);
				
				System.out.println(amb.getNumeroSensori());
				
			} catch (SQLException e) {
				
				e.printStackTrace();
				errorString = e.getMessage();
		}
		
		// Store infomation to request attribute, before forward to views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("sensor", sensor);
		
		// If error, forward to Create page.
		if (errorString != null) {
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/createSensor.jsp");
			dispatcher.forward(request, response);
		}
		
		// If everything nice.
		// Redirect to the sensor listing page.
		else {
		response.sendRedirect(request.getContextPath() + "/sensorList");
		}	
	}
	}
}
