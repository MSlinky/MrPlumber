/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hbm;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author marcocameros
 */
public class NewHibernateUtil {

    private static final SessionFactory sessionFactory;
    private static final ThreadLocal localSession;
    
    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            
           Configuration config = new Configuration();
           
            config.configure("hibernate.cfg.xml");
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
            applySettings(config.getProperties());
            
            sessionFactory = config.buildSessionFactory(builder.build());
        } catch (HibernateException ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        localSession = new ThreadLocal();
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static Session getLocalSession(){
        Session session = (Session) localSession.get();
        System.out.println("___"+session);
        if(session == null){
            session = sessionFactory.openSession();
            System.out.println("___"+session);
            localSession.set(session);
            System.out.println("___"+(Session) localSession.get());
            System.out.println("New sesion iniciada");
        }
        return session;
    }
   /* public static Session getLocalSession(){
        Session session = (Session) localSession.get();
        if(session == null) {
            session = sessionFactory.getCurrentSession();
            localSession.set(session);
            System.out.println("Sesion iniciada ");
        }
        return session;
    }*/
    public static void closeLocalSession() {
         /**
          * Retorna la session 
          */
        Session session = (Session) localSession.get();
        
        /**
         * Si la session no es nula entonces la cierra
         */
        System.out.println("___"+session);
        if (session != null){
            System.out.println("Cerro");
            session.close();
        }
        //Destruye el objeto
        localSession.set(null);
        //Imprime un mensaje que la session se cerro
        System.out.println("sesion cerrada\n");
    }
}
