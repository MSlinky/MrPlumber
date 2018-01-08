/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import dao.UsuarioDAO;
import hbm.NewHibernateUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pojo.Usuario;

/**
 *
 * @author marcocameros
 */
@WebServlet(name = "signin", urlPatterns = {"/signin"})
public class signin extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet signin</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet signin at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("Va pa fuera");
        NewHibernateUtil.closeLocalSession();
        response.sendRedirect("Login");
    }

    /**
     * Handles the HTTP <code>POST</code> method. 
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String correo = request.getParameter("correo");
        String clave = request.getParameter("clave");
        
        Session session = NewHibernateUtil.getLocalSession();
        UsuarioDAO usuario = new UsuarioDAO(session);
        Usuario is_persona = new Usuario();
        boolean isSession=false;
        try{
            if(correo.equals(usuario.getUsuarioByCorreo(correo).getCorreo()) && clave.equals(usuario.getUsuarioByCorreo(correo).getClave())){
                HttpSession misession= request.getSession(true);
                
                //is_persona.setClave(clave);
                //is_persona.setCorreo(correo);
                
                is_persona = usuario.getUsuarioByCorreo(correo);
                isSession=true;
                 
                misession.setAttribute("usuario",is_persona);
                misession.setAttribute("sesion",isSession);
                misession.setAttribute("session",session);
                
                response.sendRedirect("Inicio");
            }
            else{
                
                response.sendRedirect("Login");
            }
        }
        catch(NullPointerException e){
            response.sendRedirect("Login");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
