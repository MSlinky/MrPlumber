/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import hbm.NewHibernateUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Json;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import pojo.Eventos;
//lo que sea
/**
 *
 * @author marcocameros
 */
public class EventosDAO {
   Session session;
   
   public EventosDAO(){
       session = NewHibernateUtil.getLocalSession();
   }
   public EventosDAO(Session session){
       this.session = session;
   }
   public List<Eventos> getAll(){
           
       //Transaction tx = session.beginTransaction();  
       try{
           List<Eventos> listaDeEventos = (List<Eventos>)session.createCriteria(Eventos.class).list();
           return listaDeEventos;
       }catch(ClassCastException e){
           System.out.println("Valores vacios");
           System.out.println(e);
       }finally{
         // tx.commit();
       }
       return null;
   }
   
    public String getToday(String date){
        
        String query = "SELECT * FROM zbxv4rd7u4k5zprr.Eventos WHERE StartDate LIKE '"+ date +"%'";
        System.out.println(query);
        SQLQuery sql = session.createSQLQuery(query);
        
        Json j = new Json();
        return j.ListToArrayJson( sql.list());
   }
   
   public boolean saveEvento(int id,String name,String location,String text,String startDate,String endDate){
       Eventos evento = new Eventos();
       evento.setIdEventos(id);
       evento.setLocation(location);
       evento.setName(name);
       evento.setText(text);
       evento.setStartdate(startDate);
       evento.setEnddate(endDate);
       
       try{
            //Intentara iniciar una transaction de no ser posible procedera en el catch
            Transaction transaccion=session.beginTransaction();
            //Almacenara mi objeto personaDeTanque en la base de datos
            session.save(evento);
            //Actualizara a mis sessiones que la base de datos fue actualizada
            transaccion.commit();
            return true;
        }catch(Exception e){
            
        }finally{
            
        }
        return false;
   }
   
   
}

