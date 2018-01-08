/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dao.EventosDAO;
import hbm.NewHibernateUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONObject;
import pojo.Eventos;

/**
 *
 * @author marcocameros
 */
@WebServlet(name = "GetDataEvent", urlPatterns = {"/GetDataEvent"})
public class GetDataEvent extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession misession = (HttpSession) request.getSession();
        Session session = (Session)misession.getAttribute("session");
        
        
        if (misession.getAttribute("sesion").equals(true)){
            
            EventosDAO eventos = new EventosDAO(session);
            JSONObject json = new JSONObject();
            json.put("resultado", true);
            json.put("data", eventos.getAll());
                
            response.setContentType("application/json utf-8");
            PrintWriter out = response.getWriter();
            out.print(json.toString());
        }
    }
}
