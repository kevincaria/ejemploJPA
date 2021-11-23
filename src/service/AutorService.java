/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import DAO.AutorJpaController;
import entidad.Autor;
import java.util.Scanner;

public class AutorService {
    Scanner read = new Scanner(System.in).useDelimiter("\n");
    
    AutorJpaController ajc = new AutorJpaController();
    
    private Autor crearAutor(){
        System.out.println("Ingrese el nombre del Autor:");
        String nombre = read.next();
        return new Autor(nombre,true);
    }
    
    public Autor seleccionarAutor(){
        Autor elegido = null;
        System.out.println("Elija un autor cargado por ID, caso contrario oprima 0(cero)");
        ajc.mostrarAutores();
        Long opcion = read.nextLong();
        if(opcion==0){
            elegido = crearAutor();
            ajc.create(elegido);
        }else{
            elegido = ajc.findAutor(opcion);
        }
        return elegido;
    }
    
    private void eliminarAutor() throws Exception{
        ajc.mostrarAutores();
        System.out.println("Ingrese el ID de la autor a eliminar:");
        Long id = read.nextLong();
        Autor eliminada= ajc.findAutor(id);
        eliminada.setAlta(false);
        ajc.edit(eliminada);
    }

    private void buscarAutor(){
        Autor autor = null;
        System.out.println("Como desea buscar al autor?");
        System.out.println("1.Por ID");
        System.out.println("2.Por nombre");
        int opcion = read.nextInt();
        switch (opcion) {
            case 1:
                autor = buscarAutorPorID();
                mostrarAutor(autor);
                break;
            case 2:
                autor = buscarAutorPorNombre();
                mostrarAutor(autor);
                break;
        }
    }

    private void mostrarAutor(Autor autor) {
        System.out.println(autor.toString());
    }

    private Autor buscarAutorPorID(){
        System.out.println("Ingrese el ID del autor a buscar");
        Long id = read.nextLong();
        return ajc.findAutor(id);
    }
    
    private Autor buscarAutorPorNombre(){
        System.out.println("Ingrese el nombre del autor a buscar");
        String nombre = read.next();
        return ajc.findAutorName(nombre);
    }

    public void menuAutor() throws Exception{
        LibreriaService lis = new LibreriaService();
        Autor nuevo = null;
        int op;
        int op2 = 0;

        do {
            System.out.println("");
            System.out.println("AUTORES");
            System.out.println("MENU");
            System.out.println("1.Cargar autor nuevo");
            System.out.println("2.Mostrar autores");
            System.out.println("3.Buscar autores");
            System.out.println("4.Modificar autores");
            System.out.println("5.Eliminar autores");
            System.out.println("6.Volver al menu anterior");
            System.out.println("Ingrese una opcion: ");

            op = read.nextInt();
            while (op < 0 || op > 7) {
                System.out.println("Opcion incorrecta, elija la opcion entre 1 y 6");
                op = read.nextInt();
            }

            switch (op) {
                case 1:
                    nuevo = crearAutor();
                    ajc.create(nuevo);
                    break;
                case 2:
                    ajc.mostrarAutores();
                    break;
                case 3:
                    buscarAutor();
                    break;
                case 4:
                    modificarAutores();
                    break;
                case 5:
                    eliminarAutor();
                    break;
                case 6:
                    lis.menu();
                    break;
            }
        } while (op2 != 1);
    }

    private void modificarAutores() throws Exception{
        Autor aModificar = buscarAutorPorID();
        System.out.println("Ingrese el nombre del autor: ");
        String nombreNuevo = read.next();
        aModificar.setNombre(nombreNuevo);
        ajc.edit(aModificar);
        System.out.println("Autor Modificado !");
    }
    

}
