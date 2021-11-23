package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAutil {
    private static EntityManagerFactory emf = conect();

        private static EntityManagerFactory conect(){
            return Persistence.createEntityManagerFactory("ejerciciojpaPU");
        }

        public static EntityManager getEntityManager(){
            return emf.createEntityManager();
        }

}
