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

@WebServlet(urlPatterns = {"/GetDataClient"})
public class GetDataClient extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            
            if("Guardar cliente".equals(request.getParameter("action"))){
                Conexion objConexion = new Conexion();
                objConexion.prepareStatement("INSERT INTO cliente(nombre, apellido, telefono, origen, celular,email,rfc,calle,colonia,cp,referencia,idEstado,idCiudad) VALUES "+
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?)");
                
                objConexion.ps.setString(1, request.getParameter("nombre"));
                objConexion.ps.setString(2, request.getParameter("apellido"));
                objConexion.ps.setString(3, request.getParameter("telefono"));
                objConexion.ps.setString(4, request.getParameter("origen"));
                objConexion.ps.setString(5, request.getParameter("celular"));
                objConexion.ps.setString(6, request.getParameter("correo"));
                objConexion.ps.setString(7, request.getParameter("rfc"));
                objConexion.ps.setString(8, request.getParameter("calle"));
                objConexion.ps.setString(9, request.getParameter("colonia"));
                objConexion.ps.setString(10, request.getParameter("cp"));
                objConexion.ps.setString(11, request.getParameter("referencia"));
                objConexion.ps.setString(12, request.getParameter("estado"));
                objConexion.ps.setString(13, request.getParameter("ciudad"));
                
                int r = objConexion.executeUpdate();
                if(r > 0){
                    objConexion.prepareStatement("SELECT cliente.id, cliente.nombre, cliente.apellido, cliente.telefono, cliente.origen, cliente.celular, cliente.email, cliente.rfc, estado.nombre AS estado, ciudad.nombre AS ciudad, cliente.colonia, cliente.calle, cliente.cp, cliente.referencia FROM cliente, estado, ciudad WHERE cliente.idestado=estado.id AND cliente.idciudad=ciudad.id;");
                    objConexion.executeQuery();
                    Json j = new Json();
                    out.print( j.ListToArrayJson( objConexion.QueryToList() ) );
                }
                
                objConexion.Close();
            }else if("actualizarCliente".equals(request.getParameter("action"))){
                Conexion objConexion = new Conexion();
                objConexion.prepareStatement("SELECT cliente.id, cliente.nombre, cliente.apellido, cliente.clienteCuenta, cliente.telefono, cliente.origen, cliente.celular, cliente.email, cliente.rfc, estado.nombre AS estado, ciudad.nombre AS ciudad, cliente.colonia, cliente.calle, cliente.cp, cliente.referencia FROM cliente, estado, ciudad WHERE cliente.idestado=estado.id AND cliente.idciudad=ciudad.id;");
                objConexion.executeQuery();
                Json j = new Json();
                out.print( j.ListToArrayJson( objConexion.QueryToList() ) );
                objConexion.Close();
            }else if("gestionar".equals(request.getParameter("action"))){
                if("EliminarCliente".equals(request.getParameter("table"))){
                    Conexion objConexion = new Conexion();
                    objConexion.prepareStatement("DELETE FROM `cliente` WHERE id=?");
                    objConexion.ps.setString(1, request.getParameter("id"));
                    int r = objConexion.executeUpdate();
                    out.print(r);
                    objConexion.CloseUpdate();
                }else if("ModificarClienteConsulta".equals(request.getParameter("table"))){
                    Conexion objConexion = new Conexion();     
                    objConexion.prepareStatement("SELECT * FROM cliente WHERE id=?");
                    objConexion.ps.setString(1, request.getParameter("id"));
                    objConexion.executeQuery();
                    Json j = new Json();
                    out.print( j.ListToArrayJson( objConexion.QueryToList() ) );
                    objConexion.Close();
                }
            }else if("Modificar cliente".equals(request.getParameter("action"))){
                Conexion objConexion = new Conexion();           
                
                objConexion.prepareStatement("UPDATE `cliente` SET `nombre`=?,`apellido`=?,`telefono`=?, `origen`=?, `celular`=?,`email`=?,`rfc`=?,`calle`=?,`colonia`=?,`cp`=?,`referencia`=?,`idEstado`=?,`idCiudad`=? WHERE id=?");
                
                objConexion.ps.setString(1, request.getParameter("nombre"));
                objConexion.ps.setString(2, request.getParameter("apellido"));
                objConexion.ps.setString(3, request.getParameter("telefono"));
                objConexion.ps.setString(4, request.getParameter("origen"));
                objConexion.ps.setString(5, request.getParameter("celular"));
                objConexion.ps.setString(6, request.getParameter("correo"));
                objConexion.ps.setString(7, request.getParameter("rfc"));
                objConexion.ps.setString(8, request.getParameter("calle"));
                objConexion.ps.setString(9, request.getParameter("colonia"));
                objConexion.ps.setString(10, request.getParameter("cp"));
                objConexion.ps.setString(11, request.getParameter("referencia"));
                objConexion.ps.setString(12, request.getParameter("estado"));
                objConexion.ps.setString(13, request.getParameter("ciudad"));
                objConexion.ps.setString(14, request.getParameter("id"));
                
                int r = objConexion.executeUpdate();
                if(r > 0){
                    objConexion.prepareStatement("SELECT cliente.id, cliente.nombre, cliente.apellido, cliente.telefono, cliente.origen, cliente.celular, cliente.email, cliente.rfc, estado.nombre AS estado, ciudad.nombre AS ciudad, cliente.colonia, cliente.calle, cliente.cp, cliente.referencia FROM cliente, estado, ciudad WHERE cliente.idestado=estado.id AND cliente.idciudad=ciudad.id;");
                    objConexion.executeQuery();
                    Json j = new Json();
                    out.print( j.ListToArrayJson( objConexion.QueryToList() ) );
                }
                objConexion.Close();
            }else if("estados".equals(request.getParameter("action"))){
                Conexion objConexion = new Conexion();      
                objConexion.prepareStatement("SELECT * FROM estado;");
                objConexion.executeQuery();
                Json j = new Json();
                out.print( j.ListToArrayJson( objConexion.QueryToList() ) );
                objConexion.Close();
            }else if("ciudad".equals(request.getParameter("action"))){
                Conexion objConexion = new Conexion();      
                objConexion.prepareStatement("SELECT id, nombre FROM ciudad WHERE idEstado=?;");
                objConexion.ps.setString(1, request.getParameter("ciudad"));
                objConexion.executeQuery();
                Json j = new Json();
                out.print( j.ListToArrayJson( objConexion.QueryToList() ) );
                objConexion.Close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(GetDataClient.class.getName()).log(Level.SEVERE, null, ex);
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
