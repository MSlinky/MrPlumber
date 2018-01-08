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

@WebServlet(urlPatterns = {"/GetDataAssociate"})
public class GetDataAssociate extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            
            if("Guardar socio".equals(request.getParameter("action"))){
                Conexion objConexion = new Conexion();

                objConexion.prepareStatement("INSERT INTO socio(nombre, apellido, telefono,celular,email,rfc,especialidad,calle,colonia,cp,referencia,idEstado,idCiudad) VALUES "+
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?)");
                
                objConexion.ps.setString(1, request.getParameter("nombre"));
                objConexion.ps.setString(2, request.getParameter("apellido"));
                objConexion.ps.setString(3, request.getParameter("telefono"));
                objConexion.ps.setString(4, request.getParameter("celular"));
                objConexion.ps.setString(5, request.getParameter("correo"));
                objConexion.ps.setString(6, request.getParameter("rfc"));
                objConexion.ps.setString(7, request.getParameter("especialidad"));
                objConexion.ps.setString(8, request.getParameter("calle"));
                objConexion.ps.setString(9, request.getParameter("colonia"));
                objConexion.ps.setString(10, request.getParameter("cp"));
                objConexion.ps.setString(11, request.getParameter("referencia"));
                objConexion.ps.setString(12, request.getParameter("estado"));
                objConexion.ps.setString(13, request.getParameter("ciudad"));
                
                int r = objConexion.executeUpdate();
                if(r > 0){
                    objConexion.prepareStatement("SELECT socio.id, socio.nombre, socio.apellido, socio.telefono, socio.celular, socio.email, socio.rfc, socio.calle, socio.colonia, socio.cp, socio.referencia, especialidad.nombre AS especialidad, estado.nombre AS idEstado, ciudad.nombre AS idCiudad FROM socio, especialidad, estado, ciudad WHERE socio.especialidad=especialidad.id AND socio.idEstado=estado.id AND socio.idCiudad=ciudad.id;");
                    objConexion.executeQuery();
                    Json j = new Json();
                    out.print( j.ListToArrayJson( objConexion.QueryToList() ) ); 
                }
                
                objConexion.Close();
            }else if("actualizarSocio".equals(request.getParameter("action"))){
                Conexion objConexion = new Conexion();
                objConexion.prepareStatement("SELECT socio.id, socio.nombre, socio.apellido, socio.telefono, socio.celular, socio.email, socio.rfc, socio.calle, socio.colonia, socio.cp, socio.referencia, especialidad.nombre AS especialidad, estado.nombre AS idEstado, ciudad.nombre AS idCiudad FROM socio, especialidad, estado, ciudad WHERE socio.especialidad=especialidad.id AND socio.idEstado=estado.id AND socio.idCiudad=ciudad.id;");
                objConexion.executeQuery();
                Json j = new Json();
                out.print( j.ListToArrayJson( objConexion.QueryToList() ) );
                objConexion.Close();
            }else if("actualizarEspecialidad".equals(request.getParameter("action"))){
                Conexion objConexion = new Conexion();
                objConexion.prepareStatement("SELECT * FROM especialidad;");
                objConexion.executeQuery();
                Json j = new Json();
                out.print( j.ListToArrayJson( objConexion.QueryToList() ) );
                objConexion.Close();
            }else if("gestionar".equals(request.getParameter("action"))){
                if("EliminarSocio".equals(request.getParameter("table"))){
                    Conexion objConexion = new Conexion();
                    
                    objConexion.prepareStatement("DELETE auto FROM socio, auto, socioauto WHERE socio.id=? AND socioauto.idSocio=socio.id AND socioauto.idAuto=auto.id;");
                    objConexion.ps.setString(1, request.getParameter("id"));
                    int r = objConexion.executeUpdate();
                    
                    objConexion.prepareStatement("DELETE FROM `socio` WHERE id=?");
                    objConexion.ps.setString(1, request.getParameter("id"));
                    r = objConexion.executeUpdate();
                    
                    out.print(r);
                    objConexion.CloseUpdate();
                }else if("ModificarSocioConsulta".equals(request.getParameter("table"))){
                    Conexion objConexion = new Conexion();     
                    objConexion.prepareStatement("SELECT * FROM socio WHERE id=?");
                    objConexion.ps.setString(1, request.getParameter("id"));
                    objConexion.executeQuery();
                    Json j = new Json();
                    out.print( j.ListToArrayJson( objConexion.QueryToList() ) );
                    objConexion.Close();
                }else if("EliminarEspecialidad".equals(request.getParameter("table"))){
                    Conexion objConexion = new Conexion();
                    objConexion.prepareStatement("DELETE FROM `especialidad` WHERE id=?");
                    objConexion.ps.setString(1, request.getParameter("id"));
                    int r = objConexion.executeUpdate();
                    out.print(r);
                    objConexion.CloseUpdate();
                }else if("ModificarEspecialidadConsulta".equals(request.getParameter("table"))){
                    Conexion objConexion = new Conexion();     
                    objConexion.prepareStatement("SELECT * FROM especialidad WHERE id=?");
                    objConexion.ps.setString(1, request.getParameter("id"));
                    objConexion.executeQuery();
                    Json j = new Json();
                    out.print( j.ListToArrayJson( objConexion.QueryToList() ) );
                    objConexion.Close();
                }else if("ConsultarSocioAutos".equals(request.getParameter("table"))){
                    Conexion objConexion = new Conexion();     
                    objConexion.prepareStatement("SELECT auto.*, socio.nombre AS nombreSocio, socio.id AS idSocio FROM auto, socioauto, socio WHERE socioauto.idSocio=socio.id AND socioauto.idAuto=auto.id AND socio.id=?");
                    objConexion.ps.setString(1, request.getParameter("id"));
                    objConexion.executeQuery();
                    Json j = new Json();
                    out.print( j.ListToArrayJson( objConexion.QueryToList() ) );
                    objConexion.Close();
                }else if("GuardarAuto".equals(request.getParameter("table"))){
                    Conexion objConexion = new Conexion();
                    objConexion.prepareStatement("SELECT MAX(id) AS id FROM auto");
                    objConexion.executeQuery();
                    int idAuto = Integer.parseInt(objConexion.QueryToList().get(0).get("id")) + 1;
                    
                    objConexion.prepareStatement("INSERT INTO auto (id, placa, status) VALUES ("+idAuto+", ?, ?);");
                    objConexion.ps.setString(1, request.getParameter("placa"));
                    objConexion.ps.setString(2, request.getParameter("status"));
                    
                    objConexion.executeUpdate();
                    
                    objConexion.prepareStatement("INSERT INTO socioauto (idSocio, idAuto) VALUES (?, ?);");
                    objConexion.ps.setString(1, request.getParameter("idSocio"));
                    objConexion.ps.setInt(2, idAuto);
                    
                    int r = objConexion.executeUpdate();
                    
                    if(r > 0){
                        objConexion.prepareStatement("SELECT auto.*, socio.nombre AS nombreSocio, socio.id AS idSocio FROM auto, socioauto, socio WHERE socioauto.idSocio=socio.id AND socioauto.idAuto=auto.id AND socio.id=?");
                        objConexion.ps.setString(1, request.getParameter("idSocio"));
                        objConexion.executeQuery();
                        Json j = new Json();
                        out.print( j.ListToArrayJson( objConexion.QueryToList() ) ); 
                    }

                    objConexion.Close();
                }else if("ModificarAuto".equals(request.getParameter("table"))){
                    Conexion objConexion = new Conexion();
                    
                    objConexion.prepareStatement("UPDATE auto SET placa=?, status=? WHERE id=?;");
                    objConexion.ps.setString(1, request.getParameter("placa"));
                    objConexion.ps.setString(2, request.getParameter("status"));
                    objConexion.ps.setString(3, request.getParameter("idSocio"));
                    
                    int r = objConexion.executeUpdate();
                    
                    if(r > 0){
                        objConexion.prepareStatement("SELECT auto.*, socio.nombre AS nombreSocio, socio.id AS idSocio FROM auto, socioauto, socio WHERE socioauto.idSocio=socio.id AND socioauto.idAuto=auto.id AND socio.id=(SELECT socioauto.idSocio AS idSocio FROM socioauto WHERE socioauto.idAuto=?)");
                        objConexion.ps.setString(1, request.getParameter("idSocio"));
                        objConexion.executeQuery();
                        Json j = new Json();
                        out.print( j.ListToArrayJson( objConexion.QueryToList() ) ); 
                    }

                    objConexion.Close();
                }else if("EliminarAuto".equals(request.getParameter("table"))){
                    Conexion objConexion = new Conexion();
                    
                    objConexion.prepareStatement("SELECT socioauto.idSocio AS idSocio FROM socioauto WHERE socioauto.idAuto=?");
                    objConexion.ps.setString(1, request.getParameter("id"));
                    objConexion.executeQuery();
                    int idSocio = Integer.parseInt(objConexion.QueryToList().get(0).get("idSocio"));
                    
                    objConexion.prepareStatement("DELETE FROM auto WHERE id=?;");
                    objConexion.ps.setString(1, request.getParameter("id"));
                    
                    int r = objConexion.executeUpdate();
                    
                    if(r > 0){
                        objConexion.prepareStatement("SELECT auto.*, socio.nombre AS nombreSocio, socio.id AS idSocio FROM auto, socioauto, socio WHERE socioauto.idSocio=socio.id AND socioauto.idAuto=auto.id AND socio.id=?");
                        objConexion.ps.setInt(1, idSocio);
                        objConexion.executeQuery();
                        Json j = new Json();
                        out.print( j.ListToArrayJson( objConexion.QueryToList() ) ); 
                    }

                    objConexion.Close();
                }
            }else if("Modificar socio".equals(request.getParameter("action"))){
                Conexion objConexion = new Conexion();           
                
                objConexion.prepareStatement("UPDATE `socio` SET `nombre`=?,`apellido`=?,`telefono`=?,`celular`=?,`email`=?,`rfc`=?,`especialidad`=?,`calle`=?,`colonia`=?,`cp`=?,`referencia`=?,`idEstado`=?,`idCiudad`=? WHERE id=?");
                
                objConexion.ps.setString(1, request.getParameter("nombre"));
                objConexion.ps.setString(2, request.getParameter("apellido"));
                objConexion.ps.setString(3, request.getParameter("telefono"));
                objConexion.ps.setString(4, request.getParameter("celular"));
                objConexion.ps.setString(5, request.getParameter("correo"));
                objConexion.ps.setString(6, request.getParameter("rfc"));
                objConexion.ps.setString(7, request.getParameter("especialidad"));
                objConexion.ps.setString(8, request.getParameter("calle"));
                objConexion.ps.setString(9, request.getParameter("colonia"));
                objConexion.ps.setString(10, request.getParameter("cp"));
                objConexion.ps.setString(11, request.getParameter("referencia"));
                objConexion.ps.setString(12, request.getParameter("estado"));
                objConexion.ps.setString(13, request.getParameter("ciudad"));
                objConexion.ps.setString(14, request.getParameter("id"));
                
                int r = objConexion.executeUpdate();
                if(r > 0){
                    objConexion.prepareStatement("SELECT socio.id, socio.nombre, socio.apellido, socio.telefono, socio.celular, socio.email, socio.rfc, socio.calle, socio.colonia, socio.cp, socio.referencia, especialidad.nombre AS especialidad, estado.nombre AS idEstado, ciudad.nombre AS idCiudad FROM socio, especialidad, estado, ciudad WHERE socio.especialidad=especialidad.id AND socio.idEstado=estado.id AND socio.idCiudad=ciudad.id;");
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
                objConexion.prepareStatement("SELECT id, nombre FROM ciudad WHERE idEstado="+request.getParameter("ciudad")+";");
                objConexion.executeQuery();
                Json j = new Json();
                out.print( j.ListToArrayJson( objConexion.QueryToList() ) );
                objConexion.Close();
            }else if("Guardar especialidad".equals(request.getParameter("action"))){
                Conexion objConexion = new Conexion();
                objConexion.prepareStatement("INSERT INTO especialidad(nombre) VALUES (?)");
                
                objConexion.ps.setString(1, request.getParameter("nombre"));
                
                int r = objConexion.executeUpdate();
                if(r > 0){
                    objConexion.prepareStatement("SELECT * FROM especialidad;");
                    objConexion.executeQuery();
                    Json j = new Json();
                    out.print( j.ListToArrayJson( objConexion.QueryToList() ) );
                }
                objConexion.Close();
            }else if("Modificar especialidad".equals(request.getParameter("action"))){
                Conexion objConexion = new Conexion();           
                
                objConexion.prepareStatement("UPDATE `especialidad` SET `nombre`=? WHERE id=?");
                
                objConexion.ps.setString(1, request.getParameter("nombre"));
                objConexion.ps.setString(2, request.getParameter("id"));                
                
                int r = objConexion.executeUpdate();
                if(r > 0){
                    objConexion.prepareStatement("SELECT * FROM especialidad;");
                    objConexion.executeQuery();
                    Json j = new Json();
                    out.print( j.ListToArrayJson( objConexion.QueryToList() ) );
                }
                objConexion.Close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(GetDataAssociate.class.getName()).log(Level.SEVERE, null, ex);
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
