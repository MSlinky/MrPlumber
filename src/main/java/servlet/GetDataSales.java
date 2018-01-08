package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Conexion;
import model.Json;

@WebServlet(urlPatterns = {"/GetDataSales"})
public class GetDataSales extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            if("salesAll".equals(request.getParameter("action"))){
                Conexion objConexion = new Conexion();
                objConexion.prepareStatement("SELECT venta.id, venta.idCliente, venta.IngresoEgreso, venta.idSocio, venta.idFactura, DATE_FORMAT(venta.created_at, \"%e / %m / %Y\") AS fecha, factura.total FROM venta, factura WHERE venta.idFactura=factura.id;");
                objConexion.executeQuery();
                Json j = new Json();
                out.print( j.ListToArrayJson( objConexion.QueryToList() ) );
                
                objConexion.Close();
            }else if("gestionar".equals(request.getParameter("action"))){
                if("infoFactura".equals(request.getParameter("table"))){
                    Conexion objConexion = new Conexion();
                    objConexion.prepareStatement("SELECT * FROM productosfacturados WHERE idFactura = ?;");
                    objConexion.ps.setString(1, request.getParameter("id"));
                    objConexion.executeQuery();
                    Json j = new Json();
                    out.print( j.ListToArrayJson( objConexion.QueryToList() ) );

                    objConexion.Close();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(GetDataSales.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
