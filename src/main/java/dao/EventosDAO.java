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
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import pojo.Eventos;

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
   
    public List<Eventos> getToday(String date){
           
       
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-YYYY");
        String myDate = date;
        // Create date 17-04-2011 - 00h00
        Date minDate = null;
         try {
            minDate = formatter.parse(myDate);
        }catch (ParseException ex) {
           Logger.getLogger(EventosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
        // Create date 18-04-2011 - 00h00 
        // -> We take the 1st date and add it 1 day in millisecond thanks to a useful and not so known class
        Date maxDate = new Date(minDate.getTime() + TimeUnit.DAYS.toMillis(1));
        System.out.println(minDate);
        System.out.println(maxDate);
        System.out.println("____");
        try{
        List<Eventos> listaDeEventos = (List<Eventos>)session.createCriteria(Eventos.class)
                   .add(Restrictions.ge("StartDate", minDate ))
                   .add(Restrictions.lt("StartDate", maxDate ))
                   .list();
            System.out.println("return");
           return listaDeEventos;
       }catch(ClassCastException e){
           System.out.println("Valores vacios");
           System.out.println(e);
       }finally{
         // (Usuario)session.createCriteria(Usuario.class).add(Restrictions.eq("clave", clave)).uniqueResult();
       }
       return null;
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

