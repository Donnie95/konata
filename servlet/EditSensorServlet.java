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

import componenti.Sensore;
import exceptions.NullException;
import exceptions.ZeroException;
import utils.DBUtils;
import utils.MyUtils;

/**
 * Servlet implementation class EditSensorServlet
 */
@WebServlet("/editSensor")
public class EditSensorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditSensorServlet() {
        super();
    }

	/**
	 * Show sensor edit page
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection conn = MyUtils.getStoredConnection(request);
		
		Sensore sensor = null;
		
		String errorString = null;
		
		//int id = Integer.parseInt(idStr);
		
		try {
			
			sensor = DBUtils.findSensore(conn, SensorListServlet.id);
			
		} catch(SQLException e) {
			
			System.out.println("SQLException");
			errorString = e.getMessage();
			
		} catch (ZeroException e) {
			
			System.out.println("ZeroException");
			
		} catch (NullException e) {

			System.out.println("NullException");
		}
		
		/*
		 * if no error
		 * the sensor does not exist to edit
		 * redirect to productList
		 */
		if(errorString != null && sensor == null) {
			response.sendRedirect(request.getContextPath() + "/sensorList");
			return;
		}
		
		//store errorString in request attribute, before forward to views
		request.setAttribute("errorString", errorString);
		request.setAttribute("sensor", sensor);
		
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/editSensor.jsp");
		
		dispatcher.forward(request, response);
	}

	/**
	 * after the user modifies the product information, and click Submit
	 * this method will be executed
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection conn = MyUtils.getStoredConnection(request);
		
		Sensore sensor = null;
		
		String marca = request.getParameter("marca");
		String modello = request.getParameter("modello");
		String tipo = request.getParameter("selSens");
		String annoStr = request.getParameter("anno");
		
		java.util.Date parsed = format(annoStr);
				
		java.sql.Date anno = new java.sql.Date(parsed.getTime());

		try {
			
			sensor = new Sensore(tipo, marca, modello, anno);
			
			sensor.setId(SensorListServlet.idInt);
			
		} catch (NullException e) {

			System.out.println("NullException");
			
		} catch (ZeroException e) {
			
			System.out.println("ZeroException");
		}
		
		String errorString = null;
		
		try {
			DBUtils.updateSensor(conn, sensor);
			
		} catch(SQLException e) {
			
			System.out.println("SQLException");
			errorString = e.getMessage();
		}
		
		//store information to request attribute, before forward to views
		request.setAttribute("errorString", errorString);
		request.setAttribute("sensor", sensor);
		
		//if error, forward to edit page
		if(errorString != null) {
			
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/editSensor.jsp");
			
			dispatcher.forward(request, response);
		}
		
		/*
		 * if everything nice
		 * redirect to the product listing page
		 */
		else {
			response.sendRedirect(request.getContextPath() + "/sensorList");
		}
	}
	
	public java.util.Date format(String anno){
		
		java.util.Date parsed = null;
		
		try {
			synchronized(formatter){
				parsed = formatter.parse(anno);
			}
			
		} catch (ParseException e) {

			System.out.println("ParseException");
		}
		return parsed;
	}
}
