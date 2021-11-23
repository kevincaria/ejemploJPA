/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejerciciojpa;
import DAO.AutorJpaController;
import javax.persistence.EntityManager;
import service.AutorService;
import service.EditorialService;
import service.LibreriaService;
import service.LibroService;
import util.JPAutil;

public class Ejerciciojpa {

    public static void main(String[] args) {
        EntityManager emf = JPAutil.getEntityManager();
        LibroService ls = new LibroService();
        AutorService as = new AutorService();
        EditorialService es = new EditorialService();
        LibreriaService lis = new LibreriaService();
        AutorJpaController ajc = new AutorJpaController();
        
       try {
            lis.menu();
       } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
