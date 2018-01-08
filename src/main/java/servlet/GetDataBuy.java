package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Conexion;
import model.Json;
import org.json.JSONArray;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import pojo.Usuario;

@WebServlet(urlPatterns = {"/GetDataBuy"})
public class GetDataBuy extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            if("Egresos".equals(request.getParameter("action"))){
                Json j = new Json();
                JSONArray array = j.ArrayJsonToList(request.getParameter("carrito"));
                if(array.length() < 1) return;
                
                Conexion objConexion = new Conexion();
                
                objConexion.prepareStatement("SELECT MAX(id) AS id FROM factura");
                objConexion.executeQuery();
                int idFactura = Integer.parseInt(objConexion.QueryToList().get(0).get("id")) + 1;
                
                String actualizar = "UPDATE productos SET cantidadAlmacen = CASE id ";
                String ids = "(";
                String select = "SELECT costo FROM productos WHERE ";
                double total = 0.0;
                String productosFactura  = "";
                for (int i = 0; i < array.length(); i++) {
                    actualizar += "WHEN ? THEN cantidadAlmacen+? ";
                    select += " id="+array.getJSONObject(i).get("id").toString()+"||";
                    ids += "?" ;
                    total += Double.parseDouble( array.getJSONObject(i).get("precio").toString() ) * Double.parseDouble( array.getJSONObject(i).get("cantidad").toString() );
                    productosFactura += "( ?, ?, ?, ?, ?)";
                    if(array.length()-1 == i){
                        ids += ")";
                    }else{
                        ids += ", ";
                        productosFactura += ",";
                    }
                    
                }
                actualizar += "END WHERE id IN "+ids;
                select += "id=0;";
                
                objConexion.prepareStatement(actualizar);
                int cont = 1;
                for (int i = 0; i < array.length(); i++) {
                    objConexion.ps.setString(cont, array.getJSONObject(i).get("id").toString());
                    cont++;
                    objConexion.ps.setInt(cont, (int) array.getJSONObject(i).get("cantidad"));
                    cont++;
                }
                
                for (int i = 0; i < array.length(); i++) {
                    objConexion.ps.setString(cont, array.getJSONObject(i).get("id").toString());
                    cont++;
                }
                objConexion.executeUpdate();
                objConexion.prepareStatement("UPDATE datosemisor SET dineroCaja=dineroCaja-? WHERE id=1;");
                objConexion.ps.setDouble(1, total);
                objConexion.executeUpdate();
                objConexion.prepareStatement("SELECT dineroCaja FROM datosemisor WHERE id=1");
                objConexion.executeQuery();
                double dinero = Double.parseDouble(objConexion.QueryToList().get(0).get("dineroCaja"));
                out.print("{\"correcto\": \"Venta realizada\", \"caja\": "+dinero+"}");
                
                objConexion.prepareStatement("INSERT INTO factura (id, total) VALUES (?, ?)");
                objConexion.ps.setInt(1, idFactura);
                objConexion.ps.setDouble(2, total);
                objConexion.executeUpdate();
                
                objConexion.prepareStatement("INSERT INTO productosfacturados (nombre, descripcion, idFactura, precio, cantidad) VALUES " + productosFactura);
                cont = 1;
                for (int i = 0; i < array.length(); i++) {
                    objConexion.ps.setString(cont, array.getJSONObject(i).get("nombre").toString());
                    cont++;
                    objConexion.ps.setString(cont, array.getJSONObject(i).get("descripcion").toString());
                    cont++;
                    objConexion.ps.setInt(cont, idFactura);
                    cont++;
                    objConexion.ps.setString(cont, array.getJSONObject(i).get("precio").toString());
                    cont++;
                    objConexion.ps.setString(cont, array.getJSONObject(i).get("cantidad").toString());
                    cont++;
                }
                objConexion.executeUpdate();
                
        
                objConexion.prepareStatement("INSERT INTO venta (idFactura, idEmisor, IngresoEgreso, created_at) VALUES (?, ?, ?, ?, ?)");
                objConexion.ps.setInt(1, idFactura);
                objConexion.ps.setInt(2, 1);
                objConexion.ps.setInt(3, 0);
                objConexion.ps.setString(4, new SimpleDateFormat("YYYY/MM/dd HH:mm:ss").format(new Date()));
                objConexion.executeUpdate();
                
                objConexion.Close();
            }else if("actualizarProductos".equals(request.getParameter("action"))){
                Conexion objConexion = new Conexion();
                objConexion.prepareStatement("SELECT productos.id, productos.nombre, productos.numParte, productos.marca, productos.descripcion, productos.ubicacion, productos.unidad, productos.cantidadAlmacen, productos.codigoSat, productos.claveUnidadSat, productos.costo, productos.precioPublico, productos.precioSocio, productos.alertaPedido, categoriasproductos.nombre AS 'idCategoriasProductos'  FROM productos INNER JOIN categoriasproductos ON productos.idCategoriasProductos=categoriasproductos.id;");
                objConexion.executeQuery();
                Json j = new Json();
                out.print(j.ListToArrayJson(objConexion.QueryToList()));
                objConexion.Close();
            }          
        } catch (SQLException ex) {
            Logger.getLogger(GetDataBuy.class.getName()).log(Level.SEVERE, null, ex);
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
