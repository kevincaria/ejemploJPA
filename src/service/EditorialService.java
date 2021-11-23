/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import entidad.Editorial;
import java.util.Scanner;

import DAO.EditorialJpaController;
import DAO.exceptions.NonexistentEntityException;


public class EditorialService {
    Scanner read = new Scanner(System.in).useDelimiter("\n");
    
    EditorialJpaController ejc = new EditorialJpaController();

    private Editorial crearEditorial(){
        System.out.println("Ingrese el nombre del Editorial:");
        String nombre = read.next();
        return new Editorial(nombre,true);
    } 
    
    public Editorial seleccionarEditorial(){
        Editorial elegido = null;
        System.out.println("Elija un editorial cargado por ID, caso contrario oprima 0(cero)");
        ejc.mostrarEditoriales();
        Long opcion = read.nextLong();
        if(opcion==0){
            elegido = crearEditorial();
            ejc.create(elegido);
        }else{
            elegido = ejc.findEditorial(opcion);
        }
        return elegido;
    }
    
    private void eliminarEditorial() throws NonexistentEntityException, Exception{
        ejc.mostrarEditoriales();
        System.out.println("Ingrese el ID de la editorial a eliminar:");
        Long id = read.nextLong();
        Editorial eliminada= ejc.findEditorial(id);
        eliminada.setAlta(false);
        ejc.edit(eliminada);
    }

    private void buscarEditoriales(){
        Editorial editorial = null;
        System.out.println("Como desea buscar al editorial?");
        System.out.println("1.Por ID");
        System.out.println("2.Por nombre");
        int opcion = read.nextInt();
        switch (opcion) {
            case 1:
                editorial = buscarEditorialPorID();
                mostrarEditorial(editorial);
                break;
            case 2:
                editorial = buscarEditorialPorNombre();
                mostrarEditorial(editorial);
                break;
        }
    }

    private Editorial buscarEditorialPorID(){
        System.out.println("Ingrese el ID de la editorial a buscar");
        Long id = read.nextLong();
        return ejc.findEditorial(id);
    }

    private Editorial buscarEditorialPorNombre(){
        System.out.println("Ingrese el nombre de la editorial a buscar");
        String nombre = read.next();
        return ejc.findEditorialName(nombre);
    }

    private void mostrarEditorial(Editorial editorial){
        System.out.println(editorial.toString());
    }
    
    public void menuEditorial() throws Exception{
        LibreriaService lis = new LibreriaService();
        Editorial nueva = null;
        int op;
        int op2 = 0;

        do {
            System.out.println("");
            System.out.println("EDITORIALES");
            System.out.println("MENU");
            System.out.println("1.Cargar editorial nueva");
            System.out.println("2.Mostrar editoriales");
            System.out.println("3.Buscar editoriales");
            System.out.println("4.Modificar editoriales");
            System.out.println("5.Eliminar editoriales");
            System.out.println("6.Volver al menu anterior");
            System.out.println("Ingrese una opcion: ");

            op = read.nextInt();
            while (op < 0 || op > 7) {
                System.out.println("Opcion incorrecta, elija la opcion entre 1 y 6");
                op = read.nextInt();
            }

            switch (op) {
                case 1:
                    nueva = crearEditorial();
                    ejc.create(nueva);
                    break;
                case 2:
                    ejc.mostrarEditoriales();
                    break;
                case 3:
                    buscarEditoriales();
                    break;
                case 4:
                    modificarEditorial();
                    break;
                case 5:
                    eliminarEditorial();
                    break;
                case 6:
                    lis.menu();
                    break;
            }
        } while (op2 != 1);
    }

    private void modificarEditorial() throws Exception{
        Editorial aModificar = buscarEditorialPorID();
        System.out.println("Ingrese el nombre de la editorial: ");
        String nombreNuevo = read.next();
        aModificar.setNombre(nombreNuevo);
        ejc.edit(aModificar);
        System.out.println("Editorial Modificada !");
    }
}
