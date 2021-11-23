package DAO;

import DAO.exceptions.NonexistentEntityException;
import entidad.Editorial;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import util.JPAutil;

public class EditorialJpaController implements Serializable {
    private final EntityManager em = JPAutil.getEntityManager();

    public void mostrarEditoriales(){
        try {
            em.getTransaction().begin();
            List<Editorial> editoriales = em.createQuery("SELECT e FROM editoriales e", Editorial.class).getResultList();
            em.getTransaction().commit();
            for (Editorial editorial : editoriales) {
                System.out.println(editorial.toString());
            }
        } finally {
            em.close();
        }
    }

    public void create(Editorial editorial) {
        try {
            em.getTransaction().begin();
            em.persist(editorial);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    
    public void edit(Editorial editorial) throws NonexistentEntityException, Exception {
        try {
            em.getTransaction().begin();
            editorial = em.merge(editorial);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = editorial.getId();
                if (findEditorial(id) == null) {
                    throw new NonexistentEntityException("The editorial with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Editorial findEditorial(Long id) {
        try {
            em.getTransaction().begin();
            return em.find(Editorial.class, id);
        } finally {
            em.close();
        }
    }

    public Editorial findEditorialName(String nombre){
        try{
            em.getTransaction().begin();
            return em.createQuery("SELECT a FROM autores a WHERE a.nombre= :nombre",Editorial.class).setParameter("nombre", nombre).getSingleResult();
        }finally {
            em.close();
        }
    }

}
