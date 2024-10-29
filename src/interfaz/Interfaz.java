package interfaz;
import dominio.*;
import java.util.Scanner;

public class Interfaz {
    private Libreta libreta = new Libreta();
    private final Scanner teclado = new Scanner(System.in);
    public Interfaz() {}


    //Métodos de Interfaz
    public void ejecutar() {
        String peticion;
        do {
            peticion = leerPeticion();
        } while (procesandoPeticion(peticion));
    }
    public String listaOpciones() {
        StringBuilder sb = new StringBuilder("Lista de opciones\n");
        sb.append("\t1. add <nombre> <teléfono>: Añade un contacto a la libreta.\n")
                .append("\t2. list: Muestra la lista de contactos.\n")
                .append("\t3. exit: Sale del programa.\n");
        return sb.toString();
    }
    public String leerPeticion() {
        System.out.print(listaOpciones()+"Introduce tu petición: ");
        String entrada = teclado.nextLine();
        return entrada;
    }
    public boolean procesandoPeticion(String entrada) {
        String[] peticion = entrada.splitWithDelimiters(" ",3);
        if (peticion[0].equalsIgnoreCase("add")) {
            libreta.add(new Contacto(peticion[1], peticion[2]));
            return true;
        }
        else if (peticion[0].equalsIgnoreCase("list")) {
            System.out.println(libreta);
            return true;
        }
        else if (peticion[0].equalsIgnoreCase("exit")) {
            System.out.println("Saliendo del programa.");
            return false;
        }
        else {
            System.out.println("Petición errónea.");
            return true;
        }
    }
}