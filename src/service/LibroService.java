package service;

import entidad.Autor;
import entidad.Editorial;
import entidad.Libro;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import DAO.LibroJpaController;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/


public class LibroService {
    Scanner read = new Scanner(System.in).useDelimiter("\n");

    AutorService as = new AutorService();
    EditorialService es = new EditorialService();
    LibroJpaController ljc = new LibroJpaController();
    LibreriaService lis = new LibreriaService();
    
    public void menuLibros() throws PreexistingEntityException, Exception{
        Libro nuevo = null;
        int op;
        int op2 = 0;

        do {
            System.out.println("");
            System.out.println("LIBROS");
            System.out.println("MENU");
            System.out.println("1.Cargar libro nuevo");
            System.out.println("2.Mostrar libros");
            System.out.println("3.Buscar libros");
            System.out.println("4.Modificar libros");
            System.out.println("5.Eliminar libros");
            System.out.println("6.Volver al menu anterior");
            System.out.println("Ingrese una opcion: ");

            op = read.nextInt();
            while (op < 0 || op > 7) {
                System.out.println("Opcion incorrecta, elija la opcion entre 1 y 6");
                op = read.nextInt();
            }

            switch (op) {
                case 1:
                    nuevo = crearLibro();
                    ljc.create(nuevo);
                    break;
                case 2:
                    ljc.mostrarLibros();
                    break;
                case 3:
                    buscarLibros();
                    break;
                case 4:
                    modificarLibros();
                    break;
                case 5:
                    eliminarLibro();
                    break;
                case 6:
                    lis.menu();
                    break;
            }
        } while (op2 != 1);
    }

    private void modificarLibros() throws Exception{
        System.out.println("Que desea modificar de los Libros?");
        System.out.println("1.Titulo");
        System.out.println("2.Año");
        System.out.println("3.Cantidad de ejemplares");
        System.out.println("4.Autor");
        System.out.println("5.Editorial");
        System.out.println("6.Volver al menu anterior");
        int opcion = read.nextInt();

        while (opcion < 0 || opcion > 7) {
            System.out.println("Opcion incorrecta, elija la opcion entre 1 y 4");
            opcion = read.nextInt();
        }

        switch (opcion) {
            case 1:
                modificarTitulo();
                break;
            case 2:
                modificarAnio();
                break;
            case 3:
                modificarCantidadEjemplares();
                break;
            case 4:
                modificarAutor();
                break;
            case 5:
                modificarEditorial();
            case 6:
                menuLibros();
                break;
        }
    }

    private void buscarLibros() throws Exception{
        System.out.println("Como desea buscar los Libros?");
        System.out.println("1.Por ISBN");
        System.out.println("2.Por Titulo");
        System.out.println("3.Por Autor");
        System.out.println("4.Por Editorial");
        System.out.println("5.Volver al menu anterior");
        int opcion = read.nextInt();
        while (opcion < 0 || opcion > 6) {
            System.out.println("Opcion incorrecta, elija la opcion entre 1 y 4");
            opcion = read.nextInt();
        }

        switch (opcion) {
            case 1:
                buscarLibroPorISBN();
                break;
            case 2:
                buscarLibroPorTitulo();
                break;
            case 3:
                buscarLibrosPorAutor();
                break;
            case 4:
                buscarLibrosPorEditorial();
                break;
            case 5:
                menuLibros();
                break;
        }
    }

    private Libro crearLibro(){
        System.out.println("Ingrese el ISBN del libro:");
        Long isbn = read.nextLong();
        System.out.println("Ingrese el titulo del Libro:");
        String nombre = read.next();
        System.out.println("Ingrese el año del libro:");
        Integer anio = read.nextInt();
        System.out.println("Ingrese la cantidad de ejemplares:");
        Integer ejemplares = read.nextInt();
        Autor autorElegido = as.seleccionarAutor();
        Editorial editorElegido = es.seleccionarEditorial();
        return new Libro(isbn,nombre,anio,ejemplares,true,autorElegido,editorElegido);
    }

    private void eliminarLibro() throws NonexistentEntityException, Exception{
        Libro aModificar = libroAModificar();
        aModificar.setAlta(false);
        ljc.edit(aModificar);
    }
    
    private void buscarLibroPorISBN(){
        System.out.println("Ingrese el ISBN del libro a buscar");
        Long isbn = read.nextLong();
        Libro encontrado = ljc.findLibro(isbn);
        System.out.println(encontrado.toString());
    }
    
    private void buscarLibroPorTitulo(){
        System.out.println("Ingrese el nombre de la editorial a buscar");
        String titulo = read.next();
        Libro encontrado = ljc.buscarLibroPorTitulo(titulo);
        System.out.println(encontrado.toString());
    }

    private void buscarLibrosPorAutor(){
        System.out.println("Ingrese el nombre del autor");
        String autor = read.next();
        List<Libro> libros = ljc.buscarLibroPorNombreAutor(autor);
        mostrarLibrosEncontrados(libros);
    }

    private void buscarLibrosPorEditorial(){
        System.out.println("Ingrese el nombre de la editorial");
        String editorial = read.next();
        List<Libro> libros = ljc.buscarLibroPorNombreEditorial(editorial);
        mostrarLibrosEncontrados(libros);
    }

    private void modificarTitulo() throws Exception {
        Libro aModificar = libroAModificar();
        System.out.println("Ingrese el nuevo titulo");
        aModificar.setTitulo(read.next());
        ljc.edit(aModificar);
    }

    private void modificarAnio() throws Exception{
        Libro aModificar = libroAModificar();
        System.out.println("Ingrese el nuevo año");
        aModificar.setAnio(read.nextInt());
        ljc.edit(aModificar);
    }

    private void modificarCantidadEjemplares() throws Exception{
        Libro aModificar = libroAModificar();
        System.out.println("Ingrese la nueva cantidad de ejemplares: ");
        aModificar.setAnio(read.nextInt());
        ljc.edit(aModificar);
    }

    private void modificarAutor() throws Exception{
        Libro aModificar = libroAModificar();
        Autor autorNuevo = as.seleccionarAutor();
        aModificar.setAutor(autorNuevo);
        ljc.edit(aModificar);
    }

    private void modificarEditorial() throws Exception{
        Libro aModificar = libroAModificar();
        Editorial editorialNueva = es.seleccionarEditorial();
        aModificar.setEditorial(editorialNueva);
        ljc.edit(aModificar);
    }

    private Libro libroAModificar(){
        ljc.mostrarLibros();
        System.out.println("Ingrese el ISBN del libro:");
        Long id = read.nextLong();
        return ljc.findLibro(id);
    }
    
    private void mostrarLibrosEncontrados(List<Libro> libros) {
        for (Libro libro : libros) {
            System.out.println(libro.toString());
        }
    }
}
