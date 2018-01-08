package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import pojo.Usuario;

@WebServlet(urlPatterns = {"/GetDataShop"})
public class GetDataShop extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            if("ComprarProductos".equals(request.getParameter("action"))){
                Json j = new Json();
                JSONArray array = j.ArrayJsonToList(request.getParameter("carrito"));
                if(array.length() < 1) return;

                Conexion objConexion = new Conexion();

                objConexion.prepareStatement("SELECT MAX(id) AS id FROM factura");
                objConexion.executeQuery();
                int idFactura = Integer.parseInt(objConexion.QueryToList().get(0).get("id")) + 1;

                String prepararPS = "SELECT SUM(valido) AS total FROM(";
                String actualizar = "UPDATE productos SET cantidadAlmacen = CASE id ";
                String ids = "(";
                double total = 0.0;
                String productosFactura  = "";
                for (int i = 0; i < array.length(); i++) {
                    prepararPS += "SELECT IF(cantidadAlmacen >= ?, 1, 0) as valido FROM productos WHERE id = ?";
                    actualizar += "WHEN ? THEN cantidadAlmacen-? ";
                    ids += "?" ;

                    total += Double.parseDouble( array.getJSONObject(i).get("precio").toString() ) * Double.parseDouble( array.getJSONObject(i).get("cantidad").toString() );

                    productosFactura += "( ?, ?, ?, ?, ?)";

                    if(array.length()-1 == i){
                        prepararPS += ") AS x";
                        ids += ")";
                    }else{
                        prepararPS += " UNION ALL ";
                        ids += ", ";
                        productosFactura += ",";
                    }
                }
                actualizar += "END WHERE id IN "+ids;

                objConexion.prepareStatement(prepararPS);
                int cont = 1;
                for (int i = 0; i < array.length(); i++) {
                    objConexion.ps.setString(cont, array.getJSONObject(i).get("cantidad").toString());
                    cont++;
                    objConexion.ps.setString(cont, array.getJSONObject(i).get("id").toString());
                    cont++;
                }
                objConexion.executeQuery();

                if(Integer.parseInt(  objConexion.QueryToList().get(0).get("total") ) == array.length()){
                    objConexion.prepareStatement(actualizar);
                    cont = 1;
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
                    objConexion.prepareStatement("UPDATE datosemisor SET dineroCaja=dineroCaja+? WHERE id=1;");
                    objConexion.ps.setDouble(1, Double.parseDouble(request.getParameter("ingreso")));
                    objConexion.executeUpdate();
                    objConexion.prepareStatement("SELECT dineroCaja FROM datosemisor WHERE id=1");
                    objConexion.executeQuery();
                    double dinero = Double.parseDouble(objConexion.QueryToList().get(0).get("dineroCaja"));
                    out.print("{\"correcto\": \"Venta realizada\", \"caja\": "+dinero+"}");
                }else{
                    out.print("{\"error\": \" No hay productos en el almacen\"}");
                    return;
                }
                
                objConexion.prepareStatement("INSERT INTO factura (id, total) VALUES (?, ?)");
                objConexion.ps.setInt(1, idFactura);
                if("auto".equals(request.getParameter("tipo")))
                    objConexion.ps.setDouble(2, 0.0);
                else
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
                
                HttpSession misession = (HttpSession) request.getSession(); 
                if("auto".equals(request.getParameter("tipo"))){
                    
                    double ingreso = Double.parseDouble(request.getParameter("ingreso")) - total;
                    objConexion.prepareStatement("UPDATE socio SET socioCuenta=socioCuenta+? WHERE id=?;");
                    objConexion.ps.setDouble(1, ingreso);
                    objConexion.ps.setString(2, request.getParameter("cliente"));
                    objConexion.executeUpdate();
                    
                    for (int i = 0; i < array.length(); i++) {
                        objConexion.prepareStatement("SELECT id FROM inventarioauto WHERE idAuto=? AND idProducto=?");
                        objConexion.ps.setString(1, request.getParameter("idAuto"));
                        objConexion.ps.setString(2, array.getJSONObject(i).get("id").toString());
                        objConexion.executeQuery();
                        List<Map<String, String>> list = objConexion.QueryToList();
                        if(list.size() > 0){
                            int almacen = Integer.parseInt(list.get(0).get("id"));
                            objConexion.prepareStatement("UPDATE inventarioauto SET cantidadAlmacenAuto=cantidadAlmacenAuto+? WHERE id=?; ");
                            objConexion.ps.setInt(1, (int) array.getJSONObject(i).get("cantidad"));
                            objConexion.ps.setInt(2, almacen);
                        }else{
                            objConexion.prepareStatement("INSERT INTO inventarioauto (idAuto, idProducto, cantidadAlmacenAuto) VALUES (?, ?, ?); ");
                            objConexion.ps.setString(1, request.getParameter("idAuto"));
                            objConexion.ps.setString(2, array.getJSONObject(i).get("id").toString());
                            objConexion.ps.setString(3, array.getJSONObject(i).get("cantidad").toString());                            
                        }
                        objConexion.executeUpdate();
                    }
                    
                    objConexion.prepareStatement("INSERT INTO venta (idFactura, idEmisor, IngresoEgreso, idUsuario, created_at) VALUES (?, ?, ?, ?, ?)");
                    objConexion.ps.setInt(1, idFactura);
                    objConexion.ps.setInt(2, 1);
                    objConexion.ps.setInt(3, 1);
                    
                    System.out.println(((Usuario) misession.getAttribute("usuario")).getIdUsuario());
                    objConexion.ps.setInt(4, ((Usuario) misession.getAttribute("usuario")).getIdUsuario());
                    objConexion.ps.setString(5, new SimpleDateFormat("YYYY/MM/dd HH:mm:ss").format(new Date()));
                    objConexion.executeUpdate();
                    
                }else if("cliente".equals(request.getParameter("tipo")) || "socio".equals(request.getParameter("tipo")) ){
                    
                    String cliente = ("cliente".equals(request.getParameter("tipo")))? "idCliente": "idSocio";
                    
                    double ingreso = Double.parseDouble(request.getParameter("ingreso")) - total;
	                    
                    if( "cliente".equals(request.getParameter("tipo")) ){
                        cliente = "idCliente";
                        objConexion.prepareStatement("UPDATE cliente SET clienteCuenta=clienteCuenta+? WHERE id=?;");
                        objConexion.ps.setDouble(1, ingreso);
                        objConexion.ps.setString(2, request.getParameter("cliente"));
                        objConexion.executeUpdate();
                    }else{
                        cliente = "idSocio";
                        objConexion.prepareStatement("UPDATE socio SET socioCuenta=socioCuenta+? WHERE id=?;");
                        objConexion.ps.setDouble(1, ingreso);
                        objConexion.ps.setString(2, request.getParameter("cliente"));
                        objConexion.executeUpdate();
                    }
                    
                    objConexion.prepareStatement("INSERT INTO venta (idFactura, idEmisor, "+cliente+", IngresoEgreso,idUsuario, created_at) VALUES (?, ?, ?, ?, ?, ?)");
                    objConexion.ps.setInt(1, idFactura);
                    objConexion.ps.setInt(2, 1);
                    objConexion.ps.setString(3, request.getParameter("cliente"));
                    objConexion.ps.setInt(4, 1);
                    System.out.println(((Usuario) misession.getAttribute("usuario")).getIdUsuario());
                    objConexion.ps.setInt(5, ((Usuario) misession.getAttribute("usuario")).getIdUsuario());
                    objConexion.ps.setString(6, new SimpleDateFormat("YYYY/MM/dd HH:mm:ss").format(new Date()));
                    
                    objConexion.executeUpdate();
                    
                }else{
                    objConexion.prepareStatement("INSERT INTO venta (idFactura, idEmisor, IngresoEgreso, idUsuario, created_at) VALUES (?, ?, ?, ?, ?)");
                    objConexion.ps.setInt(1, idFactura);
                    objConexion.ps.setInt(2, 1);
                    objConexion.ps.setInt(3, 1);
                    System.out.println(((Usuario) misession.getAttribute("usuario")).getIdUsuario());
                    objConexion.ps.setInt(4, ((Usuario) misession.getAttribute("usuario")).getIdUsuario());
                    objConexion.ps.setString(5, new SimpleDateFormat("YYYY/MM/dd HH:mm:ss").format(new Date()));
                    
                    objConexion.executeUpdate();
                    
                }
                objConexion.Close();
            }else if("actualizarProductos".equals(request.getParameter("action"))){
                Conexion objConexion = new Conexion();
                objConexion.prepareStatement("SELECT productos.id, productos.nombre, productos.numParte, productos.marca, productos.descripcion, productos.ubicacion, productos.unidad, productos.cantidadAlmacen, productos.codigoSat, productos.claveUnidadSat, productos.costo, productos.precioPublico, productos.precioSocio, productos.alertaPedido, categoriasproductos.nombre AS 'idCategoriasProductos'  FROM productos INNER JOIN categoriasproductos ON productos.idCategoriasProductos=categoriasproductos.id;");
                objConexion.executeQuery();
                Json j = new Json();
                out.print(j.ListToArrayJson(objConexion.QueryToList()));
                objConexion.Close();
            }else if("inputClients".equals(request.getParameter("action"))){
                Conexion objConexion = new Conexion();
                objConexion.prepareStatement("SELECT * FROM cliente WHERE LOWER(nombre) LIKE LOWER(?) OR LOWER(apellido) LIKE LOWER(?) OR LOWER(email) LIKE LOWER(?);");
                objConexion.ps.setString(1, "%"+request.getParameter("info")+"%");
                objConexion.ps.setString(2, "%"+request.getParameter("info")+"%");
                objConexion.ps.setString(3, "%"+request.getParameter("info")+"%");
                objConexion.executeQuery();
                Json j = new Json();
                out.print( j.ListToArrayJson(objConexion.QueryToList()) );
                objConexion.Close();
            }else if("inputParners".equals(request.getParameter("action"))){
                Conexion objConexion = new Conexion();
                objConexion.prepareStatement("SELECT * FROM socio WHERE LOWER(nombre) LIKE LOWER(?) OR LOWER(apellido) LIKE LOWER(?) OR LOWER(email) LIKE LOWER(?);");
                objConexion.ps.setString(1, "%"+request.getParameter("info")+"%");
                objConexion.ps.setString(2, "%"+request.getParameter("info")+"%");
                objConexion.ps.setString(3, "%"+request.getParameter("info")+"%");
                objConexion.executeQuery();
                Json j = new Json();
                out.print( j.ListToArrayJson(objConexion.QueryToList()) );
                objConexion.Close();
            }else if("ListAutos".equals(request.getParameter("action"))){
                Conexion objConexion = new Conexion();
                objConexion.prepareStatement("SELECT auto.* FROM socioauto, auto WHERE socioauto.idSocio=? AND socioauto.idAuto=auto.id");
                objConexion.ps.setString(1, request.getParameter("socio"));
                objConexion.executeQuery();
                Json j = new Json();
                out.print( j.ListToArrayJson(objConexion.QueryToList()) );
                objConexion.Close();
            };
        } catch (SQLException ex) {
            Logger.getLogger(GetDataShop.class.getName()).log(Level.SEVERE, null, ex);
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
