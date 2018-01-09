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
import org.hibernate.criterion.Restrictions;
import pojo.Usuario;

/**
 *
 * @author marcocameros
 */
public class UsuarioDAO {
    Session session;
    
    public UsuarioDAO(){
        // Se Almacena la session en una variable del objeto PersonDAO
        session=NewHibernateUtil.getLocalSession();
    }
    public UsuarioDAO(Session session){
        // Se Almacena la session en una variable del objeto PersonDAO
        this.session=session;
    }
    public List<Usuario> getAllUsuario(){
        //Retorna un objeto persona que tenga el id que mandamos en los parametros
        
        try{
            List <Usuario> usr = (List <Usuario>)session.createCriteria(Usuario.class).list();
            return usr;
        }
        catch(ClassCastException e){
            
           System.out.println("Valores vacios");
           System.out.println(e);
        }return null;
    }  
    
    public Usuario getUsuarioById(int id){
        //Retorna un objeto persona que tenga el id que mandamos en los parametros
        return (Usuario)session.load(Usuario.class,id);
    }
    public Usuario getUsuarioByCorreo(String correo){
        return  (Usuario)session.createCriteria(Usuario.class).add(Restrictions.eq("correo", correo)).uniqueResult();
    }
    public Usuario getUsuarioByClave(String clave){
        return (Usuario)session.createCriteria(Usuario.class).add(Restrictions.eq("clave", clave)).uniqueResult();
    }
    public  boolean isCorreo(String correo){
        try{
            Usuario usuario=(Usuario)session.createCriteria(Usuario.class).add(Restrictions.eq("correo", correo)).uniqueResult();
            return true;
         }catch(NullPointerException e){
             return false;
         }      
    }
    public boolean saveEvento(String correo,String clave){
       Usuario usuario = new Usuario();
       usuario.setCorreo(correo);
       usuario.setClave(clave);
       
       try{
            //Intentara iniciar una transaction de no ser posible procedera en el catch
            Transaction transaccion=session.beginTransaction();
            //Almacenara mi objeto personaDeTanque en la base de datos
            session.save(usuario);
            //Actualizara a mis sessiones que la base de datos fue actualizada
            transaccion.commit();
            return true;
        }catch(Exception e){
            
        }finally{
            
        }
        return false;
   }
}
