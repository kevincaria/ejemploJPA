/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import DAO.exceptions.NonexistentEntityException;
import entidad.Autor;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import util.JPAutil;

public class AutorJpaController implements Serializable {
    
private final EntityManager em = JPAutil.getEntityManager();
     
    public void mostrarAutores(){
        em.getTransaction().begin();
        List<Autor> autores = em.createQuery("SELECT a FROM autores a", Autor.class).getResultList();
        em.getTransaction().commit();
        for (Autor autor : autores) {
            System.out.println(autor.toString());
        }
    }
    
    public void create(Autor autor) {
        try {
            em.getTransaction().begin();
            em.persist(autor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Autor autor) throws NonexistentEntityException, Exception {
        try {
            em.getTransaction().begin();
            autor = em.merge(autor);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = autor.getId();
                if (findAutor(id) == null) {
                    throw new NonexistentEntityException("The autor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public Autor findAutorName(String nombre){
        try{
            em.getTransaction().begin();
            return em.createQuery("SELECT a FROM autores a WHERE a.nombre= :nombre",Autor.class).setParameter("nombre", nombre).getSingleResult();
        }finally {
            em.close();
        }
    }
    
    public Autor findAutor(Long id) {
        try {
            em.getTransaction().begin();
            return em.find(Autor.class, id);
        } finally {
            em.close();
        }
    }
}
