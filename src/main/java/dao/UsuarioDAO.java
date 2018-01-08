/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import hbm.NewHibernateUtil;
import org.hibernate.Session;
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
}
