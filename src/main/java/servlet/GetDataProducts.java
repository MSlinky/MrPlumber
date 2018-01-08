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

@WebServlet(urlPatterns = {"/GetDataProducts"})
public class GetDataProducts extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            
            if("Guardar categoría".equals(request.getParameter("action"))){
                Conexion objConexion = new Conexion();
                objConexion.prepareStatement("INSERT INTO `categoriasproductos`(`nombre`, `descripcion`) VALUES "+
                        "(?,?)");
                objConexion.ps.setString(1, request.getParameter("nombre"));
                objConexion.ps.setString(2, request.getParameter("descripcion"));
                int r = objConexion.executeUpdate();
                if(r > 0){
                    objConexion.prepareStatement("SELECT * FROM categoriasproductos;");
                    objConexion.executeQuery();
                    Json j = new Json();
                    out.print( j.ListToArrayJson( objConexion.QueryToList() ) );
                }
                
                objConexion.Close();
            }else if("actualizarCategorias".equals(request.getParameter("action"))){
                Conexion objConexion = new Conexion();
                objConexion.prepareStatement("SELECT * FROM categoriasproductos;");
                objConexion.executeQuery();
                Json j = new Json();
                out.print( j.ListToArrayJson( objConexion.QueryToList() ) );
                objConexion.Close();
            }else if("gestionar".equals(request.getParameter("action"))){
                if("EliminarCategoria".equals(request.getParameter("table"))){
                    Conexion objConexion = new Conexion();
                    objConexion.prepareStatement("DELETE FROM `categoriasproductos` WHERE id=?");
                    objConexion.ps.setString(1, request.getParameter("id"));
                    int r = objConexion.executeUpdate();
                    out.print(r);
                    objConexion.CloseUpdate();
                }else if("ModificarCategoriaConsulta".equals(request.getParameter("table"))){
                    Conexion objConexion = new Conexion();     
                    objConexion.prepareStatement("SELECT * FROM categoriasproductos WHERE id=?");
                    objConexion.ps.setString(1, request.getParameter("id"));
                    objConexion.executeQuery();
                    Json j = new Json();
                    out.print( j.ListToArrayJson( objConexion.QueryToList() ) );
                    objConexion.Close();
                }else if("EliminarProducto".equals(request.getParameter("table"))){
                    Conexion objConexion = new Conexion();
                    objConexion.prepareStatement("DELETE FROM `productos` WHERE id=?");
                    objConexion.ps.setString(1, request.getParameter("id"));
                    int r = objConexion.executeUpdate();
                    out.print(r);
                    objConexion.CloseUpdate();
                }else if("ModificarProductoConsulta".equals(request.getParameter("table"))){
                    Conexion objConexion = new Conexion();     
                    objConexion.prepareStatement("SELECT * FROM productos WHERE id=?");
                    objConexion.ps.setString(1, request.getParameter("id"));
                    objConexion.executeQuery();
                    Json j = new Json();
                    out.print( j.ListToArrayJson( objConexion.QueryToList() ) );
                    objConexion.Close();
                }
            }else if("Modificar categoría".equals(request.getParameter("action"))){
                Conexion objConexion = new Conexion();                
                objConexion.prepareStatement("UPDATE `categoriasproductos` SET `nombre`=?,`descripcion`=? WHERE id=?");
                objConexion.ps.setString(1, request.getParameter("nombre"));
                objConexion.ps.setString(2, request.getParameter("descripcion"));
                objConexion.ps.setString(3, request.getParameter("id"));
                int r = objConexion.executeUpdate();
                if(r > 0){
                    objConexion.prepareStatement("SELECT * FROM categoriasproductos;");
                    objConexion.executeQuery();
                    Json j = new Json();
                    out.print( j.ListToArrayJson( objConexion.QueryToList() ) );
                }
                objConexion.Close();
            }else if("Guardar producto".equals(request.getParameter("action"))){
                Conexion objConexion = new Conexion();
                
                objConexion.prepareStatement("INSERT INTO productos(nombre,codigoSAT,claveUnidadSAT,"
                        + "idCategoriasProductos,numParte,marca,descripcion,ubicacion,unidad,cantidadAlmacen,"
                        + "costo,precioPublico,precioSocio) VALUES "+
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?);");
                
                objConexion.ps.setString(1, request.getParameter("nombre"));
                objConexion.ps.setString(2, request.getParameter("codigoSAT"));
                objConexion.ps.setString(3, request.getParameter("claveUnidadSAT"));
                objConexion.ps.setString(4, request.getParameter("categoria"));
                objConexion.ps.setString(5, request.getParameter("numeroParte"));
                objConexion.ps.setString(6, request.getParameter("marca"));
                objConexion.ps.setString(7, request.getParameter("descripcion"));
                objConexion.ps.setString(8, request.getParameter("ubicacion"));
                objConexion.ps.setString(9, request.getParameter("unidad"));
                objConexion.ps.setString(10, request.getParameter("cantidadAlmacen"));
                objConexion.ps.setString(11, request.getParameter("costo"));
                objConexion.ps.setString(12, request.getParameter("precioPublico"));
                objConexion.ps.setString(13, request.getParameter("precioSocio"));
                
                int r = objConexion.executeUpdate();

                if(r > 0){
                    objConexion.prepareStatement("SELECT * FROM productos;");
                    objConexion.executeQuery();
                    Json j = new Json();
                    out.print( j.ListToArrayJson( objConexion.QueryToList() ) );
                }
                
                objConexion.Close();
            }else if("actualizarProductos".equals(request.getParameter("action"))){
                Conexion objConexion = new Conexion();
                objConexion.prepareStatement("SELECT productos.id, productos.nombre, productos.numParte, productos.marca, productos.descripcion, productos.ubicacion, productos.unidad, productos.cantidadAlmacen, productos.codigoSat, productos.claveUnidadSat, productos.costo, productos.precioPublico, productos.precioSocio, productos.alertaPedido, categoriasproductos.nombre AS 'idCategoriasProductos'  FROM productos INNER JOIN categoriasproductos ON productos.idCategoriasProductos=categoriasproductos.id;");
                objConexion.executeQuery();
                Json j = new Json();
                out.print( j.ListToArrayJson(objConexion.QueryToList()) );
                objConexion.Close();
            }else if("Modificar producto".equals(request.getParameter("action"))){
                Conexion objConexion = new Conexion();                

                objConexion.prepareStatement("UPDATE `productos` SET `nombre`=?,codigoSAT=?,claveUnidadSAT=?,idCategoriasProductos=?,numParte=?,marca=?,descripcion=?,ubicacion=?,unidad=?,cantidadAlmacen=?,costo=?,precioPublico=?,precioSocio=? WHERE id=?");
                
                objConexion.ps.setString(1, request.getParameter("nombre"));
                objConexion.ps.setString(2, request.getParameter("codigoSAT"));
                objConexion.ps.setString(3, request.getParameter("claveUnidadSAT"));
                objConexion.ps.setString(4, request.getParameter("categoria"));
                objConexion.ps.setString(5, request.getParameter("numeroParte"));
                objConexion.ps.setString(6, request.getParameter("marca"));
                objConexion.ps.setString(7, request.getParameter("descripcion"));
                objConexion.ps.setString(8, request.getParameter("ubicacion"));
                objConexion.ps.setString(9, request.getParameter("unidad"));
                objConexion.ps.setString(10, request.getParameter("cantidadAlmacen"));
                objConexion.ps.setString(11, request.getParameter("costo"));
                objConexion.ps.setString(12, request.getParameter("precioPublico"));
                objConexion.ps.setString(13, request.getParameter("precioSocio"));
                objConexion.ps.setString(14, request.getParameter("id"));
                
                int r = objConexion.executeUpdate();
                if(r > 0){
                    objConexion.prepareStatement("SELECT productos.id, productos.nombre, productos.numParte, productos.marca, productos.descripcion, productos.ubicacion, productos.unidad, productos.cantidadAlmacen, productos.codigoSat, productos.claveUnidadSat, productos.costo, productos.precioPublico, productos.precioSocio, productos.alertaPedido, categoriasproductos.nombre AS 'idCategoriasProductos'  FROM productos INNER JOIN categoriasproductos ON productos.idCategoriasProductos=categoriasproductos.id;");
                    objConexion.executeQuery();
                    Json j = new Json();
                    out.print( j.ListToArrayJson( objConexion.QueryToList() ) );
                }
                objConexion.Close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(GetDataProducts.class.getName()).log(Level.SEVERE, null, ex);
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
