/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import hbm.NewHibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
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

