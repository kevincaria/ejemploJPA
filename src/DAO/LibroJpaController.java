package DAO;

import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import entidad.Libro;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import util.JPAutil;

public class LibroJpaController implements Serializable {
    private final EntityManager em = JPAutil.getEntityManager();

    public void mostrarLibros(){
        em.getTransaction().begin();
        List<Libro> libros = em.createQuery("SELECT l FROM libros l",Libro.class).getResultList();
        em.getTransaction().commit();
        for (Libro libro : libros) {
            System.out.println(libro.toString());
        }
    }

    public void create(Libro libro) throws PreexistingEntityException, Exception {
        try {
            em.getTransaction().begin();
            em.persist(libro);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLibro(libro.getIsbn()) != null) {
                throw new PreexistingEntityException("Libro " + libro + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Libro libro) throws NonexistentEntityException, Exception {
        try {
            em.getTransaction().begin();
            libro = em.merge(libro);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = libro.getIsbn();
                if (findLibro(id) == null) {
                    throw new NonexistentEntityException("The libro with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Libro findLibro(Long id) {
        try {
            em.getTransaction().begin();
            return em.find(Libro.class, id);
        } finally {
            em.close();
        }
    }
     
    public Libro buscarLibroPorTitulo(String titulo){
        try{
            em.getTransaction().begin();
            return em.createQuery("SELECT l FROM libros l WHERE l.titulo= :titulo",Libro.class).setParameter("titulo", titulo).getSingleResult();
        }finally {
            em.close();
        }
    }
    
    public List<Libro> buscarLibroPorNombreAutor(String nombreAutor ){
        try{
            em.getTransaction().begin();
            return em.createQuery("SELECT l FROM libros l INNER JOIN autores a "
                    + "WHERE a.nombre = : nombreAutor", Libro.class).setParameter("nombreAutor", nombreAutor).getResultList();
        }finally {
            em.close();
        }
    }
    
     public List<Libro> buscarLibroPorNombreEditorial(String nombreEditorial ){
        try{
            em.getTransaction().begin();
            return em.createQuery("SELECT l FROM libros l INNER JOIN editoriales e "
                    + "WHERE e.nombre = : nombreEditorial", Libro.class).setParameter("nombreEditorial", nombreEditorial).getResultList();
        }finally {
            em.close();
        }
    }
    
}
