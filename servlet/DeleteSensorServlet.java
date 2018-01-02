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
 * Servlet implementation class deleteSensorServlet
 */
@WebServlet("/deleteSensor")
public class DeleteSensorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteSensorServlet() {
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
		
		Ambiente amb = null;
		
		try {
			amb = DBUtils.findAmbiente(conn, AmbientListServlet.id);
			
		} catch (SQLException | ZeroException | NullException e) {
			
			e.printStackTrace();
		}
		
		String errorString = null;
		
		try {
			
			DBUtils.deleteSensor(conn, SensorListServlet.id);
			
			DBUtils.decrementSens(conn, amb, AmbientListServlet.idInt);
			
		} catch(SQLException e) {
			
			e.printStackTrace();
			
			errorString = e.getMessage();
		}
		
		//if has an error, redirect to the error page
		if(errorString != null) {
			 
			//Store information in the request attribute, before forward to views
			request.setAttribute("errorString", errorString);
			
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/deleteSensorError.jsp");
			
			dispatcher.forward(request, response);
		}
		
		/*
		 * if everything nice
		 * redirect to the sensorList
		 */
		else {
			response.sendRedirect(request.getContextPath() + "/sensorList");
		}
	}

}
