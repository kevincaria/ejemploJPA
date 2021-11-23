package service;

import java.util.Scanner;

public class LibreriaService {
    
    
    public void menu() throws Exception{
        
        Scanner read = new Scanner(System.in).useDelimiter("\n");
        AutorService as = new AutorService();
        EditorialService es = new EditorialService();
        LibroService ls = new LibroService();
        int op;
        int op2 = 0;

        do {
            System.out.println("");
            System.out.println("Libreria");
            System.out.println("MENU");
            System.out.println("1.Libros");
            System.out.println("2.Editoriales");
            System.out.println("3.Autores");
            System.out.println("4.Salir");
            System.out.println("Ingrese una opcion: ");

            op = read.nextInt();
            while (op < 0 || op > 5) {
                System.out.println("Opcion incorrecta, elija la opcion entre 1 y 4");
                op = read.nextInt();
            }

            switch (op) {
                case 1:
                    ls.menuLibros();
                    break;
                case 2:
                    es.menuEditorial();
                    break;
                case 3:
                    as.menuAutor();
                    break;
                case 4:
                    System.out.println("Esta seguro que desea salir?");
                    System.out.println("1.Si");
                    System.out.println("2.No");
                    op2 = read.nextInt();
                    while (op2 != 1 && op2 != 2) {
                        System.out.println("Ingrese una opcion correcta");
                        op2 = read.nextInt();
                    }
            }
        } while (op2 != 1);
        read.close();
    }
    

}
