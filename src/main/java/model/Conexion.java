package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion{ 
    
    /*private static final String DRIVER = "com.mysql.jdbc.Driver";     
    private static final String URL = "jdbc:mysql://localhost:3306/mrplumer"; 
    private static final String USERNAME = "root";   
    private static final String PASSWORD = "";*/
    
    private static final String DRIVER = "com.mysql.jdbc.Driver";     
    private static final String URL = "jdbc:mysql://a07yd3a6okcidwap.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/zbxv4rd7u4k5zprr"; 
    private static final String USERNAME = "b3vnhp4zvi06sw8j";   
    private static final String PASSWORD = "m6m3bx9efoerag9h";
    
    /*private static final String DRIVER = "com.mysql.jdbc.Driver";     
    private static final String URL = "jdbc:mysql://t89yihg12rw77y6f.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/t836uopkeinehoia"; 
    private static final String USERNAME = "b05z1rvhwv35tbif";   
    private static final String PASSWORD = "nyhpok6fe7yck1ya";*/
    
    public String Error = "";
    public Connection connection = null;
    public PreparedStatement ps;
    private ResultSet rs = null;
    
    private static Connection getConnection() throws SQLException{   
        try {   
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL+"?user="+USERNAME+"&password="+PASSWORD+"&zeroDateTimeBehavior=convertToNull");
        } catch (ClassNotFoundException ex) {   
            System.out.println("Where is your SQL JDBC Driver? "   
            + "Include in your library path!");   
            return null;   
        }
    }

    public Conexion(){     
        try {
            connection = getConnection();   
        } catch (SQLException ex) {
            Error = "Connection Failed! Check output console";
        }   
        if (connection != null) {
            Error = "You made it, take control your database now!";
        } else {
            Error = "Failed to make connection!";
        }
    }
    
    public void CloseUpdate(){
        try {
            connection.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void Close(){
        try {
            connection.close();
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Map<String, String>> QueryToList(){
        List<Map<String, String>> list = new ArrayList<>();
        try {
            while(rs.next()){
                Map<String, String> options = new LinkedHashMap<>();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    options.put(rs.getMetaData().getColumnLabel(i), rs.getString(i));
                }
                list.add(options);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void prepareStatement(String query){
        try {
            ps = connection.prepareStatement(query);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void executeQuery(){        
        try {
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int executeUpdate(){        
        try {
            int rs = ps.executeUpdate();
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
