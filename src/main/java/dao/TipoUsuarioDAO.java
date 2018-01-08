/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import hbm.NewHibernateUtil;
import org.hibernate.Session;

/**
 *
 * @author marcocameros
 */
public class TipoUsuarioDAO {
    Session session;
    public TipoUsuarioDAO() {
        // Se Almacena la session en una variable del objeto PersonDAO
        session = NewHibernateUtil.getLocalSession();
    }
    
}
