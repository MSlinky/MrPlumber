/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dao.EventosDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Json;
import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;
import pojo.Eventos;

/**
 *
 * @author marcocameros
 */
@WebServlet(name = "SendDataEvent", urlPatterns = {"/SendDataEvent"})
public class SendDataEvent extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        int id=Integer.parseInt(request.getParameter("id"));
        String name=request.getParameter("name");
        String location=request.getParameter("location");
        String text=request.getParameter("text");
        String startDate=request.getParameter("startDate");
        String endDate=request.getParameter("endDate");
        
        
        HttpSession misession = (HttpSession) request.getSession();
        Session session = (Session)misession.getAttribute("session");
        
        startDate= startDate.replace('T', ' ');
        endDate = endDate.replace('T', ' ');
        
        startDate=startDate.substring(0,startDate.indexOf("."));
        endDate=endDate.substring(0,endDate.indexOf("."));
        System.out.println(startDate);
        System.out.println(id + "\n" +
                name + "\n" +
                location + "\n" +
                text + "\n" +
                startDate + "\n" +
                endDate + "\n" 
                );
        
  
        
        EventosDAO evento = new EventosDAO(session);
        evento.saveEvento(id,name,location,text,startDate,endDate);
        
    }
}