/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dao.UsuarioDAO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pojo.Usuario;

/**
 *
 * @author marcocameros
 */
public class Prueba {
    
    public Prueba() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void hello() {
     
         UsuarioDAO personita=new UsuarioDAO();
         Usuario personaProNuevoPablo=new Usuario();
         //System.out.println(personita.getPersonaByName("marco"));
         //personaProNuevoPablo.setNombre("Marco");
         //personita.updateById(4, personaProNuevoPablo);
         System.out.println(personita.getUsuarioById(1).getClave());
     }
}
